package vk.tests;

import vk.pages.*;
import webdriver.BaseTest;
import webdriver.PropertiesResourceManager;
import static framework.enums.MainLeftMenuLabel.*;
import static org.testng.Assert.*;

public class VkontakteTest extends BaseTest {

    private static PropertiesResourceManager prop = new PropertiesResourceManager("credentials.properties");
    private final static String USER_USERNAME = prop.getProperty("credentials_username");
    private final static String USER_PASSWORD = prop.getProperty("credentials_password");
    private final static String USER_TOKEN = prop.getProperty("credentials_token");
    private final static String USER_ID = prop.getProperty("credentials_user_id");
    private final static String POST_TEXT = Long.toHexString(Double.doubleToLongBits(Math.random()));
    private final static String EDITION_TEXT = Long.toHexString(Double.doubleToLongBits(Math.random()));
    private final static String COMMENT_TEXT = Long.toHexString(Double.doubleToLongBits(Math.random()));
    private final static String GET_REQUEST_TYPE = "GET";

    @Override
    public void runTest() {
        logStep(1,"OPENING vk.com...");
        SignInPage signInPage = new SignInPage();

        logStep(2,"AUTHORIZATION...");
        signInPage.sigIn(USER_USERNAME, USER_PASSWORD);

        logStep(3,"OPENING NEWS PAGE AND SELECTING MY PAGE TAB...");
        NewsPage newsPage = new NewsPage();
        newsPage.leftMainMenu.clicLeftMainMenuLabel(MY_PAGE);

        logStep(4,"OPENING MAIN USER PAGE..");
        MainUserPage mainUserPage = new MainUserPage();

        logStep(5,"ADDING POST WITH TEXT AND VERIFY THIS STEP...");
        mainUserPage.addTextPost(USER_ID, POST_TEXT,GET_REQUEST_TYPE,USER_TOKEN);
        assertTrue(mainUserPage.verifyPostForNecessaryText(mainUserPage.getLastPostId(),POST_TEXT),String.format("Text %s has not found",POST_TEXT));
        //assertTrue(mainUserPage.verifyPostForNecessaryUser(mainUserPage.getLastPostId(),USER_ID),"Post does not belong this user");

        logStep(6,"ADDING AND VERIFY PHOTO AND CHANGING TEXT IN POST...");
        mainUserPage.editPost(USER_ID,mainUserPage.getLastPostId(),EDITION_TEXT,USER_TOKEN,GET_REQUEST_TYPE);
        //assertTrue(mainUserPage.verifyPostForNecessaryText(mainUserPage.getLastPostId(),POST_TEXT),String.format("Text %s have not found",EDITION_TEXT)));
        //assertTrue(mainUserPage.verifyPostForCorrectPhoto(),"Photo has not found");

        //logStep(7,"ADDING AND VERIFY POST COMMENT...");
       // mainUserPage.addComment(USER_ID,mainUserPage.getLastPostId(),COMMENT_TEXT,USER_TOKEN,GET_REQUEST_TYPE);
        //assertTrue(mainUserPage.verifyComment(mainUserPage.getLastPostId(),USER_ID),String.format("Text %s has not found",COMMENT_TEXT));

       // logStep(8,"ADDING AND VERIFY POST LIKE...");
       // mainUserPage.addPostLike(mainUserPage.getLastPostId(), USER_ID);
        assertTrue(mainUserPage.verifyIsPostLiked(mainUserPage.getLastPostId(),USER_ID,USER_TOKEN,GET_REQUEST_TYPE),"Post has not had 'like'");

        //logStep(9,"DELETE POST AND VERIFY THIS OPTION...");
        //mainUserPage.deletePost(USER_ID,mainUserPage.getLastPostId(),USER_TOKEN,GET_REQUEST_TYPE);
        //assertFalse(mainUserPage.verifyIsPostDelete(USER_ID,mainUserPage.getLastPostId()),"Post has not deleted");
    }
}