package vk.pages.menu;

import framework.enums.*;
import org.openqa.selenium.By;
import webdriver.elements.*;

public class LeftMainMenu {

    private String menuLabel = "//li[contains(@id,'%s')]//a";

    public void clickLeftMenuLabel(MainLeftMenuLabel mainLeftMenuLabel) {
        new Label(By.xpath(String.format(menuLabel, mainLeftMenuLabel.getLeftMenuLabelCategory())), mainLeftMenuLabel.getLeftMenuLabelCategory() + " Label").clickAndWait();
    }
}
