package pama1234.gdx.util.wrapper;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;

/**
 * 带返回值的方法使用父类的返回结果，基本上没法正常使用
 */
@Deprecated
public class ActorCenter extends Actor{
  public LinkedList<Actor> list=new LinkedList<>();

  @Override
  public void act(float delta) {
    for(Actor e:list) e.act(delta);
    super.act(delta);
  }

  @Override
  public void addAction(Action action) {
    for(Actor e:list) e.addAction(action);
    super.addAction(action);
  }

  @Override
  public boolean addCaptureListener(EventListener listener) {
    for(Actor e:list) e.addCaptureListener(listener);
    return super.addCaptureListener(listener);
  }

  @Override
  public boolean addListener(EventListener listener) {
    for(Actor e:list) e.addListener(listener);
    return super.addListener(listener);
  }

  @Override
  public boolean ancestorsVisible() {
    for(Actor e:list) e.ancestorsVisible();
    return super.ancestorsVisible();
  }

  @Override
  public boolean ascendantsVisible() {
    for(Actor e:list) e.ascendantsVisible();
    return super.ascendantsVisible();
  }

  @Override
  public void clear() {
    for(Actor e:list) e.clear();
    super.clear();
  }

  @Override
  public void clearActions() {
    for(Actor e:list) e.clearActions();
    super.clearActions();
  }

  @Override
  public void clearListeners() {
    for(Actor e:list) e.clearListeners();
    super.clearListeners();
  }

  @Override
  public boolean clipBegin() {
    for(Actor e:list) e.clipBegin();
    return super.clipBegin();
  }

  @Override
  public boolean clipBegin(float x,float y,float width,float height) {
    for(Actor e:list) e.clipBegin(x,y,width,height);
    return super.clipBegin(x,y,width,height);
  }

  @Override
  public void clipEnd() {
    for(Actor e:list) e.clipEnd();

    super.clipEnd();
  }

  @Override
  public Actor debug() {
    for(Actor e:list) e.debug();
    return super.debug();
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    for(Actor e:list) e.draw(batch,parentAlpha);
    super.draw(batch,parentAlpha);
  }

  @Override
  public void drawDebug(ShapeRenderer shapes) {
    for(Actor e:list) e.drawDebug(shapes);
    super.drawDebug(shapes);
  }

  @Override
  protected void drawDebugBounds(ShapeRenderer shapes) {
    // for(Actor e:list) e.
    super.drawDebugBounds(shapes);
  }

  @Override
  public boolean fire(Event event) {
    for(Actor e:list) e.fire(event);
    return super.fire(event);
  }

  @Override
  public <T extends Actor> T firstAscendant(Class<T> type) {
    for(Actor e:list) e.firstAscendant(type);
    return super.firstAscendant(type);
  }

  @Override
  public Array<Action> getActions() {
    for(Actor e:list) e.getActions();
    return super.getActions();
  }

  @Override
  public DelayedRemovalArray<EventListener> getCaptureListeners() {
    for(Actor e:list) e.getCaptureListeners();
    return super.getCaptureListeners();
  }

  @Override
  public Color getColor() {
    for(Actor e:list) e.getColor();
    return super.getColor();
  }

  @Override
  public boolean getDebug() {
    for(Actor e:list) e.getDebug();
    return super.getDebug();
  }

  @Override
  public float getHeight() {
    for(Actor e:list) e.getHeight();
    return super.getHeight();
  }

  @Override
  public DelayedRemovalArray<EventListener> getListeners() {
    for(Actor e:list) e.getListeners();
    return super.getListeners();
  }

  @Override
  public String getName() {
    for(Actor e:list) e.getName();
    return super.getName();
  }

  @Override
  public float getOriginX() {
    for(Actor e:list) e.getOriginX();
    return super.getOriginX();
  }

  @Override
  public float getOriginY() {
    for(Actor e:list) e.getOriginY();
    return super.getOriginY();
  }

  @Override
  public Group getParent() {
    for(Actor e:list) e.getParent();
    return super.getParent();
  }

  @Override
  public float getRight() {
    for(Actor e:list) e.getRight();
    return super.getRight();
  }

