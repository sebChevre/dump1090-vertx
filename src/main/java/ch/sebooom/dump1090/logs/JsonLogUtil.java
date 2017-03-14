package ch.sebooom.dump1090.logs;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sce on 23.02.2017.
 */
public class JsonLogUtil {



    public static final String EMPTY_CORRELATION_ID = "EMPTY";
    public static final String CORRELATION_ID_ERROR = "ERROR";
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";


    public static Map<String, String> technicalLog(EventType eventType){
        Map<String, String> logMap = new HashMap<>();

        logMap.put("eventType",String.valueOf(eventType));
        logMap.put("logType",String.valueOf(eventType.logType()));

        return logMap;
    }

    public static Map<String, String> domainLog(EventType eventType,String correlationId){
        Map<String, String> logMap = new HashMap<>();

        logMap.put("eventType",String.valueOf(eventType));
        logMap.put("logType",String.valueOf(eventType.logType()));
        logMap.put("correlationId",correlationId);

        return logMap;
    }






    private String logDate(){
        Format formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(new Date());
    }




}