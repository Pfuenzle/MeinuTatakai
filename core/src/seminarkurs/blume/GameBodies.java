package seminarkurs.blume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameBodies {

    private final World world;
    Body player,ground,wall;
    float hight, width;

    GameBodies(World world){
        this.world = world;
    }
    Body createGround() {
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(Gdx.graphics.getWidth()/2,0);
        groundDef.fixedRotation = true;
        // groundDef.angle = 90;
        ground = world.createBody(groundDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Gdx.graphics.getWidth()/2,20);
        ground.createFixture(shape,1f);
        shape.dispose();


        return ground;
    }
    Body createWall(boolean right) {
        BodyDef wallDef = new BodyDef();
        wallDef.type = BodyDef.BodyType.StaticBody;
        if(right)wallDef.position.set(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);
            else wallDef.position.set(0,Gdx.graphics.getHeight()/2);
        wallDef.fixedRotation = true;
        // groundDef.angle = 90;
        wall = world.createBody(wallDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20,Gdx.graphics.getHeight()/2);
        wall.createFixture(shape,1f);
        shape.dispose();


        return wall;
    }

    Body createPlayer(int posX,int posY){
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(posX,posY);
        playerDef.fixedRotation = true;
        player = world.createBody(playerDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(302/2,802/2);
        player.createFixture(shape,1f);

        return player;
    }
    public Body resizePlayer(Body player, float hight,float width){
        this.player = player;
        player.destroyFixture(player.getFixtureList().first());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hight,width);
        player.createFixture(shape,1f);
        return player;
    }
}