  @Override
  public float getRotation() {
    for(Actor e:list) e.getRotation();
    return super.getRotation();
  }

  @Override
  public float getScaleX() {
    for(Actor e:list) e.getScaleX();
    return super.getScaleX();
  }

  @Override
  public float getScaleY() {
    for(Actor e:list) e.getScaleY();
    return super.getScaleY();
  }

  @Override
  public Stage getStage() {
    for(Actor e:list) e.getStage();
    return super.getStage();
  }

  @Override
  public float getTop() {
    for(Actor e:list) e.getTop();
    return super.getTop();
  }

  @Override
  public Touchable getTouchable() {
    for(Actor e:list) e.getTouchable();
    return super.getTouchable();
  }

  @Override
  public Object getUserObject() {
    for(Actor e:list) e.getUserObject();
    return super.getUserObject();
  }

  @Override
  public float getWidth() {
    for(Actor e:list) e.getWidth();
    return super.getWidth();
  }

  @Override
  public float getX() {
    for(Actor e:list) e.getX();
    return super.getX();
  }

  @Override
  public float getX(int alignment) {
    for(Actor e:list) e.getX(alignment);
    return super.getX(alignment);
  }

  @Override
  public float getY() {
    for(Actor e:list) e.getY();
    return super.getY();
  }

  @Override
  public float getY(int alignment) {
    for(Actor e:list) e.getY(alignment);
    return super.getY(alignment);
  }

  @Override
  public int getZIndex() {
    for(Actor e:list) e.getZIndex();
    return super.getZIndex();
  }

  @Override
  public boolean hasActions() {
    for(Actor e:list) e.hasActions();
    return super.hasActions();
  }

  @Override
  public boolean hasKeyboardFocus() {
    for(Actor e:list) e.hasKeyboardFocus();
    return super.hasKeyboardFocus();
  }

  @Override
  public boolean hasParent() {
    for(Actor e:list) e.hasParent();
    return super.hasParent();
  }

  @Override
  public boolean hasScrollFocus() {
    for(Actor e:list) e.hasScrollFocus();
    return super.hasScrollFocus();
  }

  @Override
  public Actor hit(float x,float y,boolean touchable) {
    for(Actor e:list) e.hit(x,y,touchable);
    return super.hit(x,y,touchable);
  }

  @Override
  public boolean isAscendantOf(Actor actor) {
    for(Actor e:list) e.isAscendantOf(actor);
    return super.isAscendantOf(actor);
  }

  @Override
  public boolean isDescendantOf(Actor actor) {
    for(Actor e:list) e.isDescendantOf(actor);
    return super.isDescendantOf(actor);
  }

  @Override
  public boolean isTouchFocusListener() {
    for(Actor e:list) e.isTouchFocusListener();
    return super.isTouchFocusListener();
  }

  @Override
  public boolean isTouchFocusTarget() {
    for(Actor e:list) e.isTouchFocusTarget();
    return super.isTouchFocusTarget();
  }

  @Override
  public boolean isTouchable() {
    for(Actor e:list) e.isTouchable();
    return super.isTouchable();
  }

  @Override
  public boolean isVisible() {
    for(Actor e:list) e.isVisible();
    return super.isVisible();
  }

  @Override
  public Vector2 localToActorCoordinates(Actor actor,Vector2 localCoords) {
    for(Actor e:list) e.localToActorCoordinates(actor,localCoords);
    return super.localToActorCoordinates(actor,localCoords);
  }

  @Override
  public Vector2 localToAscendantCoordinates(Actor ascendant,Vector2 localCoords) {
    for(Actor e:list) e.localToAscendantCoordinates(ascendant,localCoords);
    return super.localToAscendantCoordinates(ascendant,localCoords);
  }

  @Override
  public Vector2 localToParentCoordinates(Vector2 localCoords) {
    for(Actor e:list) e.localToParentCoordinates(localCoords);
    return super.localToParentCoordinates(localCoords);
  }

  @Override
  public Vector2 localToScreenCoordinates(Vector2 localCoords) {
    for(Actor e:list) e.localToScreenCoordinates(localCoords);
    return super.localToScreenCoordinates(localCoords);
  }

