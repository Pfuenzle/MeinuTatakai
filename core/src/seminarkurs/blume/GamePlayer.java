package seminarkurs.blume;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.Serializable;

/**
 * Created by Leon on 27.02.2018.
 */


public class GamePlayer implements Serializable {

    private double health = 400; //max 1000
    private float speed = 1000;

    private int player;
    private String skin;

    private double x;
    private double y;

    private String name;
    private int RP;

    World world;
    Stage stage;

    public Stage getGamePlayerStage() {
        return stage;
    }

    public void setGamePlayerStage(Stage stage) {
        this.stage = stage;
    }

    Body body;
    GamePlayerActor actor;



    GamePlayer(Stage stage, World world, String skin){
        this.world = world;
        this.stage = stage;
        this.skin = skin;

        actor = new GamePlayerActor(this);

        body = new GameBodies(world).createPlayer(302,802);

    }
    void act(float delta){
        update();

        //body = new GameBodies(world).resizePlayer(body, actor.getRegionHight(),actor.getRegionWidth());
        actor.act(delta);
        actor.draw(stage.getBatch(),255f);
    }

    private void update(){
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;

        actor.drawX = body.getPosition().x- actor.getRegionWidth()/2;
        actor.drawY = body.getPosition().y - actor.getRegionHight()/2;
    }

    void doNothing(){
        body.setLinearVelocity(0,body.getLinearVelocity().y);
    }

    void doWalking(boolean isRight, float percX){
        actor.doWalking(isRight);
        //Todo: Body bewegen
        body.setLinearVelocity(speed*percX,body.getLinearVelocity().y);
    }
    void doJumping(){
        actor.doJumping();
        body.applyLinearImpulse(0,20000000,(float) x,(float) y+100,false);
        //body.applyForce();
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }


    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }


    public double getPosX() {
        return x;
    }

    public void setPosX(float x) {
        this.x = x;
    }


    public double getPosY() {
        return y;
    }

    public void setPosY(float y) {
        this.y = y;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRP() {
        return RP;
    }

    public void setRP(int RP) {
        this.RP = RP;
    }
}

