package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;

/**
 * 偷懒神器
 */
public class AutoEntityManager<T extends UtilScreen>extends LayeredEntityCenter<T>{
  // 实体类型
  public static final int MoveOnStateChange=0;
  public static final int MoveOnEveryStateChange=1;

  // 实体加入的列表
  public static final int Center=0;
  public static final int CenterScreen=1;
  public static final int CenterCam=2;
  public static final int CenterNeo=3;

  public EntityCenter<T,StateEntity<T,?>> stateEntityCenter;
  public EntityCenter<T,StateEntity<T,?>> stateEntityComplexCenter;

  // 操作目标
  public EntityCenterAbstract<T,EntityListener,?>[] target;
  //  public EntityCenter<T,EntityListener>[] target;

  public AutoEntityManager(T p) {
    super(p,2);

    list[0]=stateEntityCenter=new EntityCenter<>(p);
    list[1]=stateEntityComplexCenter=new EntityCenter<>(p);

    target=new EntityCenterAbstract[] {p.center,p.centerScreen,p.centerCam};
    //    target=new EntityCenter[] {p.center,p.centerScreen,p.centerCam,p.centerNeo};
  }

  public <E extends EntityListener> void register(GetEntityWithStateChange<E> in,int eType,int[] data) {
    switch(eType) {
      case MoveOnEveryStateChange: {
        stateEntityComplexCenter.add(new StateEntity<T,E>(p,in,data[0],data[1]));
        stateEntityComplexCenter.refresh();
      }
        break;
      case MoveOnStateChange: {
        stateEntityCenter.add(new StateEntity<T,E>(p,in,data[0],data[1]));
        stateEntityCenter.refresh();
      }
        break;
      default:
        break;
    }
  }

  public <E extends EntityListener> void register(E in,int eType,int[] data) {
    switch(eType) {
      case MoveOnEveryStateChange: {
        stateEntityComplexCenter.add(new StateEntity<T,E>(p,in,data[0],data[1]));
        stateEntityComplexCenter.refresh();
      }
      case MoveOnStateChange: {
        stateEntityCenter.add(new StateEntity<T,E>(p,in,data[0],data[1]));
        stateEntityCenter.refresh();
      }
        break;
      default:
        break;
    }
  }
  /** 默认from和to不相等 */
  public void stateChangeEvent(StateEntityBase<?,?,?> out,StateEntityBase<?,?,?> in) {
    boolean outFlag=out!=null;
    boolean inFlag=in!=null;
    int from=outFlag?out.id:0;
    int to=inFlag?in.id:0;
    for(StateEntity<T,?> e:stateEntityCenter.list) {
      int id=e.stateId;
      if(outFlag) {
        if(id==from) {
          EntityListener content=e.content(out,in,false);
          if(content!=null) target[e.listType].remove(content);
        }
      }
      if(inFlag) {
        if(id==to) {
          EntityListener content=e.content(out,in,true);
          if(content!=null) target[e.listType].add(content);
        }
      }
    }
  }
  public static class StateEntity<T2 extends UtilScreen,E extends EntityListener>extends EntityWrapper<T2,E>{

    public int stateId;
    public GetEntityWithStateChange<E> getE;
    public E eCache;

    public int listType;

    public StateEntity(T2 p,E son,int stateId,int typeIn) {
      super(p,son);

      this.stateId=stateId;
      listType=typeIn;
    }
    public StateEntity(T2 p,GetEntityWithStateChange<E> getE,int stateId,int typeIn) {
      this(p,(E)null,stateId,typeIn);
      this.getE=getE;
    }

    public EntityListener content(StateEntityBase<?,?,?> from,StateEntityBase<?,?,?> to,boolean doAdd) {
      if(getE==null) {
        return content;
      }else {
        E tec=eCache;
        // if(!doAdd) eCache=null;
        if(tec==null) {
          tec=eCache=getE.get(from,to);
          return tec;
        }else {
          return tec;
        }
      }
    }
  }
  @FunctionalInterface
  public interface GetEntityWithStateChange<E extends EntityListener>{
    public E get(StateEntityBase<?,?,?> from,StateEntityBase<?,?,?> to);
  }
}
