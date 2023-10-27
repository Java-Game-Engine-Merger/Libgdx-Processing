package pama1234.gdx;

import org.yaml.snakeyaml.Yaml;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pama1234.yaml.UtilYaml;

public class SystemSetting{
  public static Yaml yaml=UtilYaml.Yaml();

  public static String path="data/sysConf.yaml";
  public static FileHandle file;
  public static SystemSettingData data;
  public static SystemSettingRuntime runtimeData;
  protected static void innerLoad() {
    file=Gdx.files.local(path);
    if(file.exists()) data=yaml.loadAs(file.readString("UTF-8"),SystemSettingData.class);
    else {
      data=new SystemSettingData();
      innerSave();
    }
    runtimeData=new SystemSettingRuntime(data);
  }
  protected static void innerSave() {
    file.writeString(yaml.dumpAsMap(data),false,"UTF-8");
  }
  public static void load() {
    if(data==null) innerLoad();
  }
  public static void save() {
    if(data!=null) innerSave();
  }
  /** 程序启动时读取的配置 */
  public static class SystemSettingData{
    /** 覆盖本地存储路径，一般用于安卓调试 */
    public String dataPath="data/";
    public FileType dataPathType=FileType.Local;

    /** 电脑端窗口的长度和宽度 */
    public int width=640,height=480;
    /** 电脑端是否使用全屏模式 */
    public boolean fullScreen;

    public int targetFps=60;

    public boolean mute;
    /** 区间为0到1 */
    public float volume=1;

    public boolean overridePlatform;
    public boolean isAndroid;

    public boolean printLog;
  }
  public static class SystemSettingRuntime{
    public FileHandle dataPath;
    public SystemSettingRuntime(SystemSettingData data) {
      loadFrom(data);
    }

    public void loadFrom(SystemSettingData data) {
      dataPath=Gdx.files.getFileHandle(data.dataPath,data.dataPathType);
    }
  }
}
