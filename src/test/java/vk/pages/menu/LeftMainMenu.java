package vk.pages.menu;

import framework.enums.*;
import org.openqa.selenium.By;
import webdriver.elements.*;

public class LeftMainMenu {

    private String menuLabel = "//li[contains(@id,'%s')]//a";
    private String path;

    public void clicLeftMainMenuLabel(MainLeftMenuLabel mainLeftMenuLabel) {
        path = String.format(menuLabel, mainLeftMenuLabel.getMainLeftMenuLabelCategory());
        new Label(By.xpath(path), mainLeftMenuLabel.getMainLeftMenuLabelCategory() + " Label").clickAndWait();
    }
}
