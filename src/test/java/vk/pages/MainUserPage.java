package vk.pages;

import framework.RegularExpFinder;
import framework.VkApiUtils;
import framework.enums.*;
import org.openqa.selenium.By;
import vk.pages.menu.LeftMainMenu;
import webdriver.BaseForm;
import webdriver.elements.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
public class MainUserPage extends BaseForm {

    private static final String MAIN_LOCATOR = "//div[contains(@id,'owner_photo')]";
    private static final String INDIKATOR_EXIST_LIKE = "1";
    private String postTextPath = "//div[contains(@id,'_%s')]//div[contains(@class,'post_text')]";
    private String postOwnerIdPath = "//div[contains(@id,'_%s')]//a[contains(@class,'post_image')]";
    private String commentOwnerIdPath = "//div[contains(@id,'_%s')]//div[contains(@class,'reply_author')]//a";
    private String postLikePath = "//div[contains(@id,'_%s')]//div[contains(@class,'wall%s')]//a[contains(@class,'like_btn like ')]";
    private VkApiUtils vkApiUtils = new VkApiUtils();
    public LeftMainMenu leftMainMenu = new LeftMainMenu();
    private HttpURLConnection httpURLConnection;
    private String lastPostId;
    String uploadPhotoOwnerId;
    String uploadPhotoId;
    String uploadPhotoIdTemplate = "photo%s_%s";
    private String photoServerForDownload;
    private String photoIdForDownload;
    private String photoHashForDownload;
    private String photoOwnerId;
    private String photoId;
    private String response;


    public MainUserPage() {
        super(By.xpath(MAIN_LOCATOR), "Vkontakte Main User Page");
    }

    public void addTextPost(String id,String message, String requesstType, String token) {
//        String request = String.format(RequestUrlTemplate.TEXT_POST.getRequest(),id,message,token);
//        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(String.format(RequestUrlTemplate.TEXT_POST.getRequest(),id,message,token),requesstType);
        lastPostId = RegularExpFinder.findByRegularExp(vkApiUtils.getResponse(httpURLConnection), RegularExpression.POST_ID.getRegExp());
    }

    public boolean verifyPostForNecessaryUser(String postId, String userId){
        String id = RegularExpFinder.findByRegularExp(new Label(By.xpath(String.format(postOwnerIdPath,postId)), "Post Id Label").getAttribute("href"),RegularExpression.USER_ID.getRegExp());
        return id.equals(userId);
    }

    public String getLastPostId() {
        return lastPostId;
    }

    public void editPost(String userId, String postId, String message, String token, String requestType){
        //httpURLConnection = vkApiUtils.getConnection(request,requesstType);
        //response = vkApiUtils.getResponse(httpURLConnection);
        //System.out.println(response);
        //photoServerForDownload = RegularExpFinder.findByRegularExp(response,RegularExpression.PHOTO_SERVER.getRegExp());
        //photoIdForDownload = RegularExpFinder.findByRegularExp(response,RegularExpression.PHOTO_ID.getRegExp());
        //photoHashForDownload = RegularExpFinder.findByRegularExp(response,RegularExpression.PHOTO_HASH.getRegExp());

        // String urlSavedPhoto = String.format(RequestUrlTemplate.PHOTO_SAVER.getRequest(),id, photoIdForDownload, photoServerForDownload, photoHashForDownload, token);
        //System.out.println(urlSavedPhoto);

        addPostPhoto(userId,token,requestType);
        String requestEditPost = String.format(RequestUrlTemplate.POST_EDITOR.getRequest(),userId,postId, message,uploadPhotoOwnerId,uploadPhotoId,token);
        httpURLConnection = vkApiUtils.getConnection(requestEditPost,requestType);

    }

    public void addPostPhoto(String userId,String token,String requestType){
        String request = String.format(RequestUrlTemplate.PHOTO_UPLOADER.getRequest(),userId,token);
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,requestType);
        response = vkApiUtils.getResponse(httpURLConnection);
        System.out.println(response);

        String host = RegularExpFinder.findByRegularExp(response, RegularExpression.UPLOAD_URL_FIRST_PART.getRegExp());
        String url = RegularExpFinder.findByRegularExp(response, RegularExpression.UPLOAD_URL_SECOND_PART.getRegExp());
        String middle = RegularExpFinder.findByRegularExp(response, RegularExpression.UPLOAD_URL_THIRD_PART.getRegExp());

