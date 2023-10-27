package pama1234.yaml;

import java.util.LinkedHashMap;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

public class UtilYaml{
  public static Yaml Yaml() {
    Yaml yaml;
    // 初始化Yaml对象
    DumperOptions dops=new DumperOptions();
    OrdinalPropertyUtils customPropertyUtils=new OrdinalPropertyUtils();
    Representer repr=new Representer(dops);
    repr.setPropertyUtils(customPropertyUtils);
    yaml=new Yaml(new Constructor(new LoaderOptions()),repr);
    return yaml;
  }
  public Yaml yaml;

  public UtilYaml() {
    yaml=new Yaml();
  }
  public UtilYaml(Yaml yaml) {
    this.yaml=yaml;
  }

  public LocalBundle readMap(LinkedHashMap<String,String> map) {
    return readMap(new LocalBundle(),map);
  }
  public LocalBundle readMap(LocalBundle data,LinkedHashMap<String,String> map) {
    data.loadFrom(map);
    return data;
  }

  public <T> T load(LocalBundle localBundle,Class<T> class1) {//TODO 效率较低
    return yaml.loadAs(localBundle.getYaml(yaml),class1);
  }
  public <T> T load(LocalBundle localBundle,String[] name,Class<T> class1) {
    return yaml.loadAs(localBundle.getYaml(yaml,name),class1);
  }
}
