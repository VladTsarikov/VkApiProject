package vk.pages.menu;

import framework.enums.MainLeftMenuLabel;
import org.openqa.selenium.By;
import webdriver.elements.*;

public class LeftMainMenu {

    private static Label lblLeftMainMenu;
    private String menuLabel = "//li[contains(@id,'%s')]//a";
    private String path;
    private final static int ID_INDEX = 1;

    public void clicLeftMainMenuLabel(MainLeftMenuLabel mainLeftMenuLabel) {
        path = String.format(menuLabel, mainLeftMenuLabel.getMainLeftMenuLabelCategory());
        lblLeftMainMenu = new Label(By.xpath(path), mainLeftMenuLabel.getMainLeftMenuLabelCategory() + " Label");
        lblLeftMainMenu.clickAndWait();
    }

    public String getCurrentUserId() {
        return lblLeftMainMenu.getAttribute("href");
    }
}
