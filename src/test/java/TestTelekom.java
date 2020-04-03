import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestTelekom {

    @BeforeClass
    private void startTest() {
        System.out.println(common.Framework.localTime());
    }

    @Test
    public void helloWorld() {
        System.out.println("Hello World!");

    }

}
