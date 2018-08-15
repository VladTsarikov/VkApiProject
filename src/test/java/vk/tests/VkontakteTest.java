package vk.tests;

import vk.pages.*;
import webdriver.BaseTest;
import webdriver.PropertiesResourceManager;

import static framework.enums.MainLeftMenuLabel.*;
import static org.testng.Assert.*;

public class VkontakteTest extends BaseTest {

    private static PropertiesResourceManager prop = new PropertiesResourceManager("credentials.properties");
    private final static String EMAIL_USERNAME = prop.getProperty("credentials_username");
    private final static String EMAIL_PASSWORD = prop.getProperty("credentials_password");
    String data = Long.toHexString(Double.doubleToLongBits(Math.random()));
    String data2 = Long.toHexString(Double.doubleToLongBits(Math.random()));

    @Override
    public void runTest() {
        logStep(1,"OPENING vk.com...");
        MainPage mainPage = new MainPage();

        logStep(2,"AUTHORIZATION...");
        mainPage.sigIn(EMAIL_USERNAME, EMAIL_PASSWORD);

        logStep(3,"OPENING NEWS PAGE AND SELECTING MY PAGE TAB...");
        NewsPage newsPage = new NewsPage();
        newsPage.leftMainMenu.clicLeftMainMenuLabel(MY_PAGE);

        logStep(1,"OPENING MAIN USER PAGE..");
        MainUserPage mainUserPage = new MainUserPage();
        mainUserPage.addTextPost(data,"GET");
        //assertTrue(mainUserPage.vefifyPostForNecessaryText(mainUserPage.getLastPostId(),data));
        //assertTrue(mainUserPage.verifyPostForNecessaryUser(mainUserPage.getLastPostId(),mainUserPage.leftMainMenu.getCurrentUserId()));
        mainUserPage.editPost();
        //assertTrue(mainUserPage.vefifyPostForNecessaryText(mainUserPage.getLastPostId(),data));
        //assertTrue(mainUserPage.verufyPostForCorrectPhoto());
        //mainUserPage.addComment(mainUserPage.leftMainMenu.getCurrentUserId(),mainUserPage.getLastPostId(),data2);
        //assertTrue(mainUserPage.verifyComment(mainUserPage.getLastPostId(),mainUserPage.leftMainMenu.getCurrentUserId()));
        //mainUserPage.addPostLike(mainUserPage.getLastPostId());
        //mainUserPage.verifyIsPostLiked(mainUserPage.getLastPostId(),mainUserPage.leftMainMenu.getCurrentUserId());
        //mainUserPage.deletePost(mainUserPage.leftMainMenu.getCurrentUserId(),mainUserPage.getLastPostId());
        //assertFalse(mainUserPage.verifyIsPostDelete(mainUserPage.leftMainMenu.getCurrentUserId(),mainUserPage.getLastPostId()));

    }

}
