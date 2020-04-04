/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.File;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.logging.*;

import static java.util.logging.Level.*;


/**
 * @author Ferenc Kovács
 */
public class Logger {
    public final static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getName());

    //private static FileHandler fh = null;
    private static ConsoleHandler ch = null;

    static {
        /*File f = new File("logs/");
        if (!f.exists() && !f.isDirectory()) {
            f.mkdir();
        }
        */

        //SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");

        try {
            ch = new ConsoleHandler();
            ch.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    SimpleDateFormat logTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    Calendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(record.getMillis());
                    return logTime.format(cal.getTime())
                            + " - "
                            + (record.getLevel() == SEVERE ? "ERROR" : record.getLevel())
                            + " - "
                            + record.getSourceClassName().substring(
                                record.getSourceClassName().lastIndexOf(".")+1,
                                record.getSourceClassName().length())
                            + "."
                            + record.getSourceMethodName()
                            + "() - "
                            + record.getMessage().replace("\n", "") + "\n";
                }
            });

            /*
            fh = new FileHandler("logs/telekom_" + format.format(Calendar.getInstance().getTime()) + ".csv");
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    SimpleDateFormat logTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    Calendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(record.getMillis());
                    return logTime.format(cal.getTime())
                            + ";"
                            + (record.getLevel() == SEVERE ? "ERROR" : record.getLevel())
                            + ";"
                            + record.getSourceClassName().substring(
                                record.getSourceClassName().lastIndexOf(".")+1,
                                record.getSourceClassName().length())
                            + "."
                            + record.getSourceMethodName()
                            + "();"
                            + record.getMessage().replace("\n", " ") + "\n";
                }
            });
            */
        } catch (Exception e) {
            e.printStackTrace();
        }

        // change default handlers to ours
        java.util.logging.Logger root = java.util.logging.Logger.getLogger("");
        Handler[] handlers = root.getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            root.removeHandler(handlers[i]);
        }

        //logger.addHandler(fh);
        logger.addHandler(ch);
    }
}
