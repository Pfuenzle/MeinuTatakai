package seminarkurs.blume;

import java.io.Serializable;

public class GamePlayer implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public GamePlayer()
    {

    }

    private String username;
    private int RP;

    private double health;
    private double speed;

    private int player;
    private int skin;

    private double x;
    private double y;

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
}