        String uploadUrl = String.format(RequestUrlTemplate.PHOTO_UPLOADER_URL.getRequest(),host,url,middle);
        System.out.println("XX"+uploadUrl);
        String multiResponse = null;
        try {
            multiResponse = vkApiUtils.getMultipartEntity(uploadUrl,"src/main/resources/images/testImage.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        photoServerForDownload = RegularExpFinder.findByRegularExp(multiResponse,RegularExpression.PHOTO_SERVER.getRegExp());
        photoIdForDownload = RegularExpFinder.findByRegularExp(multiResponse,RegularExpression.PHOTO_ID.getRegExp());
        photoHashForDownload = RegularExpFinder.findByRegularExp(multiResponse,RegularExpression.PHOTO_HASH.getRegExp());

        String photo = null;
        try {
            photo = URLEncoder.encode(photoIdForDownload.replaceAll("\\\\",""),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        System.out.println("777777"+photo);
//        String photo1 = null;
//        try {
//            photo1 = URLEncoder.encode(photo, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String requestSavePhoto = String.format(RequestUrlTemplate.PHOTO_SAVER.getRequest(),userId,photo,photoServerForDownload,photoHashForDownload,token);
        System.out.println(requestSavePhoto);
        httpURLConnection = vkApiUtils.getConnection(requestSavePhoto,requestType);
        response = vkApiUtils.getResponse(httpURLConnection);
        System.out.println("5555"+response);

        String uploadPhotoOwnerId = RegularExpFinder.findByRegularExp(response,RegularExpression.UPLOAD_PHOTO_OWNER_ID.getRegExp());
        String uploadPhotoId = RegularExpFinder.findByRegularExp(response,RegularExpression.UPLOAD_PHOTO_ID.getRegExp());





    }

    public boolean verifyPostForNecessaryText(String postId, String text){
        return new TextBox(By.xpath(String.format(postTextPath,postId)), "Post Text TextBox").getText().equals(text);
    }

    public boolean verifyPostForCorrectPhoto(String userId, String postId, String photoId){
        String compareId = RegularExpFinder.findByRegularExp(new Label(By.xpath(String.format("//div[@id='wpt%s_%s']//a[contains(@href,'photo')]",userId,postId)), "Post Photo Label").getAttribute("href"),RegularExpression.PHOTO_COMPARE_ID.getRegExp());
        return compareId.equals(String.format(uploadPhotoIdTemplate,uploadPhotoOwnerId,uploadPhotoId));
    }

    public void addComment(String userId, String postId,String message, String token, String requestType){
        String request = String.format(RequestUrlTemplate.COMMENT_ADDER.getRequest(),userId,postId,message,token);
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,requestType);
        System.out.println(vkApiUtils.getResponse(httpURLConnection));
    }

    public boolean verifyComment(String postId, String userId){
        String id = RegularExpFinder.findByRegularExp(new TextBox(By.xpath(String.format(commentOwnerIdPath, postId)), "Comment Owner Id TextBox").getAttribute("href"),RegularExpression.USER_ID.getRegExp());
        return id.equals(userId);
    }

    public void addPostLike(String postId, String userId){
        new Label(By.xpath(String.format(postLikePath,postId,userId)), "Post Like Label").click();
    }

    public boolean verifyIsPostLiked(String postId, String userId,String token, String requestType){
        String request = String.format(RequestUrlTemplate.LIKE_CHECKER.getRequest(),userId,userId,postId,token);
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,requestType);
        response = vkApiUtils.getResponse(httpURLConnection);
        return RegularExpFinder.findByRegularExp(response,RegularExpression.LIKE_INDIKATOR.getRegExp()).equals(INDIKATOR_EXIST_LIKE);
    }


    public void deletePost(String ownerId, String postId, String token, String requestType){
        String request = String.format(RequestUrlTemplate.POST_DELETER.getRequest(),ownerId,postId,token);
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,requestType);
        System.out.println(vkApiUtils.getResponse(httpURLConnection));
    }

    public boolean verifyIsPostDelete(String ownerId, String postId){
        return new Label(By.xpath(String.format("//div[@id='post%s_%s')]",ownerId,postId)), "Delete Post Label").isPresent();
    }

}