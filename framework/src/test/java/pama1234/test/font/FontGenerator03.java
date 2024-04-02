package pama1234.test.font;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.tools.hiero.BMFontUtil;
import com.badlogic.gdx.tools.hiero.HieroSettings;
import com.badlogic.gdx.tools.hiero.unicodefont.UnicodeFont;
import com.badlogic.gdx.tools.hiero.unicodefont.UnicodeFont.RenderType;
import com.badlogic.gdx.tools.hiero.unicodefont.effects.ConfigurableEffect.Value;
import com.badlogic.gdx.tools.hiero.unicodefont.effects.DistanceFieldEffect;

public class FontGenerator03 extends InputAdapter implements ApplicationListener{
  public static void main(String[] args) {
    Class<?> clas=MethodHandles.lookup().lookupClass();
    //    MainAppBase mab=new MainAppBase() {
    //      {
    //        var classArray=new Class[] {clas};
    //        screenClassList=new ArrayList<>(classArray.length);
    //        for(int i=0;i<classArray.length;i++) {
    //          screenClassList.add(i,classArray[i]);
    //        }
    //      }
    //
    //    };
    //    new Lwjgl3Application(mab,getDefaultConfiguration(mab,clas.getSimpleName()));
    new Lwjgl3Application(new FontGenerator03(),getDefaultConfiguration(null,clas.getSimpleName()));
  }

  public HieroSettings hieroSettings;
  public UnicodeFont unicodeFont;

  public void setup() {
    int fontSize=32;
    float spread=3;
    int spreadInt=(int)(spread+1);
    int advance=(int)(-spread*2);

    hieroSettings=new HieroSettings();
    hieroSettings.setFontSize(fontSize);
    hieroSettings.setFontName("Maple Mono SC NF");

    HieroSettings settings=hieroSettings;

    //    settings.setFont2File(fontFileText.getText());
    //    settings.setFont2Active(fontFileRadio.isSelected());
    settings.setFont2Active(false);
    //    settings.setBold(boldCheckBox.isSelected());
    //    settings.setItalic(italicCheckBox.isSelected());
    //    settings.setMono(monoCheckBox.isSelected());
    settings.setGamma(1.8f);

    settings.setPaddingTop(spreadInt);
    settings.setPaddingRight(spreadInt);
    settings.setPaddingBottom(spreadInt);
    settings.setPaddingLeft(spreadInt);

    settings.setPaddingAdvanceX(-advance);
    settings.setPaddingAdvanceY(-advance);

    settings.setGlyphPageWidth(1024);
    settings.setGlyphPageHeight(1024);
    //    settings.setGlyphText(sampleTextPane.getText());
    hieroSettings.setGlyphText("abcABC123!@#$%^&*()_+`-=[]\\{}|;':,./<>?\"");
    settings.setRenderType(RenderType.Java.ordinal());
    //    if(nativeRadio.isSelected()) settings.setRenderType(RenderType.Native.ordinal());
    //    else if(freeTypeRadio.isSelected()) settings.setRenderType(RenderType.FreeType.ordinal());
    //    else settings.setRenderType(RenderType.Java.ordinal());
    //    for(Iterator iter=effectPanels.iterator();iter.hasNext();) {
    //      EffectPanel panel=(EffectPanel)iter.next();
    //      settings.getEffects().add(panel.getEffect());
    //    }

    DistanceFieldEffect distanceFieldEffect=new DistanceFieldEffect();
    var list=new ArrayList<Value>();
    list.add(new BasicValue("Color",new Color(255,255,255,255)));
    list.add(new BasicValue("Scale",32));
    list.add(new BasicValue("Spread",3.0f));
    distanceFieldEffect.setValues(list);
    //    System.out.println(Arrays.toString(distanceFieldEffect.getValues().toArray()));
    List effects=hieroSettings.getEffects();
    //    effects.clear();
    effects.add(distanceFieldEffect);

    String fonts[]=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    //    for(int i=0;i<fonts.length;i++) {
    //      var fontName=fonts[i];
    //      if(fontName.contains("Maple")) {
    //        System.out.println(fontName);
    //      }
    //    }

    Font mapleMonoScNf=new Font("Maple Mono SC NF",Font.PLAIN,fontSize);
    //    System.out.println(mapleMonoScNf);

    unicodeFont=new UnicodeFont(mapleMonoScNf,hieroSettings);
    //    unicodeFont.addGlyphs(32,72);
    unicodeFont.addGlyphs("abcABC123!@#$%^&*()_+`-=[]\\{}|;':,./<>?\"");
    unicodeFont.loadGlyphs();
    //    unicodeFont.loadGlyphs(Integer.MAX_VALUE);
    //    unicodeFont.getFontFile();
    String pathAndName=System.getProperty("user.dir")+"/fontOut/test.fnt";

    try {
      new BMFontUtil(unicodeFont).save(new File(pathAndName));
    }catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void create() {
    setup();
    Gdx.app.exit();
  }

  @Override
  public void resize(int width,int height) {

  }

  @Override
  public void render() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {

  }

  public static class BasicValue implements Value{
    public String name;
    public Object object;

    public BasicValue(String name,Object object) {
      this.name=name;
      this.object=object;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public void setString(String value) {

    }

    @Override
    public String getString() {
      return "null";
    }

    @Override
    public Object getObject() {
      return object;
    }

    @Override
    public void showDialog() {

    }
  }
}