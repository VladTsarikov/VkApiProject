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
    private final static String INDIKATOR_EXIST_LIKE = "1";
    private final static String ENCODING = "UTF-8";
    private final static String POST_TEXT = Long.toHexString(Double.doubleToLongBits(Math.random()));
    private final static String EDITION_TEXT = Long.toHexString(Double.doubleToLongBits(Math.random()));
    private final static String COMMENT_TEXT = Long.toHexString(Double.doubleToLongBits(Math.random()));
    private final static String UPLOAD_FILE_PATH = "src/main/resources/images/testImage.jpg";

    @Override
    public void runTest() {
        logStep(1,"OPENING vk.com...");
        SignInPage signInPage = new SignInPage();

        logStep(2,"AUTHORIZATION...");
        signInPage.sigIn(USER_USERNAME, USER_PASSWORD);

        logStep(3,"OPENING NEWS PAGE AND SELECTING MY PAGE TAB...");
        NewsPage newsPage = new NewsPage();
        newsPage.leftMainMenu.clickLeftMenuLabel(MY_PAGE);

        logStep(4,"OPENING MAIN USER PAGE..");
        MainUserPage mainUserPage = new MainUserPage();

        logStep(5,"ADDING POST WITH TEXT AND VERIFY THIS STEP...");
        String lastPostId = mainUserPage.addTextPost(USER_ID, POST_TEXT,USER_TOKEN);
        assertEquals(String.format("Text %s has not found",POST_TEXT),mainUserPage.verifyPostForNecessaryText(lastPostId,POST_TEXT),POST_TEXT);
        assertEquals(String.format("Post does not belong user with id: %s",USER_ID),mainUserPage.verifyPostForNecessaryUser(lastPostId),USER_ID);

        logStep(6,"ADDING PHOTO AND CHANGING TEXT IN POST...");
        mainUserPage.editPost(USER_ID,lastPostId,EDITION_TEXT,USER_TOKEN,UPLOAD_FILE_PATH,ENCODING);

        logStep(7,"VERIFY PHOTO AND TEXT IN POST...");
        assertEquals(String.format("Text %s have not found",EDITION_TEXT),mainUserPage.verifyPostForNecessaryText(lastPostId,EDITION_TEXT),EDITION_TEXT);
        assertTrue(mainUserPage.verifyPostForCorrectPhoto(USER_ID,lastPostId),"Photo has not found");

        logStep(8,"ADDING AND VERIFY POST COMMENT...");
        mainUserPage.addComment(USER_ID,lastPostId,COMMENT_TEXT,USER_TOKEN);
        assertEquals(String.format("Comment %s by user with id:%s has not found",COMMENT_TEXT,USER_ID),mainUserPage.verifyComment(lastPostId),USER_ID);

        logStep(9,"ADDING AND VERIFY POST LIKE...");
        mainUserPage.addPostLike(lastPostId, USER_ID);
        assertEquals("Post has not had 'like'",mainUserPage.verifyIsPostLiked(lastPostId,USER_ID,USER_TOKEN),INDIKATOR_EXIST_LIKE);

        logStep(10,"DELETE POST AND VERIFY THIS OPTION...");
        mainUserPage.deletePost(USER_ID,lastPostId,USER_TOKEN);
        assertFalse(mainUserPage.verifyIsPostDelete(USER_ID,lastPostId),"Post has not deleted");
    }
}