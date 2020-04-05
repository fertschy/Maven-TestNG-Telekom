package common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import static java.util.logging.Level.*;
import static logger.Logger.logger;
import static org.hamcrest.Matchers.lessThan;

public class Framework {
    public static String localDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm:ss");

        return dtf.format(LocalDateTime.now());
    }

    public static void testApi(String url) {
        Response res = restAssuredRequest(url);

        //Ha az api hívás után a response kód nem 200, akkor próbáljuk meg még egyszer elküldeni a requestet.
        if (res.statusCode() != 200) restAssuredRequest(url);
    }

    private static Response restAssuredRequest(String url) {
        //logger.log(INFO, "Start request.");
        Response res = given().
                config(RestAssured.config().sslConfig(
                        new SSLConfig().relaxedHTTPSValidation())).
                when().
                //Az api hívás esetén kerüljön loggolásra a konzolra:
                // - A request URL
                // - A request method
                // - A request header
                // - A request body
                log().uri().log().method().log().headers().log().body().
                get(url).
                then().
                //Minden RESPONSE esetén kerüljön logolásra a konzolra:
                // - A response kód
                // - A response body
                log().status().log().body().
                extract().
                response();
        //logger.log(INFO, "End request.");

        logger.log(INFO, "Response time: " + res.getTime()) + "ms";

        //Ha az api hívás után a response kód 200 minden esetben kerüljön a konzolra egy üzenet, hogy „OK”, ha 400 vagy 404 akkor „NOTOK”.
        switch (res.statusCode()) {
            case 200: logger.log(INFO, "OK"); break;
            case 400: case 404: logger.log(SEVERE, "NOTOK"); break;
        }

        return res;
    }
}
