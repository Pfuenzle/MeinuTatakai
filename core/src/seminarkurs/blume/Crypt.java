package seminarkurs.blume;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Leon on 25.02.2018.
 */

public class Crypt {
    public static String applyMD5(String str){ //Hasht einen übergeben String mit MD5
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5"); //Initialisieren von MD5
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(str.getBytes()); //Übergeben der Nachrichricht die gehasht werden soll
        byte[] digest = md.digest(); //Hashen des Strings in einen Array
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) { //Schreiben des Arrays in einen Stringbuffer
            sb.append(String.format("%02x", b & 0xff));
        }
        String hash = sb.toString();
        return hash; //zurückgebend des Hashes
    }
}
