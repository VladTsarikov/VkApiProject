package vk.pages;

import framework.RegularExpFinder;
import framework.VkApiUtils;
import framework.enums.RegularExpression;
import framework.enums.RequestUrlTemplate;
import org.openqa.selenium.By;
import vk.pages.menu.LeftMainMenu;
import webdriver.BaseForm;
import webdriver.elements.Label;
import webdriver.elements.TextBox;

import java.io.File;
import java.io.IOException;
import java.net.*;
public class MainUserPage extends BaseForm {

    private static final String MAIN_LOCATOR = "//div[contains(@id,'owner_photo')]";
    private static final String INDIKATOR_EXIST_LIKE = "1";
    private String postTextPath = "//div[contains(@id,'_%s')]//div[contains(@class,'post_text')]";
    private String postOwnerIdPath = "//div[contains(@id,'_%s')]//a[contains(@class,'post_image')]";
    private String commentOwnerIdPath = "//div[contains(@id,'_%s')]//div[contains(@class,'reply_author')]//a";
    private String postLikePath = "//div[contains(@id,'_%s')]//div[contains(@class,'wall%s')]//a[contains(@class,'like_btn like ')]";
    //    private final TextBox txbUserEmail = new TextBox(By.xpath("//input[@name='email']"), "Email Field");
//    private final TextBox txbUserPassword = new TextBox(By.xpath("//input[@name='pass']"), "Password Field");
    private VkApiUtils vkApiUtils = new VkApiUtils();
    public LeftMainMenu leftMainMenu = new LeftMainMenu();
    private HttpURLConnection httpURLConnection;
    private String id = "381108928";
    private String token = "904da13c62ef685c2d17cdd3f3fb8430dcb1e6f23311809ba5b79e18fa4e97334dc30da82610f31b575ba";
    private String lastPostId;
    private String photoServerForDownload;
    private String photoIdForDownload;
    private String photoHashForDownload;
    private String response;


    public MainUserPage() {
        super(By.xpath(MAIN_LOCATOR), "Vkontakte Main User Page");
    }

    public void addTextPost(String message, String requesstType) {
        String request = String.format(RequestUrlTemplate.TEXT_POST.getRequest(),id,message,token);
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,requesstType);
        lastPostId = RegularExpFinder.findByRegularExp(vkApiUtils.getResponse(httpURLConnection), RegularExpression.POST_ID.getRegExp());
    }

    public boolean verifyPostForNecessaryUser(String postId, String userId){
        return new TextBox(By.xpath(String.format(postOwnerIdPath,postId)), "Post Id TextBox").getAttribute("href").equals(userId);
    }

    public String getLastPostId() {
        return lastPostId;
    }


    public void editPost(){
        String request = String.format(RequestUrlTemplate.POST_EDITOR.getRequest(),id,token);
        System.out.println("!!! " + request);
        //httpURLConnection = vkApiUtils.getConnection(request,"GET");
        //response = vkApiUtils.getResponse(httpURLConnection);
        //System.out.println(response);
        //photoServerForDownload = RegularExpFinder.findByRegularExp(response,RegularExpression.PHOTO_SERVER.getRegExp());
        //photoIdForDownload = RegularExpFinder.findByRegularExp(response,RegularExpression.PHOTO_ID.getRegExp());
        //photoHashForDownload = RegularExpFinder.findByRegularExp(response,RegularExpression.PHOTO_HASH.getRegExp());

       // String urlSavedPhoto = String.format(RequestUrlTemplate.PHOTO_SAVER.getRequest(),id, photoIdForDownload, photoServerForDownload, photoHashForDownload, token);
        //System.out.println(urlSavedPhoto);
        try {
            vkApiUtils.getMultipartEntity(request,new File("src/main/resources/images/testImage.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean vefifyPostForNecessaryText(String postId, String text){
        return new TextBox(By.xpath(String.format(postTextPath,postId)), "Post Text TextBox").getText().equals(text);
    }

    public void verufyPostForCorrectPhoto(){

    }

    public void addComment(String userId, String postId,String message){
        String request = String.format(RequestUrlTemplate.COMMENT_ADDER.getRequest(),id,lastPostId,message,token);
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,"GET");
        System.out.println(vkApiUtils.getResponse(httpURLConnection));
    }

    public boolean verifyComment(String postId, String userId){
        return new TextBox(By.xpath(String.format(commentOwnerIdPath, postId)), "Comment Owner Id TextBox").getAttribute("href").equals(userId);
    }

    public void addPostLike(String postId){
        new Label(By.xpath(String.format(postLikePath,postId,id)), "Post Like Label").click();
    }

    public boolean verifyIsPostLiked(String postId, String userId){
        String request = String.format(RequestUrlTemplate.LIKE_CHECKER.getRequest(),id,id,postId,token);
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,"GET");
        response = vkApiUtils.getResponse(httpURLConnection);
        return RegularExpFinder.findByRegularExp(response,RegularExpression.LIKE_INDIKATOR.getRegExp()).equals(INDIKATOR_EXIST_LIKE);
    }

    public void deletePost(String ownerId, String postId){
        String request = String.format(RequestUrlTemplate.POST_DELETER.getRequest(),id,postId,token);
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,"GET");
        System.out.println(vkApiUtils.getResponse(httpURLConnection));
    }

    public boolean verifyIsPostDelete(String ownerId, String postId){
        return new Label(By.xpath(String.format("//div[contains(@id,'post_del381108928_25783')]",ownerId,postId)), "Delete Post Label").isPresent();
    }

}