package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Leon on 01.02.2018.
 */

public class NetworkPlayer {
    public static String getMainServer() { //Bekomme IP des Hautpservers
        return MAIN_SERVER;
    }

    public static String getGameServer() { //Bekomme IP des Spieleserver
        return GAME_SERVER;
    }

    private static final String MAIN_SERVER = "seminarkurs.pfuenzle.io"; //IP des Hautpservers

    private static final String GAME_SERVER = "meinutatakai.pfuenzle.io"; //IP des Spieleservers

    public static String getUsername() {
        return username;
    } //Bekomme Username

    public static void setUsername(String username_in) {
        username = username_in;
    }

    public static  int getRP() {
        return RP;
    } //Bekomme RP des Spielers

    public static void setRP(int RP_in) {
        RP = RP_in;
    }

    public static int getWins() {
        return wins;
    } //Bekomme Wins des Spielers

    public static void setWins(int wins_in) {
        wins = wins_in;
    }

    public static int getLosses() {
        return losses;
    } //Bekomme Niederlagen des Spielers

    public static void setLosses(int losses_in) {
        losses = losses_in;
    }


    private static String username = "Fehler";
    private static int RP = 1337;
    private static int wins = 0;
    private static int losses = 0;


    //Network
    private static String SESSION = "0";
    private static String HWID;

    public static String getSESSION() {
        return SESSION;
    } //Bekomme aktuelle Session des Spielers

    public static void setSESSION(String SESSION) {
        NetworkPlayer.SESSION = SESSION;
    }

    public static String getHWID() {
        return HWID;
    } //Bekomme HWID des Geräts

    public static void setHWID(String HWID) {
        NetworkPlayer.HWID = HWID;
    }

    public static String fill(int number, int length) //Füllt 'number' zu 'length' stellen auf (5 -> 0005)
    {
        String ret = "";
        String number_str = String.valueOf(number);
        String[] number_arr = number_str.split("");
        for(int i = 0; i < length - number_str.length(); i++)
        {
            ret = ret + "0";
        }
        for(int i = 0; i < number_str.length() + 1; i++)
        {
            ret = ret + number_arr[i];
        }
        return ret;
    }

    public static void update()
    {
        String packettype = "1x0";
        String packet = packettype + "x" + username;
        SocketHints socketHints = new SocketHints();
        Socket socket = null;
        try
        {
            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "seminarkurs.pfuenzle.io", 1337, socketHints);
        }
        catch(com.badlogic.gdx.utils.GdxRuntimeException e)
        {
            return;
        }
        try {
            socket.getOutputStream().write(packet.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        final Socket finalSocket = socket;
        new Thread(new Runnable() {
            @Override
            public void run () {
                String resp;
                try {
                    resp = new BufferedReader(new InputStreamReader(finalSocket.getInputStream())).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    e.toString();
                    Gdx.app.log("UserPacket", e.toString());
                    return;
                }
                UserPacket user = new UserPacket(resp);
                if(user.getRet()) {
                    setRP(user.getRP());
                    setWins(getWins());
                    setLosses(user.getLosses());
                }

            }
        }).start();
    }

    public static int getCharID() { //Bekomme ID des Characters des Spielers
        return charID;
    }

    public static void setCharID(int charID) {
        NetworkPlayer.charID = charID;
    }

    private static int charID = 1; //ID des Characters, den der Spieler ausgewählt hat

    public static int getMapID() { //Bekomme ID des Characters des Spielers
        return mapID;
    }

    public static void setMapID(int mapID) {
        NetworkPlayer.mapID = mapID;
    }

    private static int mapID = 0; //ID der Map, den der Spieler ausgewählt hat
}
