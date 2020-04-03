package common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static logger.Logger.logger;

public class Framework {
    public static String localDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm:ss");

        return dtf.format(LocalDateTime.now());
    }

    public static Response restAssuredRequest(String url) {
        //logger.log(INFO, localDateTime());
        Response res = given().config(
                RestAssured.config().sslConfig(
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
                //Minden RESPONSE esetén kerüljön logolásra a konzolra: A response body
                log().body().
                extract().
                response();
        //logger.log(INFO, localDateTime());

        //Minden RESPONSE esetén kerüljön logolásra a konzolra: A response kód
        logger.log(INFO, "Status code: " + res.statusCode());

        //Ha az api hívás után a response kód 200 minden esetben kerüljön a konzolra egy üzenet, hogy „OK”, ha 400 vagy 404 akkor „NOTOK”.
        if (res.statusCode() == 200) { logger.log(INFO, "OK"); }
        else if (res.statusCode() == 400 || res.statusCode() == 404) { logger.log(SEVERE, "NOTOK"); }

        return res;
    }
}
