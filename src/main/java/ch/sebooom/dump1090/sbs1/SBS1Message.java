package ch.sebooom.dump1090.sbs1;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class SBS1Message {

    private static final String TCP_STRING_TOKEN_SEPARATOR = ",";
    private static final int TCP_STRING_TOKEN_SIZE = 22;
    public static final int ICAO_IDENT = 4;


    public static boolean validEntryString(String entryString){

        return entryString.split(TCP_STRING_TOKEN_SEPARATOR,-1).length
                == TCP_STRING_TOKEN_SIZE;

    }

    /**
     * Retourne le code icao si message ok, sinon retourne une chaine vide
     * @param entryString
     * @return
     */
    public static String getIcaoCode (String entryString) {

        String icao = "";

        if(validEntryString(entryString)){
            icao = entryString.split(TCP_STRING_TOKEN_SEPARATOR,-1)[ICAO_IDENT];
        }

        return icao;
    }
}
