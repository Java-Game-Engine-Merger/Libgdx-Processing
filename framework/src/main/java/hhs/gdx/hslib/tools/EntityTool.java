package hhs.gdx.hslib.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import hhs.gdx.hslib.entities.BasicEntity;

public class EntityTool{
  public static void setSpriteBound(Sprite s,BasicEntity e) {
    s.setPosition(e.pos.x,e.pos.y);
    s.setSize(e.size.x,e.size.y);
  }
  public static void setSpriteRotation(Sprite s,BasicEntity e) {
    s.setCenter(e.center.x,e.center.y);
    s.setRotation(e.rotate);
  }
  public static boolean overlaps(BasicEntity r1,BasicEntity r2) {
    return Rectangle.tmp.set(r1.pos.x,r1.pos.y,r1.size.x,r1.size.y).overlaps(Rectangle.tmp2.set(r2.pos.x,r2.pos.y,r2.size.x,r2.size.y));
  }
  public static boolean testBoundInCamera(BasicEntity be,OrthographicCamera cam) {
    Vector2 pos=be.pos,size=be.size;
    float cw=cam.viewportWidth*cam.zoom/2;
    float ch=cam.viewportHeight*cam.zoom/2;
    return Rectangle.tmp.set(pos.x,pos.y,size.x,size.y)
      .overlaps(Rectangle.tmp2.set(cam.position.x-cw,cam.position.y-ch,cw*2,ch*2));
  }
}
