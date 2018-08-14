package vk.pages;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.TextBox;

public class MainPage extends BaseForm {

    private static final String MAIN_LOCATOR = "//button[contains(@id,'login')]";
    private final Button btnSignIn = new Button(By.xpath("//button[contains(@id,'login')]"), "SignIn Button");
    private final TextBox txbUserEmail = new TextBox(By.xpath("//input[@name='email']"), "Email Field");
    private final TextBox txbUserPassword = new TextBox(By.xpath("//input[@name='pass']"), "Password Field");

    public MainPage() {
        super(By.xpath(MAIN_LOCATOR), "Vkontakte Main Form");
    }

    public void sigIn(String userEmail, String password){
        txbUserEmail.setText(userEmail);
        txbUserPassword.setText(password);
        btnSignIn.clickAndWait();
    }

}