  @Override
  public Vector2 localToStageCoordinates(Vector2 localCoords) {
    for(Actor e:list) e.localToStageCoordinates(localCoords);
    return super.localToStageCoordinates(localCoords);
  }

  @Override
  public void moveBy(float x,float y) {
    for(Actor e:list) e.moveBy(x,y);
    super.moveBy(x,y);
  }

  @Override
  public boolean notify(Event event,boolean capture) {
    for(Actor e:list) e.notify();
    return super.notify(event,capture);
  }

  @Override
  public Vector2 parentToLocalCoordinates(Vector2 parentCoords) {
    for(Actor e:list) e.parentToLocalCoordinates(parentCoords);
    return super.parentToLocalCoordinates(parentCoords);
  }

  @Override
  protected void positionChanged() {
    // for(Actor e:list) e.
    super.positionChanged();
  }

  @Override
  public boolean remove() {
    for(Actor e:list) e.remove();
    return super.remove();
  }

  @Override
  public void removeAction(Action action) {
    for(Actor e:list) e.removeAction(action);
    super.removeAction(action);
  }

  @Override
  public boolean removeCaptureListener(EventListener listener) {
    for(Actor e:list) e.removeCaptureListener(listener);
    return super.removeCaptureListener(listener);
  }

  @Override
  public boolean removeListener(EventListener listener) {
    for(Actor e:list) e.removeListener(listener);
    return super.removeListener(listener);
  }

  @Override
  public void rotateBy(float amountInDegrees) {
    for(Actor e:list) e.rotateBy(amountInDegrees);
    super.rotateBy(amountInDegrees);
  }

  @Override
  protected void rotationChanged() {
    // for(Actor e:list) e.
    super.rotationChanged();
  }

  @Override
  public void scaleBy(float scale) {
    for(Actor e:list) e.scaleBy(scale);
    super.scaleBy(scale);
  }

  @Override
  public void scaleBy(float scaleX,float scaleY) {
    for(Actor e:list) e.scaleBy(scaleX,scaleY);
    super.scaleBy(scaleX,scaleY);
  }

  @Override
  protected void scaleChanged() {
    // for(Actor e:list) e.
    super.scaleChanged();
  }

  @Override
  public Vector2 screenToLocalCoordinates(Vector2 screenCoords) {
    for(Actor e:list) e.screenToLocalCoordinates(screenCoords);
    return super.screenToLocalCoordinates(screenCoords);
  }

  @Override
  public void setBounds(float x,float y,float width,float height) {
    for(Actor e:list) e.setBounds(x,y,width,height);
    super.setBounds(x,y,width,height);
  }

  @Override
  public void setColor(Color color) {
    for(Actor e:list) e.setColor(color);
    super.setColor(color);
  }

  @Override
  public void setColor(float r,float g,float b,float a) {
    for(Actor e:list) e.setColor(r,g,b,a);
    super.setColor(r,g,b,a);
  }

  @Override
  public void setDebug(boolean enabled) {
    for(Actor e:list) e.setDebug(enabled);
    super.setDebug(enabled);
  }

  @Override
  public void setHeight(float height) {
    for(Actor e:list) e.setHeight(height);
    super.setHeight(height);
  }

  @Override
  public void setName(String name) {
    for(Actor e:list) e.setName(name);
    super.setName(name);
  }

  @Override
  public void setOrigin(int alignment) {
    for(Actor e:list) e.setOrigin(alignment);
    super.setOrigin(alignment);
  }

  @Override
  public void setOrigin(float originX,float originY) {
    for(Actor e:list) e.setOrigin(originX,originY);
    super.setOrigin(originX,originY);
  }

  @Override
  public void setOriginX(float originX) {
    for(Actor e:list) e.setOriginX(originX);
    super.setOriginX(originX);
  }

  @Override
  public void setOriginY(float originY) {
    for(Actor e:list) e.setOriginY(originY);
    super.setOriginY(originY);
  }

  @Override
  protected void setParent(Group parent) {
    // for(Actor e:list) e.
    super.setParent(parent);
  }

