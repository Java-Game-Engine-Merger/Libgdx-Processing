package pama1234.util;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.*;

public class Annotations{
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface ClientOnly{}
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface ServerOnly{}
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface ServerAndClient{}
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface ApiLevel{
    int level();
  }
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface DesktopOnly{}
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface MobileOnly{}
  /** 表示多余的缓存，用于假的多继承 */
  @ZenithLang
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface RedundantCache{}
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface ScreenDescription{
    String value();
  }
  /**
   * 天顶语将会实现对标记了这个东东的东东的自动化
   */
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface ZenithLang{}

  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public @interface FastText{

  }

  /** 表示语法糖 */
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public @interface SyntacticSugar{

  }
}
