package vk.pages;

import org.openqa.selenium.By;
import vk.pages.menu.LeftMainMenu;
import webdriver.BaseForm;;

public class NewsPage extends BaseForm {

    private static final String MAIN_LOCATOR = "//a[contains(@id,'menu_news')]";
    public LeftMainMenu leftMainMenu = new LeftMainMenu();

    public NewsPage() {
        super(By.xpath(MAIN_LOCATOR), "Vkontakte News Page");
    }



}
