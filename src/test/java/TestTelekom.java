import static common.Framework.*;

import static logger.Logger.logger;
import static java.util.logging.Level.*;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestTelekom {
    @BeforeMethod()
    private void startMethods(ITestResult result) {
        //Minden teszt előtt kerüljön a konzolra egy üzenet, amely tartalmazza az aktuális rendszer időd, azaz a teszt kezdetének időpontját.
        logger.log(INFO, "Telekom test is started: - " + localDateTime());
        //Minden teszt előtt kerüljön a konzolra egy üzenet, amely tartalmazza az osztály nevét ahol a teszt fut és a teszt metódus nevét.
        logger.log(INFO, result.getTestClass().getName() + "." + result.getMethod().getMethodName());
    }

    @AfterClass
    private void endTests() {
        //Miután lefutott az összes teszt kerüljön a konzolra egy üzenet, amely tartalmazza az aktuális rendszer időd.
        logger.log(INFO, "The tests has ended! Local time: " + localDateTime());
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
        Response res = restAssuredRequest("https://telekom.hu/lakossagi");

        //Ha az api hívás utána a response kód nem 200, akkor próbáljuk meg még egyszer elküldeni a requestet.
        if (res.statusCode() != 200) restAssuredRequest("https://telekom.hu/lakossagi");
    }

    @Test()
    public void telekomWeb() {
        Response res = restAssuredRequest("https://telekom.hu/web");

        //Ha az api hívás után a response kód nem 200, akkor próbáljuk meg még egyszer elküldeni a requestet.
        if (res.statusCode() != 200) restAssuredRequest("https://telekom.hu/web");
    }

    @Test()
    public void telekomWebshop() {
        Response res = restAssuredRequest("https://telekom.hu/webshop");

        //Ha az api hívás utána a response kód nem 200, akkor próbáljuk meg még egyszer elküldeni a requestet.
        if (res.statusCode() != 200) restAssuredRequest("https://telekom.hu/webshop");
    }
}
