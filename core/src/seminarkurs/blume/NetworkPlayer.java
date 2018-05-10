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
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username_in) {
        username = username_in;
    }

    public static  int getRP() {
        return RP;
    }

    public static void setRP(int RP_in) {
        RP = RP_in;
    }

    public static int getWins() {
        return wins;
    }

    public static void setWins(int wins_in) {
        wins = wins_in;
    }

    public static int getLosses() {
        return losses;
    }

    public static void setLosses(int losses_in) {
        losses = losses_in;
    }


    private static String username = "Fehler";
    private static int RP = 1337;
    private static int wins = 88;
    private static int losses = -2;


    //Network
    private static String SESSION = "0";
    private static String HWID;

    public static String getSESSION() {
        return SESSION;
    }

    public static void setSESSION(String SESSION) {
        NetworkPlayer.SESSION = SESSION;
    }

    public static String getHWID() {
        return HWID;
    }

    public static void setHWID(String HWID) {
        NetworkPlayer.HWID = HWID;
    }

    public static String fill(int number, int length)
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

}
