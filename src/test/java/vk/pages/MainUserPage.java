package vk.pages;

import framework.RegularExpFinder;
import framework.VkApiUtils;
import framework.enums.RegularExpression;
import org.openqa.selenium.By;
import vk.pages.menu.LeftMainMenu;
import webdriver.BaseForm;
import webdriver.elements.Label;
import webdriver.elements.TextBox;
import java.net.*;
public class MainUserPage extends BaseForm {

    private static final String MAIN_LOCATOR = "//div[contains(@id,'owner_photo')]";
    private String postTextPath = "//div[contains(@id,'_%s')]//div[contains(@class,'post_text')]";
    private String postOwnerIdPath = "//div[contains(@id,'_%s')]//a[contains(@class,'post_image')]";
    private String commentOwnerIdPath = "//div[contains(@id,'_%s')]//div[contains(@class,'reply_author')]//a";
    private String postLikePath = "//div[contains(@id,'_%s')]//div[contains(@class,'wall%s')]//a[contains(@class,'like_btn like ')]";
//    private final TextBox txbUserEmail = new TextBox(By.xpath("//input[@name='email']"), "Email Field");
//    private final TextBox txbUserPassword = new TextBox(By.xpath("//input[@name='pass']"), "Password Field");
    private VkApiUtils vkApiUtils = new VkApiUtils();
    public LeftMainMenu leftMainMenu = new LeftMainMenu();
    HttpURLConnection httpURLConnection;
    boolean bool;
    private String id = "381108928";
    private String token = "904da13c62ef685c2d17cdd3f3fb8430dcb1e6f23311809ba5b79e18fa4e97334dc30da82610f31b575ba";
    private String lastPostId;
    private String photoUrlForDownload;


    public MainUserPage() {
        super(By.xpath(MAIN_LOCATOR), "Vkontakte Main User Page");
    }

    public void addTextPost(String message, String requesstType) {
        String contentType = "text/plain";
        String request = String.format(vkApiUtils.getRequestTemplate(),String.format("wall.post?owner_id=%s&message=%s&access_token=%s",id,message,token),"5.80");
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,requesstType);

        lastPostId = RegularExpFinder.findByRegularExp(vkApiUtils.getResponse(httpURLConnection), RegularExpression.POST_ID.getRegExp());

    }

    public boolean verifyPostForTextAndNecessaryUser(String postId, String text, String userId){
        if(new TextBox(By.xpath(String.format(postTextPath,postId)), "Post Text TextBox").getText().equals(text) && new TextBox(By.xpath(String.format(postOwnerIdPath,postId)), "Post Id TextBox").getAttribute("href").equals(userId)){
           bool = Boolean.TRUE;
        }else{
            bool = Boolean.FALSE;
        }
        return bool;
    }

    public String getLastPostId() {
        return lastPostId;
    }


//    public void editPost(){
//        String request = String.format(vkApiUtils.getRequestTemplate(),String.format("photos.getWallUploadServer?owner_id=%s&access_token=%s",id,token),"5.80");
//        System.out.println("!!! " + request);
//        httpURLConnection = vkApiUtils.getConnection(request,"GET");
//        System.out.println(vkApiUtils.getResponse(httpURLConnection));
//        photoUrlForDownload = RegularExpFinder.findByRegularExp(vkApiUtils.getResponse(httpURLConnection),RegularExpression.PHOTO_URL.getRegExp());
//
//
//
//
//
//    }

    public void addComment(String userId, String postId,String message){
        String request = String.format(vkApiUtils.getRequestTemplate(),String.format("wall.createComment?owner_id=%s&post_id=%s&message=%s&access_token=%s",id,lastPostId,message,token),"5.80");
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,"GET");
        System.out.println(vkApiUtils.getResponse(httpURLConnection));
    }

    public boolean verifyComment(String postId, String userId){
        if(new TextBox(By.xpath(String.format(commentOwnerIdPath,postId)), "Comment Owner Id TextBox").getAttribute("href").equals(userId)){
            bool = Boolean.TRUE;
        }else{
            bool = Boolean.FALSE;
        }
        return bool;
    }

    public void addPostLike(String postId){
        new Label(By.xpath(String.format(postLikePath,postId,id)), "Post Like Label").click();
    }

    public boolean verifyIsPostLiked(String postId, String userId){
        String request = String.format(vkApiUtils.getRequestTemplate(),String.format("likes.isLiked?user_id=%s&type=post&owner_id=%s&item_id=%s&access_token=%s",id,userId,postId,token),"5.80");
        System.out.println("!!! " + request);
        httpURLConnection = vkApiUtils.getConnection(request,"GET");
        System.out.println(vkApiUtils.getResponse(httpURLConnection));
        if(RegularExpFinder.findByRegularExp(vkApiUtils.getResponse(httpURLConnection),RegularExpression.LIKE_INDIKATOR.getRegExp()).equals("1")){
            bool = Boolean.TRUE;
        }else{
            bool = Boolean.FALSE;
        }
        return bool;
    }


}
