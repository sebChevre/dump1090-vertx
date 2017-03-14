package ch.sebooom.dump1090.properties;

import ch.sebooom.dump1090.logs.EventType;
import ch.sebooom.dump1090.logs.JsonLogUtil;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static ch.sebooom.dump1090.logs.EventType.PROPERTIES;
import static ch.sebooom.dump1090.logs.JsonLogUtil.technicalLog;
import static net.logstash.logback.marker.Markers.appendEntries;


/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class AppProperties {
    private final static Logger logger = LoggerFactory.getLogger(AppProperties.class.getName());
    private static Properties properties = new Properties();
    public final static String PROPERTIES_PATH = "./sconfig/config.properties";

    public static JsonObject  get(){

        logger.info(appendEntries(
                technicalLog(PROPERTIES)),
                "Trying to load properties file [{}]...",PROPERTIES_PATH);

        loadPropertiesFile();

        JsonObject jsonProp = new JsonObject();
        jsonProp.put(PropertiesKey.TCP_SERVER_HOST.key,properties.getProperty(PropertiesKey.TCP_SERVER_HOST.key));
        jsonProp.put(PropertiesKey.TCP_SERVER_PORT.key,
                Integer.valueOf(properties.getProperty(PropertiesKey.TCP_SERVER_PORT.key)));
        return jsonProp;

    }

    private static void loadPropertiesFile() {
        try {
            properties.load(new FileInputStream(PROPERTIES_PATH));

            logger.info(appendEntries(JsonLogUtil.technicalLog(EventType.PROPERTIES)),
                    "Properties file successfuly loaded!");

        } catch (IOException e) {

            logger.error(appendEntries(JsonLogUtil.technicalLog(EventType.PROPERTIES)),
                    "Error during loading properties file [{}] - System exit now",PROPERTIES_PATH);

            System.exit(1);
        }
    }
}
