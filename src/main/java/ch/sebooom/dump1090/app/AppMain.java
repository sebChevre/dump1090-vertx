package ch.sebooom.dump1090.app;

import ch.sebooom.dump1090.properties.AppProperties;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ch.sebooom.dump1090.logs.EventType.VERTX_DEPLOY;
import static ch.sebooom.dump1090.logs.JsonLogUtil.technicalLog;
import static net.logstash.logback.marker.Markers.appendEntries;

//import java.util.logging.Logger;


/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class AppMain extends AbstractVerticle{

    //private final static Logger logger = Logger.getLogger(AppMain.class.getName());
    private static final Logger logger = LoggerFactory.getLogger(AppMain.class.getName());
    private static JsonObject properties;

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        logger.info(appendEntries(
                technicalLog(VERTX_DEPLOY)),
                "Deploying {} as verticle...",TCPListener.class.getName());


        properties = AppProperties.get();

        DeploymentOptions options = new DeploymentOptions()
                .setConfig(properties);

        vertx.deployVerticle(TCPListener.class.getName(),options);
    }
}
