package webdriver;

import org.testng.annotations.Test;

/**
 * An abstract class that describes the basic test application contains
 * methods for logging and field test settings (options)
 */
public abstract class BaseTest extends BaseEntity {


    /**
     * To override.
     */
    public abstract void runTest();

    /**
     * Test
     *
     * @throws Throwable Throwable
     */

    @Test
    public void xTest() throws Throwable {
        Class<? extends BaseTest> currentClass = this.getClass();
        try {
            logger.logTestName(currentClass.getName());
            runTest();
            logger.logTestEnd(currentClass.getName());
        } catch (Exception | AssertionError e) {
            logger.debug(this, e);
            logger.warn("");
            logger.warnRed(getLoc("loc.test.failed"));
            logger.warn("");
            throw e;
        } finally {
            if (Browser.getAnalyzeTraffic()) {
                getBrowser().assertAnalyzedTrafficResponseCode();
                getBrowser().assertAnalyzedTrafficLoadTime();
                ProxyServ.saveToFile(this.getClass().getName());
            }
            makeScreen(currentClass);
        }
    }

    /**
     * Format logging
     *
     * @param message Message
     * @return Message
     */
    @Override
    protected String formatLogMsg(final String message) {
        return message;
    }

}
