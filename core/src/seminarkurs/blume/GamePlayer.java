package seminarkurs.blume;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GamePlayer{

    boolean isFilled = false;

    public GamePlayer()
    {
        username = null;
    }

    private String username;
    private int RP;

    private double health;
    private double speed;

    private int player;
    private int skin;

    private double x = 0;
    private double y = 0;

    private int direction;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    private int action; //0 = none, 1 == left, 2 == right, 3 == jump, 4 == jump_down, 5 = tritt, 6 = schlag

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRP() {
        return RP;
    }

    public void setRP(int RP) {
        this.RP = RP;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }


    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }


    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void sendUpdate(Socket s) throws IOException
    {
        Socket socket = s;
        final DataOutputStream outToClient = new DataOutputStream(socket .getOutputStream());

        outToClient.writeBytes("10x" + getUsername() + "\n");
        System.out.println("Sent Username");
        outToClient.writeBytes("11x" + String.valueOf(getRP()) + "\n");
        System.out.println("Sent RP");
        outToClient.writeBytes("12x" + String.valueOf(getHealth()) + "\n");
        System.out.println("Sent health");
        outToClient.writeBytes("13x" + String.valueOf(getSpeed()) + "\n");
        System.out.println("Sent speed");
        outToClient.writeBytes("14x" + String.valueOf(getPlayer()) + "\n");
        System.out.println("Sent player");
        outToClient.writeBytes("15x" + String.valueOf(getSkin()) + "\n");
        System.out.println("Sent skin");
        outToClient.writeBytes("16x" + String.valueOf(getX()) + "\n");
        System.out.println("Sent x");
        outToClient.writeBytes("17x" + String.valueOf(getY()) + "\n");
        System.out.println("Sent y");
        outToClient.writeBytes("18x" + String.valueOf(getDirection()) + "\n");
        System.out.println("Sent direction");
        outToClient.writeBytes("19x" + String.valueOf(getAction()) + "\n");
        System.out.println("Sent action");

    }

}
