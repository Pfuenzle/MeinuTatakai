package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.SocketHints;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Leon on 27.02.2018.
 */

public class MultiPlayer {
    private String SERVER = "89.245.247.244";
    private int PORT;

    private Socket socket;

    private GamePlayer localPlayer;

    public static GamePlayer enemy;

    private static String serverStatus;

    private boolean multiplayerRunning;

    private static boolean init = false;

    public static String getServerStatus() {
        return serverStatus;
    }

    private void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public GamePlayer getLocalPlayer() {
        return localPlayer;
    }

    public void setLocalPlayer(GamePlayer localPlayer) {
        this.localPlayer = localPlayer;
    }

    public GamePlayer getEnemy() {
        return enemy;
    }

    public void setEnemy(GamePlayer enemy) {
        this.enemy = enemy;
    }

    private static void parsePacket(String packet) {
        if(packet.length() < 2)
            return;
        if (packet.substring(0, 2).equals("10")) {
            enemy.setUsername(packet.substring(3));
            System.out.println("Set enemy username to " + packet.substring(3));
            init = true;
        }
        else if (packet.substring(0, 2).equals("11")) {
            try{
                enemy.setRP(Integer.parseInt(packet.substring(3)));
            }
            catch (Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("12")) {
            enemy.setHealth(Double.parseDouble(packet.substring(3)));
            init = true;
        }
        else if (packet.substring(0, 2).equals("13")) {
            enemy.setSpeed(Double.parseDouble(packet.substring(3)));
            init = true;
        }
        else if (packet.substring(0, 2).equals("14")) {
            enemy.setPlayer(Integer.parseInt(packet.substring(3)));
            init = true;
        }
        else if (packet.substring(0, 2).equals("15")) {
            enemy.setSkin(Integer.parseInt(packet.substring(3)));
            init = true;
        }
        else if (packet.substring(0, 2).equals("16")) {
            enemy.setX(Double.parseDouble(packet.substring(3)));
            init = true;
        }
        else if (packet.substring(0, 2).equals("17")) {
            enemy.setY(Double.parseDouble(packet.substring(3)));
            init = true;
        }
        else if (packet.substring(0, 2).equals("18")) {
            enemy.setDirection(Integer.parseInt(packet.substring(3)));
            init = true;
        }
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

    public void startMultiPlayerThread() throws IOException, ClassNotFoundException, InterruptedException {
        socket = new Socket(SERVER,PORT); //verbinde zu server
        multiplayerRunning = true;

        final DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

        outToClient.writeBytes("11x" + String.valueOf(localPlayer.getRP()) + "\n");
        outToClient.writeBytes("12x" + String.valueOf(localPlayer.getHealth()) + "\n");
        outToClient.writeBytes("13x" + String.valueOf(localPlayer.getSpeed()) + "\n");
        outToClient.writeBytes("14x" + String.valueOf(localPlayer.getPlayer()) + "\n");
        outToClient.writeBytes("15x" + String.valueOf(localPlayer.getSkin()) + "\n");
        outToClient.writeBytes("16x" + String.valueOf(localPlayer.getX()) + "\n");
        outToClient.writeBytes("17x" + String.valueOf(localPlayer.getY()) + "\n");
        outToClient.writeBytes("18x" + String.valueOf(localPlayer.getDirection()) + "\n");
        outToClient.writeBytes("10x" + localPlayer.getUsername() + "\n");

        new Thread(new Runnable() {
            public void run() {
                BufferedReader inFromServer = null;
                try {
                    inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String packet = null;
                while(multiplayerRunning)
                {
                    try {
                        packet = inFromServer.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Packet received: ");
                    if(packet != null)
                        System.out.println(packet);
                    if(packet != null)
                        parsePacket(packet);
                    if(!init/* || enemy.getUsername().equals("null")*/)
                    {
                        try {
                            outToClient.writeBytes("19x\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //packet = null;
            }
        }).start();
    }

    public MultiPlayer(int PORT) throws IOException, ClassNotFoundException {
        this.PORT = PORT;

        localPlayer = new GamePlayer();

        localPlayer.setRP(NetworkPlayer.getRP());
        localPlayer.setUsername(NetworkPlayer.getUsername());
        localPlayer.setHealth(100);
        localPlayer.setSpeed(10);
        localPlayer.setPlayer(1);
        localPlayer.setSkin(0);
        localPlayer.setX(0);
        localPlayer.setX(0);

        enemy = new GamePlayer();

        /*enemy.setRP(12344321);
        enemy.setUsername("gegnername");
        enemy.setHealth(100);
        enemy.setSpeed(10);
        enemy.setPlayer(1);
        enemy.setSkin(0);
        enemy.setX(0);
        enemy.setX(0);*/

        try {
            startMultiPlayerThread();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveLeft(float num) throws IOException, InterruptedException {
        localPlayer.setX(localPlayer.getX() - num);
        localPlayer.setDirection(-1);
        localPlayer.sendUpdate(socket);
    }

    public void moveRight(float num) throws IOException, InterruptedException {
        localPlayer.setX(localPlayer.getX() + num);
        localPlayer.setDirection(1);
        localPlayer.sendUpdate(socket);
    }

}
