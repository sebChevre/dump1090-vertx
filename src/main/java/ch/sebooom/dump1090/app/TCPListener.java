package ch.sebooom.dump1090.app;

import ch.sebooom.dump1090.logs.EventType;
import ch.sebooom.dump1090.properties.PropertiesKey;
import ch.sebooom.dump1090.sbs1.SBS1Message;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.net.NetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.Arrays;

import static ch.sebooom.dump1090.logs.EventType.TCP_LISTENNING;
import static ch.sebooom.dump1090.logs.EventType.VERTX_DEPLOY;
import static ch.sebooom.dump1090.logs.JsonLogUtil.domainLog;
import static ch.sebooom.dump1090.logs.JsonLogUtil.technicalLog;
import static net.logstash.logback.marker.Markers.appendEntries;


/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class TCPListener extends AbstractVerticle {

    private final static Logger logger = LoggerFactory.getLogger(TCPListener.class.getName());
    private static final String LINE_TOKENIZER = "\n";

    @Override
    public void start() throws Exception {

        logger.info(appendEntries(
                technicalLog(VERTX_DEPLOY)),"{} successfully deployed as verticle!");


        int port = config().getInteger(PropertiesKey.TCP_SERVER_PORT.key);
        String host = config().getString(PropertiesKey.TCP_SERVER_HOST.key);

        logger.info(appendEntries(
                technicalLog(TCP_LISTENNING)),"Starting tcp listenning on [{}:{}]",host,port);

        NetClient tcpClient = vertx.createNetClient();


        tcpClient.rxConnect(port,host).subscribe(
                //connection tcp ok
                sucess -> {

                    logger.info(appendEntries(
                            technicalLog(TCP_LISTENNING)),"TCP Connection successfully established!",host,port);

                    //observable sur les trames tcp
                    sucess.toObservable().subscribe(next -> {

                        String[] tcpLines = splitLines(next.getString(0,next.length()));

                        //System.out.println(tcpLines);
                        Observable.from(Arrays.asList(tcpLines))
                                .subscribe(line -> {

                                    EventType msgEventype;

                                    msgEventype = (SBS1Message.validEntryString(line))
                                            ? EventType.TCP_MESSAGE_VALIDATED : EventType.TCP_MESSAGE_REJECTED;

                                    logger.info(appendEntries(
                                            domainLog(TCP_LISTENNING,SBS1Message.getIcaoCode(line))),
                                            "TCP Message received:{}",line);

                                },
                                error->{

                                    logger.error(appendEntries(technicalLog(TCP_LISTENNING)),
                                            "Error during parsing lines from tcp message: {}",error.getMessage());

                                },
                                ()->{}
                        );
                    });
                },
                error -> {

                    logger.error(appendEntries(technicalLog(TCP_LISTENNING)),
                            "Problem during connect to tcp server: [{}:{}] - {} " +
                                    "- System exit now",host,port,error.getMessage());

                    System.exit(1);
                }
        );
    }


    private String[] splitLines(String tcpFrames){
        return tcpFrames.split(LINE_TOKENIZER);
    }
}
