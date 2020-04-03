import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import logger.Logger;
import static java.util.logging.Level.*;

public class TestTelekom {
    @BeforeClass(alwaysRun = true)
    private void startTests() {
        Logger.log.log(INFO,"Telekom tests are started!");
    }

    @BeforeMethod
    private void startMethods(ITestResult result) {
        Logger.log.log(INFO, "Test started: " + result.getTestClass().getName() + "." + result.getMethod().getMethodName());
    }

    @Test
    public void helloWorld() {
        System.out.print("Hello World!");
    }

    @Test
    public void helloWorld2() {
        System.out.print("Hello World2!");
    }



}
