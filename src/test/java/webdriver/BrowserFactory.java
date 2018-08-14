package webdriver;

import com.opera.core.systems.OperaDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;
import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import webdriver.Browser.Browsers;

import javax.naming.NamingException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static webdriver.Logger.getLoc;

/**
 * The class-initializer-based browser string parameter.
 */
final public class BrowserFactory {

    private static final String LOCALHOST = "localhost";
    private static String androidHost = System.getProperty("androidHost", LOCALHOST);
    private static String androidPort = System.getProperty("androidPort", "6789");
    private static String androidEmuSid = System.getProperty("androidSerial", "emulator-5554");
    private static String androidBrowserName = System.getProperty("androidBrowserName", "android");
    private static final Logger logger = Logger.getInstance();
    private static final String CLS_NAME = BrowserFactory.class.getName();
    private static String PROPERTIES_FILE = "selenium.properties";
    protected static PropertiesResourceManager props = new PropertiesResourceManager(PROPERTIES_FILE);
    private static final String ARTIFACTS_PATH = props.getProperty("artifactsTestDataDirectory");

    public static final String PLATFORM = System.getProperty("os.name").toLowerCase();
    public static final String PATH_FOR_ARTIFACTS = FileSystems.getDefault().getPath((ARTIFACTS_PATH), File.separator).toAbsolutePath().toString();

    private BrowserFactory() {
        // do not instantiate BrowserFactory class
    }

    /**
     * Setting up Driver
     *
     * @param type Browser type
     * @return RemoteWebDriver
     */
    public static RemoteWebDriver setUp(final Browsers type) {
        Proxy proxy = null;
        if (Browser.getAnalyzeTraffic()) {
            //browsermob proxy
            //-----------------------------------------------------------
            //captures the mouse movements and navigations
            ProxyServ.getProxyServer().setCaptureHeaders(true);
            ProxyServ.getProxyServer().setCaptureContent(true);
            // get the Selenium proxy object
            proxy = null;
            try {
                proxy = ProxyServ.getProxyServer().seleniumProxy();
            } catch (Exception e) {
                logger.debug("BrowserFactory.setUp", e);
            }
        }
        return getWebDriver(type, proxy);
    }

    /**
     * Setting up Driver
     *
     * @param type Browser type
     * @return RemoteWebDriver
     * @throws NamingException NamingException
     */
    public static RemoteWebDriver setUp(final String type) throws NamingException {
        for (Browsers t : Browsers.values()) {
            if (t.toString().equalsIgnoreCase(type)) {
                return setUp(t);
            }
        }
        throw new NamingException(getLoc("loc.browser.name.wrong") + ":\nchrome\nfirefox\niexplore\nopera\nsafari");
    }

    //////////////////
    // Private methods
    //////////////////

    private static RemoteWebDriver getWebDriver(final Browsers type, Proxy proxy) {
        DesiredCapabilities capabilitiesProxy = new DesiredCapabilities();
        if (proxy != null) {
            capabilitiesProxy.setCapability(CapabilityType.PROXY, proxy);
        }
        switch (type) {
            case CHROME:
                return getChromeDriver(proxy);
            case FIREFOX:
                return getFirefoxDriver(capabilitiesProxy);
            default:
                return null;
        }
    }

    private static RemoteWebDriver getFirefoxDriver(DesiredCapabilities capabilities) {


        String driverPath = "src/main/resources/geckodriver";
        String absoluteDriverPath = FileSystems.getDefault().getPath(driverPath)
                .normalize().toAbsolutePath().toString();
        System.setProperty("webdriver.gecko.driver", absoluteDriverPath);
        DesiredCapabilities caps =  (capabilities != null) ? capabilities : new DesiredCapabilities();
        FirefoxProfile ffProfile = new FirefoxProfile();
//        caps.setCapability("browserName", "firefox");
//        caps.setCapability("browserVersion", "61.0");
//        caps.setCapability("platformName", "Linux");
        //caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

        ffProfile.setPreference("plugin.state.flash", (Browser.isWithoutFlash()) ? 1 : 0);
        ffProfile.setPreference("browser.download.folderList", 2);
        ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "application/x-debian-package; application/octet-stream");
        ffProfile.setPreference("browser.download.manager.showWhenStarting", false);
        ffProfile.setPreference("pdfjs.disabled", true);
        ffProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        if (Browser.getDetectJsErrors()) {
            try {
                JavaScriptError.addExtension(ffProfile);
            } catch (IOException e) {
                logger.debug(CLS_NAME + ".getFirefoxDriver", e);
                logger.warn("Error during initializing of FF (JavaScriptError) webdriver");
            }
        }
        FirefoxDriverManager.getInstance().setup();
        return new FirefoxDriver(caps);
        // return new FirefoxDriver(new FirefoxBinary(), ffProfile, caps);
    }

    private static RemoteWebDriver getChromeDriver(Proxy proxy) {
        ChromeOptions options = null;
        if (Browser.getDetectJsErrors()) {
            options = new ChromeOptions();
            options.addExtensions(new File(ClassLoader.getSystemResource("Chrome_JSErrorCollector.crx").getPath()));
        }
        options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_settings.popups", "0");
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("safebrowsing.enabled", "true");
        prefs.put("download.default_directory", PATH_FOR_ARTIFACTS);
        options.setExperimentalOption("prefs", prefs);
        DesiredCapabilities cp1 = DesiredCapabilities.chrome();
        cp1.setCapability("chrome.switches", Arrays.asList("--disable-popup-blocking"));
        cp1.setCapability(ChromeOptions.CAPABILITY, options);
        if (Browser.getAnalyzeTraffic()) {
            cp1.setCapability(CapabilityType.PROXY, proxy);
        }
        if (options != null) {
            cp1.setCapability(ChromeOptions.CAPABILITY, options);
        }
        ChromeDriverManager.getInstance().setup();
        RemoteWebDriver driver = new ChromeDriver(cp1);
        return driver;
    }
}