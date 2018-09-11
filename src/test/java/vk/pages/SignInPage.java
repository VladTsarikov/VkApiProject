package vk.pages;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.*;

public class SignInPage extends BaseForm {

    private static final String MAIN_LOCATOR = "//button[contains(@id,'login')]";
    private static final String CREDENTIALS_LOCATOR_TEMPLATE = "//input[@name='%s']";
    private final Button btnSignIn = new Button(By.xpath(MAIN_LOCATOR), "SignIn Button");
    private final TextBox txbUserEmail = new TextBox(By.xpath(String.format(CREDENTIALS_LOCATOR_TEMPLATE,"email")), "Email Field");
    private final TextBox txbUserPassword = new TextBox(By.xpath(String.format(CREDENTIALS_LOCATOR_TEMPLATE,"pass")), "Password Field");

    public SignInPage() {
        super(By.xpath(MAIN_LOCATOR), "Vkontakte Main Page");
    }

    public void sigIn(String userEmail, String password){
        txbUserEmail.setText(userEmail);
        txbUserPassword.setText(password);
        btnSignIn.clickAndWait();
    }
}
