package ch.sebooom.dump1090.properties;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public enum PropertiesKey {

    TCP_SERVER_PORT("tcpserver.port"),
    TCP_SERVER_HOST("tcpserver.host");

    public final String key;

    PropertiesKey (String key){
        this.key = key;
    }
}
