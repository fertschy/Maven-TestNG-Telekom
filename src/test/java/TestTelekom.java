import static common.Framework.*;

import static logger.Logger.logger;
import static java.util.logging.Level.*;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;

import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.SkipException;

import org.w3c.dom.Document;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public class TestTelekom {
    Boolean telekomHomePageRunningFlag = false;
    Boolean telekomWebRunningFlag = false;
    Boolean telekomWebshopRunningFlag = false;

    private void skipTestExecution() {
        logger.log(INFO, "Skip test.");

        throw new SkipException("Skipping this test case!");
    }

    @BeforeClass(alwaysRun = true)
    private void startTests() {
        //Az összes teszt indítható legyen egy suite.xml fájl megadásával külön-külön és egyben is. A suite.xml fájlt paraméterként lehessen megadni a maven parancsban.
        try {
            File file = new File(System.getProperty("suiteXMLPath"));
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            try {
                telekomHomePageRunningFlag = Boolean.valueOf(document.getElementsByTagName("telekomHomepage").item(0).getTextContent());
            } catch (NullPointerException npe) { }
            try {
                telekomWebRunningFlag = Boolean.valueOf(document.getElementsByTagName("telekomWeb").item(0).getTextContent());
            } catch (NullPointerException npe) { }
            try {
                telekomWebshopRunningFlag = Boolean.valueOf(document.getElementsByTagName("telekomWebshop").item(0).getTextContent());
            } catch (NullPointerException npe) { }
        } catch (Exception e) {
            e.printStackTrace();
            telekomHomePageRunningFlag = true;
            telekomWebRunningFlag = true;
            telekomWebshopRunningFlag = true;
        }

        //This statement is initialize the Rest Assured library to satisfy the response time expectations,
        //because the first request is always slow. It's a workaround.
        //If you remove this statement, the first test will be unreasonably slow.
        RestAssured.given().when().get("http://dummy.restapiexample.com").then().extract().response().getBody();
    }

    @BeforeMethod()
    private void startMethods(ITestResult result) {
        //Minden teszt előtt kerüljön a konzolra egy üzenet, amely tartalmazza az aktuális rendszer időd, azaz a teszt kezdetének időpontját.
        logger.log(INFO, "Telekom test is started: " + localDateTime());
        //Minden teszt előtt kerüljön a konzolra egy üzenet, amely tartalmazza az osztály nevét ahol a teszt fut és a teszt metódus nevét.
        logger.log(INFO, result.getTestClass().getName() + "." + result.getMethod().getMethodName());
    }

    @AfterClass
    private void endTests() {
        //Miután lefutott az összes teszt kerüljön a konzolra egy üzenet, amely tartalmazza az aktuális rendszer időd.
        logger.log(INFO, "The tests has ended: " + localDateTime());
    }

    @Test(enabled = false)
    public void helloWorld() {
        System.out.print("Hello World!");
    }

    @Test(enabled = false)
    public void helloWorld2() {
        System.out.print("Hello World2!");
    }

    @Test()
    public void telekomHomePage() {
        if (!telekomHomePageRunningFlag) skipTestExecution();

        testApi("https://telekom.hu/lakossagi");
    }

    @Test()
    public void telekomWeb() {
        if (!telekomWebRunningFlag) skipTestExecution();

        testApi("https://telekom.hu/web");
    }

    @Test()
    public void telekomWebshop() {
        if (!telekomWebshopRunningFlag) skipTestExecution();

        testApi("https://telekom.hu/webshop");
    }
}
