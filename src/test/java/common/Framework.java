package common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Framework {
    public static String localTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        return dtf.format(LocalDateTime.now());
    }


}
