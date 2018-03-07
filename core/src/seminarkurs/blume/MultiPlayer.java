package seminarkurs.blume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by Leon on 27.02.2018.
 */

public class MultiPlayer {
    private String SERVER = "seminarkurs.pfuenzle.io";
    private int PORT;

    private Socket socket;

    private GamePlayer enemy;

    private String serverStatus;

    private boolean multiplayerRunning;

    public String getServerStatus() {
        return serverStatus;
    }

    private void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public GamePlayer getEnemy() {
        return enemy;
    }

    public void setEnemy(GamePlayer enemy) {
        this.enemy = enemy;
    }






    private void parsePacket(String packet)
    {
        if(packet.substring(0, 2).equals("10"))
            handleStatus(packet);
        else if(packet.substring(0, 2).equals("99"))
            multiplayerRunning = false;
        else if(packet.substring(0, 2).equals("11"))
            handleEnemyPacket(packet);
        else if(packet.substring(0, 2).equals("12"))
            handleLocalPacket(packet);
    }

    private void handleEnemyPacket(String packet)
    {

    }

    private void handleLocalPacket(String packet)
    {

    }

    private void handleStatus(String packet)
    {
        setServerStatus(packet.substring(3, packet.length()));
    }

    public void startMultiPlayerThread() throws IOException, ClassNotFoundException {
        socket = new Socket(SERVER,PORT);
        multiplayerRunning = true;

        ObjectInputStream enemyObjectInput = new ObjectInputStream(socket.getInputStream());
        GamePlayer enemy =(GamePlayer) enemyObjectInput.readObject();

        new Thread(new Runnable() {
            public void run() {
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(System.in));
                String packet = null;
                while(multiplayerRunning)
                {
                    try {
                        packet = inFromServer.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(packet != null)
                        parsePacket(packet);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void startMultiPlayer()
    {

    }

    public MultiPlayer(int PORT)
    {
        this.PORT = PORT;
    }

}
