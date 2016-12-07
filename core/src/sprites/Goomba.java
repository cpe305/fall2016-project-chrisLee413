package sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.clee186.mariobros.MarioBros;

import screens.PlayScreen;

public class Goomba extends Enemy {
  private float stateTime;
  private Animation walkAnimation;
  private Array<TextureRegion> frames;
  private boolean setToDestroy;
  private boolean destroyed;

  public Goomba(PlayScreen screen, float x, float y) {
    super(screen, x, y);

    frames = new Array<TextureRegion>();
    for (int i = 0; i < 2; i++) {
      frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
    }
    walkAnimation = new Animation(0.4f, frames);
    stateTime = 0;
    setBounds(getX(), getY(), 16 / MarioBros.PPM, 16 / MarioBros.PPM);

    setToDestroy = false;
    destroyed = false;
  }

  public void update(float dt) {
    stateTime += dt;
    if (setToDestroy && !destroyed) {
      world.destroyBody(b2body);
      destroyed = true;
      setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
    } else if (!destroyed) {
      setPosition(b2body.getPosition().x - getWidth() / 2,
          b2body.getPosition().y - getHeight() / 2);
      setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }
  }

  @Override
  protected void defineEnemy() {
    BodyDef bdef = new BodyDef();
    bdef.position.set(getX() + 16 / MarioBros.PPM, getY());
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body = world.createBody(bdef);

    FixtureDef fdef = new FixtureDef();
    CircleShape shape = new CircleShape();
    shape.setRadius(6 / MarioBros.PPM);
    fdef.filter.categoryBits = MarioBros.ENEMY_BIT;
    fdef.filter.maskBits = MarioBros.GROUND_BIT | MarioBros.COIN_BIT | MarioBros.BRICK_BIT
        | MarioBros.ENEMY_BIT | MarioBros.OBJECT_BIT | MarioBros.MARIO_BIT;

    fdef.shape = shape;
    b2body.createFixture(fdef);

    PolygonShape head = new PolygonShape();
    Vector2[] vertices = new Vector2[4];
    vertices[0] = new Vector2(-5, 8).scl(1 / MarioBros.PPM);
    vertices[1] = new Vector2(5, 8).scl(1 / MarioBros.PPM);
    vertices[2] = new Vector2(-3, 3).scl(1 / MarioBros.PPM);
    vertices[3] = new Vector2(3, 3).scl(1 / MarioBros.PPM);
    head.set(vertices);

    fdef.shape = head;
    fdef.restitution = 0.5f;
    fdef.filter.categoryBits = MarioBros.ENEMY_HEAD_BIT;
    b2body.createFixture(fdef).setUserData(this);
  }

  @Override
  public void hitOnHead() {
    setToDestroy = true;
  }

}