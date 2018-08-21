package vk.pages;

import framework.JsonFunctions;
import framework.RegularExpFinder;
import framework.VkApiUtils;
import framework.enums.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import webdriver.BaseForm;
import webdriver.Browser;
import webdriver.elements.*;
import webdriver.waitings.SmartWait;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class MainUserPage extends BaseForm {

    private final static String MAIN_LOCATOR = "//div[contains(@id,'owner_photo')]";
    private String postCommonIdPart = "//div[contains(@id,'_%s')]";
    private String postTextPath = String.format("%s//div[contains(@class,'post_text')]",postCommonIdPart);
    private String postActiveLikePath = "//div[contains(@class,'like_wall%s_%s')]//a[contains(@class,'active')]";
    private String postOwnerIdPath = String.format("%s//a[contains(@class,'post_image')]",postCommonIdPart);
    private String commentOwnerIdPath = String.format("%s//div[contains(@class,'reply_author')]//a",postCommonIdPart);
    private String postLikePath = new StringBuilder(postCommonIdPart).append("//div[contains(@class,'wall%s')]//a[contains(@class,'like_btn like ')]").toString();
    private String postPhotoPath = "//div[@id='wpt%s_%s']//a[contains(@href,'photo')]";
    private String deletePostId = "//div[@id='post%s_%s')]";
    private VkApiUtils vkApiUtils = new VkApiUtils();
    private String uploadPhotoOwnerId;
    private String uploadPhotoId;
    private String photoServerForDownload;
    private String photoIdForDownload;
    private String photoHashForDownload;
    private String response;

    public MainUserPage() {
        super(By.xpath(MAIN_LOCATOR), "Vkontakte Main User Page");
    }

    public String addTextPost(String userId, String message, String token) {
        response = vkApiUtils.sendGetRequest(String.format(RequestUrlTemplate.TEXT_POST.getRequestTemplate(),userId,message,token));
        return JsonFunctions.parseResponseBody(response,ResponseParamName.RESPONSE.getParamName(),ResponseParamName.POST_ID.getParamName());
    }

    public String verifyPostForNecessaryUser(String postId){
        return RegularExpFinder.findByRegularExp(new Label(By.xpath(String.format(postOwnerIdPath,postId)), "Post Id Label").getAttribute("href"),RegularExpression.USER_ID.getRegExp());
    }

    public void editPost(String userId, String postId, String message, String token,String filePath,String encoding){
        addPostPhoto(userId,token,filePath,encoding);
        response = vkApiUtils.sendGetRequest(String.format(RequestUrlTemplate.POST_EDITOR.getRequestTemplate(),userId,postId, message,uploadPhotoOwnerId,uploadPhotoId,token));
    }

    private void addPostPhoto(String userId,String token,String filePath, String encoding){
        String uploadUrl = getWallUploadServer(userId,token);
        String multiResponse = vkApiUtils.sendPostPhotoRequest(uploadUrl,filePath);
        photoServerForDownload = JsonFunctions.getResponseBody(multiResponse,ResponseParamName.SERVER.getParamName());
        photoIdForDownload = JsonFunctions.getResponseBody(multiResponse,ResponseParamName.PHOTO.getParamName());
        photoHashForDownload = JsonFunctions.getResponseBody(multiResponse,ResponseParamName.HASH.getParamName());
        String photo = null;
        try {
            photo = URLEncoder.encode(photoIdForDownload,encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        saveWallPhoto(userId,token,photo);
    }

    private String getWallUploadServer(String userId, String token){
        response = vkApiUtils.sendGetRequest(String.format(RequestUrlTemplate.PHOTO_UPLOADER.getRequestTemplate(),userId,token));
        return JsonFunctions.parseResponseBody(response,ResponseParamName.RESPONSE.getParamName(),ResponseParamName.UPLOAD_URL.getParamName());
    }

    private void saveWallPhoto(String userId,String token,String photo){
        response = vkApiUtils.sendGetRequest(String.format(RequestUrlTemplate.PHOTO_SAVER.getRequestTemplate(),userId,photo,photoServerForDownload,photoHashForDownload,token));
        uploadPhotoOwnerId = JsonFunctions.getResponseBody(JsonFunctions.getResponseBody(response,ResponseParamName.RESPONSE.getParamName()).replaceAll(RegularExpression.BRACKETS_REPLACER.getRegExp(), VkApiUtils.getReplacement()),ResponseParamName.OWNER_ID.getParamName());
        uploadPhotoId = JsonFunctions.getResponseBody(JsonFunctions.getResponseBody(response,ResponseParamName.RESPONSE.getParamName()).replaceAll(RegularExpression.BRACKETS_REPLACER.getRegExp(), VkApiUtils.getReplacement()),ResponseParamName.ID.getParamName());
    }

    public String verifyPostForNecessaryText(String postId, String text){
        SmartWait.waitForTrue(ExpectedConditions.textToBePresentInElementValue(By.xpath(String.format(postTextPath,postId)),text),Browser.getDefaultElementChangeTimeout());
        return (new TextBox(By.xpath(String.format(postTextPath,postId)), "Post Text TextBox").getText());
    }

    public boolean verifyPostForCorrectPhoto(String userId, String postId){
        String compareId = RegularExpFinder.findByRegularExp(new Label(By.xpath(String.format(postPhotoPath,userId,postId)), "Post Photo Label").getAttribute("href"),RegularExpression.PHOTO_COMPARE_ID.getRegExp());
        return compareId.equals(uploadPhotoId);
    }

    public void addComment(String userId, String postId,String message, String token){
        response = vkApiUtils.sendGetRequest(String.format(RequestUrlTemplate.COMMENT_ADDER.getRequestTemplate(),userId,postId,message,token));
    }

    public String verifyComment(String postId){
        return RegularExpFinder.findByRegularExp(new TextBox(By.xpath(String.format(commentOwnerIdPath, postId)), "Comment Owner Id TextBox").getAttribute("href"),RegularExpression.USER_ID.getRegExp());
    }

    public void addPostLike(String postId, String userId){
        new Label(By.xpath(String.format(postLikePath,postId,userId)), "Post Like Label").clickViaJsAndWait();
        SmartWait.waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(postActiveLikePath, userId,postId))));
    }

    public String verifyIsPostLiked(String postId, String userId, String token){
        response = vkApiUtils.sendGetRequest(String.format(RequestUrlTemplate.LIKE_CHECKER.getRequestTemplate(),userId,userId,postId,token));
        return JsonFunctions.parseResponseBody(response,ResponseParamName.RESPONSE.getParamName(),ResponseParamName.LIKED.getParamName());
    }

    public void deletePost(String ownerId, String postId, String token){
        response = vkApiUtils.sendGetRequest(String.format(RequestUrlTemplate.POST_DELETER.getRequestTemplate(),ownerId,postId,token));
    }

    public boolean verifyIsPostDelete(String ownerId, String postId){
        return new Label(By.xpath(String.format(deletePostId,ownerId,postId)), "Delete Post Label").isPresent();
    }
}