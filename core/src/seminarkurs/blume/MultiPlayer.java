package seminarkurs.blume;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import jdk.nashorn.internal.runtime.ECMAException;

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

    DataOutputStream outToServer;

    public void dispose()
    {
        multiplayerRunning = false;
        try {
            socket.close(); //Beende Verbindung zu Server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String winner = "";
    private String looser = "";

    private void parsePacket(String packet) {
        if(packet.length() < 2)
            return;
        if (packet.substring(0, 2).equals("10")) { //Update Nutzernamen des Gegners
            try {
                enemy.setUsername(packet.substring(3));
                System.out.println("Set enemy username to " + packet.substring(3));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("11")) { //Update RP des Gegners
            try{
                enemy.setRP(Integer.parseInt(packet.substring(3)));
            }
            catch (Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("12")) { //Update Leben eines Spielers
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
        else if (packet.substring(0, 2).equals("13")) {  //Update Geschwindigkeit des Gegners
            try {
                enemy.setSpeed(Double.parseDouble(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("14")) {  //Update Charakter des Gegners
            try {
                enemy.setPlayer(Integer.parseInt(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("15")) {  //Update Skin des Gegners
            try {
                enemy.setSkin(Integer.parseInt(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("16")) {  //Update X-Position des Gegners
            try {
                enemy.setX(Double.parseDouble(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("17")) {  //Update Y-Position des Gegners
            try {
                enemy.setY(Double.parseDouble(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("18")) {  //Update Blickrichtung des Gegners
            try {
                enemy.setDirection(Integer.parseInt(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("19")) {  //Update Aktion des Gegners
            try {
                enemy.setAction(Integer.parseInt(packet.substring(3)));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("21")) {  //Update Spielernummer
            try {
                System.out.println("PlayerNr: " + packet.substring(3));
            }
            catch(Exception e)
            {

            }
            try {
                playerNr = Integer.parseInt(packet.substring(3));
            }
            catch(Exception e)
            {

            }
            init = true;
        }
        else if (packet.substring(0, 2).equals("99")) { //Spiel ist Vorbei
            //99x0000xuserx0000xuser2
            try {
                int winner_pos_start = 8;
                int winner_pos_end = winner_pos_start + Integer.parseInt(packet.substring(3, 7));
                int looser_pos_start = winner_pos_end + 6;
                int looser_pos_end = looser_pos_start + Integer.parseInt(packet.substring(winner_pos_end + 1, winner_pos_end + 5));
                try {
                    this.setWinner(packet.substring(winner_pos_start, winner_pos_end)); //Lese Gewinner
                    this.setLooser(packet.substring(looser_pos_start, looser_pos_end)); //Lese Verlierer
                } catch (Exception e) {

                }
            }
            catch(Exception e) //Bei Fehler Abbruch und ins Hauptmenü
            {
                mpgs.dispose();
                dispose();
                game.setScreen(new MainScreen(game));
            }
            multiplayerRunning = false;
        }
    }

    public void startMultiPlayerThread() throws IOException{
        socket = new Socket(SERVER,PORT); //Verbinde zu Server
        multiplayerRunning = true;

        outToServer = new DataOutputStream(socket.getOutputStream());

        //Sende alle eigenen Attribute am Anfang zum Server
        outToServer.writeBytes("11x" + String.valueOf(localPlayer.getRP()) + "\n");
        outToServer.writeBytes("12x" + String.valueOf(localPlayer.getHealth()) + "\n");
        outToServer.writeBytes("13x" + String.valueOf(localPlayer.getSpeed()) + "\n");
        outToServer.writeBytes("14x" + String.valueOf(localPlayer.getPlayer()) + "\n");
        outToServer.writeBytes("15x" + String.valueOf(localPlayer.getSkin()) + "\n");
        outToServer.writeBytes("16x" + String.valueOf(localPlayer.getX()) + "\n");
        outToServer.writeBytes("17x" + String.valueOf(localPlayer.getY()) + "\n");
        outToServer.writeBytes("18x" + String.valueOf(localPlayer.getDirection()) + "\n");
        outToServer.writeBytes("19x" + String.valueOf(localPlayer.getAction()) + "\n");
        outToServer.writeBytes("10x" + localPlayer.getUsername() + "\n");

        new Thread(new Runnable() {
            public void run() { //Starte neuen Thread
                mpgs.exitLoadingScreen();
                boolean hasStartPos = false;
                BufferedReader inFromServer = null;
                try {
                    inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    mpgs.dispose();
                    dispose();
                    game.setScreen(new MainScreen(game));
                    e.printStackTrace();
                }
                String packet = null;
                while(multiplayerRunning) //Schleife während Spiel läuft
                {
                    try {
                            packet = inFromServer.readLine(); //Lese von Server
                        if(getPlayerNr() == 2 && !hasStartPos) { //Spieler ist Spieler zwei, setze Position auf rechte Seite und sende Update
                            getLocalPlayer().setX(1460);
                            getLocalPlayer().sendUpdate(socket);
                            hasStartPos = true;
                        }//                            getLocalPlayer().sendUpdate(socket);
                        else if(getPlayerNr() == 1 && !hasStartPos) { //Spieler ist Spieler eins, bleibe auf linker Seite
                            getLocalPlayer().sendUpdate(socket);
                            hasStartPos = true;
                        }
                    } catch (IOException e) { //Bei Fehler Verbindung beenden und ins Hauptmenü zurück
                        mpgs.dispose();
                        dispose();
                        game.setScreen(new MainScreen(game));
                        e.printStackTrace();
                    } catch (Exception e) {
                        mpgs.dispose();
                        dispose();
                        game.setScreen(new MainScreen(game));
                        e.printStackTrace();
                    }
                    if(packet != null)
                        System.out.println(packet);
                    if(packet != null)
                        parsePacket(packet); //Paket auslesen
                    if(getEnemy().getUsername().equals(""))
                    {
                        try {
                        outToServer.writeBytes("20x\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }}
                }
                try { //Spiel ist vorbei, beenden
                    socket.close();
                } catch (IOException e) {
                    mpgs.dispose();
                    dispose();
                    game.setScreen(new MainScreen(game));
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public MultiPlayer(int PORT, MultiPlayerGameScreen mpgs){
        System.out.println("Initializing Multiplayer");
        this.game = mpgs.getGame();
        this.mpgs = mpgs;
        this.PORT = PORT;

        //Objekte für beide Spieler erstellen

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

        enemy.setUsername("");

        try {
            startMultiPlayerThread(); //Mulitplayer-Funktion aufrufen
        } catch (Exception e) {
            mpgs.dispose();
            dispose();
            game.setScreen(new MainScreen(game));
        }
    }

    public void moveLeft(float num) throws IOException{ //Bewege Spieler um "num" pixel nach links und sende Aktion und Update an Server
        localPlayer.setX(localPlayer.getX() - num);
        localPlayer.setDirection(-1);
        localPlayer.setAction(1);
        outToServer.writeBytes("16x" + String.valueOf(localPlayer.getX()) + "\n"); //Sende X-Position an Server
        outToServer.writeBytes("18x" + String.valueOf(localPlayer.getDirection()) + "\n"); //Sende Blickrichtung an Server
        outToServer.writeBytes("19x" + String.valueOf(localPlayer.getAction()) + "\n"); //Sende Aktion an Server
    }

    public void moveRight(float num) throws IOException{ //Bewege Spieler um "num" pixel nach rechts und sende Aktion und Update an Server
        localPlayer.setX(localPlayer.getX() + num);
        localPlayer.setDirection(1);
        localPlayer.setAction(2);
        outToServer.writeBytes("16x" + String.valueOf(localPlayer.getX()) + "\n"); //Sende X-Position an Server
        outToServer.writeBytes("18x" + String.valueOf(localPlayer.getDirection()) + "\n"); //Sende Blickrichtung an Server
        outToServer.writeBytes("19x" + String.valueOf(localPlayer.getAction()) + "\n"); //Sende Aktion an Server
    }

    public void jump(float num) throws IOException{ //Bewege Spieler um "num" nach oben und sende Aktion und Update an Server
        localPlayer.setY(localPlayer.getY() + num);
        localPlayer.setAction(3);
        outToServer.writeBytes("17x" + String.valueOf(localPlayer.getY()) + "\n"); //Sende Y-Position an Server
        outToServer.writeBytes("19x" + String.valueOf(localPlayer.getAction()) + "\n"); //Sende Aktion an Server
    }

    public void onGround(float num) throws IOException{//Bewege Spieler um "num" nach unten und sende Aktion und Update an Server
        localPlayer.setY(localPlayer.getY() - num);
        localPlayer.setAction(4);
        outToServer.writeBytes("17x" + String.valueOf(localPlayer.getY()) + "\n"); //Sende Y-Position an Server
        outToServer.writeBytes("19x" + String.valueOf(localPlayer.getAction()) + "\n"); //Sende Aktion an Server
    }

    public void doTritt() throws IOException{ //Spiele Tretanimation und sende Aktion an Server
        final DataOutputStream outToClient = new DataOutputStream(socket .getOutputStream());
        localPlayer.setAction(5); //Setze Action auf Tritt
        outToServer.writeBytes("19x" + String.valueOf(localPlayer.getAction()) + "\n"); //Sende Aktion an Server
        outToClient.writeBytes("31x" + "\n"); //Sende Attacke-Paket
    }

    public void doSchlag() throws IOException{ //Spiele Schlaganimation und sende Aktion an Server
        final DataOutputStream outToClient = new DataOutputStream(socket .getOutputStream());
        localPlayer.setAction(6); //Setze Action auf Schlag
        outToServer.writeBytes("19x" + String.valueOf(localPlayer.getAction()) + "\n"); //Sende Aktion an Server
        outToClient.writeBytes("31x" + "\n"); //Sende Attacke-Paket
    }

    public void doNothing() throws IOException{ //Sende nichts-Aktion an Server
        localPlayer.setAction(0);
        outToServer.writeBytes("19x" + String.valueOf(localPlayer.getAction()) + "\n"); //Sende Aktion an Server
    }


}