  @Override
  public void setPosition(float x,float y) {
    for(Actor e:list) e.setPosition(x,y);
    super.setPosition(x,y);
  }

  @Override
  public void setPosition(float x,float y,int alignment) {
    for(Actor e:list) e.setPosition(x,y,alignment);
    super.setPosition(x,y,alignment);
  }

  @Override
  public void setRotation(float degrees) {
    for(Actor e:list) e.setRotation(degrees);
    super.setRotation(degrees);
  }

  @Override
  public void setScale(float scaleXY) {
    for(Actor e:list) e.setScale(scaleXY);
    super.setScale(scaleXY);
  }

  @Override
  public void setScale(float scaleX,float scaleY) {
    for(Actor e:list) e.setScale(scaleX,scaleY);
    super.setScale(scaleX,scaleY);
  }

  @Override
  public void setScaleX(float scaleX) {
    for(Actor e:list) e.setScaleX(scaleX);
    super.setScaleX(scaleX);
  }

  @Override
  public void setScaleY(float scaleY) {
    for(Actor e:list) e.setScaleY(scaleY);
    super.setScaleY(scaleY);
  }

  @Override
  public void setSize(float width,float height) {
    for(Actor e:list) e.setSize(width,height);
    super.setSize(width,height);
  }

  @Override
  protected void setStage(Stage stage) {
    // for(Actor e:list) e.
    super.setStage(stage);
  }

  @Override
  public void setTouchable(Touchable touchable) {
    for(Actor e:list) e.setTouchable(touchable);
    super.setTouchable(touchable);
  }

  @Override
  public void setUserObject(Object userObject) {
    for(Actor e:list) e.setUserObject(userObject);
    super.setUserObject(userObject);
  }

  @Override
  public void setVisible(boolean visible) {
    for(Actor e:list) e.setVisible(visible);
    super.setVisible(visible);
  }

  @Override
  public void setWidth(float width) {
    for(Actor e:list) e.setWidth(width);
    super.setWidth(width);
  }

  @Override
  public void setX(float x) {
    for(Actor e:list) e.setX(x);
    super.setX(x);
  }

  @Override
  public void setX(float x,int alignment) {
    for(Actor e:list) e.setX(x,alignment);
    super.setX(x,alignment);
  }

  @Override
  public void setY(float y) {
    for(Actor e:list) e.setX(y);
    super.setY(y);
  }

  @Override
  public void setY(float y,int alignment) {
    for(Actor e:list) e.setY(y);
    super.setY(y,alignment);
  }

  @Override
  public boolean setZIndex(int index) {
    for(Actor e:list) e.setZIndex(index);
    return super.setZIndex(index);
  }

  @Override
  public void sizeBy(float size) {
    for(Actor e:list) e.sizeBy(size);
    super.sizeBy(size);
  }

  @Override
  public void sizeBy(float width,float height) {
    for(Actor e:list) e.sizeBy(width,height);
    super.sizeBy(width,height);
  }

  @Override
  protected void sizeChanged() {
    // for(Actor e:list) e.
    super.sizeChanged();
  }

  @Override
  public Vector2 stageToLocalCoordinates(Vector2 stageCoords) {
    for(Actor e:list) e.stageToLocalCoordinates(stageCoords);
    return super.stageToLocalCoordinates(stageCoords);
  }

  @Override
  public void toBack() {
    for(Actor e:list) e.toBack();
    super.toBack();
  }

  @Override
  public void toFront() {
    for(Actor e:list) e.toFront();
    super.toFront();
  }

  // @Override
  // public String toString() {
  //   for(Actor e:list) e.
  //   return super.toString();
  // }

  // @Override
  // protected Object clone() throws CloneNotSupportedException {
  //    return super.clone();
  // }

  // @Override
  // public boolean equals(Object obj) {
  //   for(Actor e:list) e.equals(obj);
  //   return super.equals(obj);
  // }

  // @Override
  // protected void finalize() throws Throwable {
  //    super.finalize();
  // }

  // @Override
  // public int hashCode() {
  //   for(Actor e:list) e.hashCode();
  //   return super.hashCode();
  // }

}
