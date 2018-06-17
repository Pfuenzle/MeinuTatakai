package seminarkurs.blume;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Leon on 27.02.2018.
 */

public class MultiPlayer {
    private MyGdxGame game;

    private MultiPlayerGameScreen mpgs;

    private String SERVER = NetworkPlayer.getGameServer();
    private int PORT;

    public static int getPlayerNr() {
        return playerNr;
    }

    public static void setPlayerNr(int playerNr) {
        MultiPlayer.playerNr = playerNr;
    }

    private static int playerNr = 0;

    private Socket socket;

    private GamePlayer localPlayer;

    private static GamePlayer enemy;

    private static String serverStatus;

    private boolean multiplayerRunning;

    private static boolean init = false;

    public static String getServerStatus() {
        return serverStatus;
    }

    private void setServerStatus(String serverStatusArg) {
        serverStatus = serverStatusArg;
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

    public void setEnemy(GamePlayer enemyArg) {
        enemy = enemyArg;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLooser() {
        return looser;
    }

    public void setLooser(String looser) {
        this.looser = looser;
    }

    public void dispose()
    {
        multiplayerRunning = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String winner = "";
    private String looser = "";

    private void parsePacket(String packet) {
        if(packet.length() < 2)
            return;
        if (packet.substring(0, 2).equals("10")) {
            try {
                enemy.setUsername(packet.substring(3));
                System.out.println("Set enemy username to " + packet.substring(3));
            }
            catch(Exception e)
            {

            }
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
            //12x1x88
            int player = Integer.parseInt(packet.substring(3, 4));
            try {
                if(player == getPlayerNr())
                    localPlayer.setHealth(Double.parseDouble(packet.substring(5)));
                else
                    enemy.setHealth(Double.parseDouble(packet.substring(5)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("13")) {
            try {
                enemy.setSpeed(Double.parseDouble(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("14")) {
            try {
                enemy.setPlayer(Integer.parseInt(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("15")) {
            try {
                enemy.setSkin(Integer.parseInt(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("16")) {
            try {
                enemy.setX(Double.parseDouble(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("17")) {
            try {
                enemy.setY(Double.parseDouble(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("18")) {
            try {
                enemy.setDirection(Integer.parseInt(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("19")) {
            try {
                enemy.setAction(Integer.parseInt(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("21")) {
            System.out.println("PlayerNr: " + packet.substring(3));
            try {
                playerNr = Integer.parseInt(packet.substring(3));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("99")) {
            //99x0000xuserx0000xuser2
            int winner_pos_start = 8;
            int winner_pos_end = winner_pos_start + Integer.parseInt(packet.substring(3, 7));
            int looser_pos_start = winner_pos_end + 6;
            int looser_pos_end = looser_pos_start + Integer.parseInt(packet.substring(winner_pos_end + 1, winner_pos_end + 5));
            try
            {
                this.setWinner(packet.substring(winner_pos_start, winner_pos_end));
                this.setLooser(packet.substring(looser_pos_start, looser_pos_end));
            }
            catch(Exception e)
            {

            }
            multiplayerRunning = false;
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

    public void startMultiPlayerThread() throws IOException{
        socket = new Socket(SERVER,PORT); //verbinde zu server
        multiplayerRunning = true;

        final DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

        outToServer.writeBytes("11x" + String.valueOf(localPlayer.getRP()) + "\n");
        outToServer.writeBytes("12x" + String.valueOf(localPlayer.getHealth()) + "\n");
        outToServer.writeBytes("13x" + String.valueOf(localPlayer.getSpeed()) + "\n");
        outToServer.writeBytes("14x" + String.valueOf(localPlayer.getPlayer()) + "\n");
        outToServer.writeBytes("15x" + String.valueOf(localPlayer.getSkin()) + "\n");
        outToServer.writeBytes("16x" + String.valueOf(localPlayer.getX()) + "\n");
        outToServer.writeBytes("17x" + String.valueOf(localPlayer.getY()) + "\n");
        outToServer.writeBytes("18x" + String.valueOf(localPlayer.getDirection()) + "\n");
        outToServer.writeBytes("10x" + localPlayer.getUsername() + "\n");

        new Thread(new Runnable() {
            public void run() {
                mpgs.exitLoadingScreen();
                boolean hasStartPos = false;
                BufferedReader inFromServer = null;
                try {
                    inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    dispose();
                    game.setScreen(new MainScreen(game));
                    dispose();
                    e.printStackTrace();
                }
                String packet = null;
                while(multiplayerRunning)
                {
                    try {
                            packet = inFromServer.readLine();
                        if(getPlayerNr() == 2 && !hasStartPos) {
                            getLocalPlayer().setX(1660);
                            getLocalPlayer().sendUpdate(socket);
                            hasStartPos = true;
                        }
                    } catch (IOException e) {
                        dispose();
                        game.setScreen(new MainScreen(game));
                        dispose();
                        e.printStackTrace();
                    } catch (Exception e) {
                        dispose();
                        game.setScreen(new MainScreen(game));
                        dispose();
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
                            outToServer.writeBytes("20x\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    dispose();
                    e.printStackTrace();
                }
                //packet = null;
            }
        }).start();
    }

    public MultiPlayer(int PORT, MultiPlayerGameScreen mpgs){
        System.out.println("Initializing Multiplayer");
        this.game = mpgs.getGame();
        this.mpgs = mpgs;
        this.PORT = PORT;

        localPlayer = new GamePlayer();

        localPlayer.setRP(NetworkPlayer.getRP());
        localPlayer.setUsername(NetworkPlayer.getUsername());
        localPlayer.setHealth(100);
        localPlayer.setSpeed(10);
        localPlayer.setPlayer(NetworkPlayer.getCharID());
        localPlayer.setSkin(0);
        localPlayer.setX(0);
        localPlayer.setY(0);

        enemy = new GamePlayer();

        /*(enemy.setRP(12344321);
        enemy.setUsername("gegnername");
        enemy.setHealth(100);
        enemy.setSpeed(10);
        enemy.setPlayer(1);
        enemy.setSkin(0);
        enemy.setX(0);
        enemy.setY(0);*/

        try {
            startMultiPlayerThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveLeft(float num) throws IOException{
        localPlayer.setX(localPlayer.getX() - num);
        localPlayer.setDirection(-1);
        localPlayer.setAction(1);
        localPlayer.sendUpdate(socket);
    }

    public void moveRight(float num) throws IOException{
        localPlayer.setX(localPlayer.getX() + num);
        localPlayer.setDirection(1);
        localPlayer.setAction(2);
        localPlayer.sendUpdate(socket);
    }

    public void jump(float num) throws IOException{
        localPlayer.setY(localPlayer.getY() + num);
        localPlayer.setAction(3);
        localPlayer.sendUpdate(socket);
    }

    public void onGround(float num) throws IOException{
        localPlayer.setY(localPlayer.getY() - num);
        localPlayer.setAction(4);
        localPlayer.sendUpdate(socket);
    }

    public void doTritt() throws IOException{
        final DataOutputStream outToClient = new DataOutputStream(socket .getOutputStream());
        localPlayer.setAction(5); //Setze Action auf Tritt
        outToClient.writeBytes("31x" + "\n"); //Sende Attacke-Paket //TODO BESSER
        localPlayer.sendUpdate(socket);
    }

    public void doSchlag() throws IOException{
        final DataOutputStream outToClient = new DataOutputStream(socket .getOutputStream());
        localPlayer.setAction(6); //Setze Action auf Schlag
        outToClient.writeBytes("31x" + "\n"); //Sende Attacke-Paket //TODO BESSER
        localPlayer.sendUpdate(socket);
    }

    public void doNothing() throws IOException{
        final DataOutputStream outToClient = new DataOutputStream(socket .getOutputStream());
        localPlayer.setAction(0);
        localPlayer.sendUpdate(socket);
    }


}
