package pama1234.test.font;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.tools.hiero.BMFontUtil;
import com.badlogic.gdx.tools.hiero.HieroSettings;
import com.badlogic.gdx.tools.hiero.unicodefont.UnicodeFont;
import com.badlogic.gdx.tools.hiero.unicodefont.UnicodeFont.RenderType;
import com.badlogic.gdx.tools.hiero.unicodefont.effects.ColorEffect;
import com.badlogic.gdx.tools.hiero.unicodefont.effects.ConfigurableEffect.Value;

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

  public String userDir=System.getProperty("user.dir");

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

    hieroSettings.setFont2Active(false);
    hieroSettings.setGamma(1.8f);

    hieroSettings.setPaddingTop(spreadInt);
    hieroSettings.setPaddingRight(spreadInt);
    hieroSettings.setPaddingBottom(spreadInt);
    hieroSettings.setPaddingLeft(spreadInt);

    hieroSettings.setPaddingAdvanceX(advance);
    hieroSettings.setPaddingAdvanceY(advance);

    hieroSettings.setGlyphPageWidth(1024);
    hieroSettings.setGlyphPageHeight(1024);
    String sampleText="abcABC123!@#$%^&*()_+`-=[]\\{}|;':,./<>?\"";
    hieroSettings.setGlyphText(sampleText);
    hieroSettings.setRenderType(RenderType.Java.ordinal());

    List effects=hieroSettings.getEffects();

    var colorEffect=new ColorEffect();
    colorEffect.setColor(java.awt.Color.WHITE);
    effects.add(colorEffect);

    //    DistanceFieldEffect distanceFieldEffect=new DistanceFieldEffect();
    //    var list=new ArrayList<Value>();
    //    //    list.add(new BasicValue("Color",new Color(255,255,255,255)));
    //    list.add(new BasicValue("Scale",32));
    //    list.add(new BasicValue("Spread",3.0f));
    //    distanceFieldEffect.setValues(list);
    //    //    System.out.println(Arrays.toString(distanceFieldEffect.getValues().toArray()));
    //    //    effects.clear();
    //    effects.add(distanceFieldEffect);

    //    String fonts[]=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    //    for(int i=0;i<fonts.length;i++) {
    //      var fontName=fonts[i];
    //      if(fontName.contains("Maple")) {
    //        System.out.println(fontName);
    //      }
    //    }

    //    Font mapleMonoScNf=new Font("Maple Mono SC NF",Font.PLAIN,fontSize);
    //    Font mapleMonoScNf=new Font(Font.MONOSPACED,Font.PLAIN,fontSize);
    //    System.out.println(mapleMonoScNf);

    //    unicodeFont=new UnicodeFont(mapleMonoScNf,hieroSettings);
    unicodeFont=new UnicodeFont(userDir+"/doc/font/MapleMono-SC-NF/MapleMono-SC-NF-Regular.ttf",hieroSettings);
    //    unicodeFont.addGlyphs(32,72);
    unicodeFont.addAsciiGlyphs();
    //    unicodeFont.addGlyphs(0,Character.MAX_VALUE);
    unicodeFont.loadGlyphs();
    //    List fontEffect=unicodeFont.getEffects();
    //    fontEffect.clear();
    //    for(int i=0;i<effects.size();i++) {
    //      fontEffect.add(effects.get(i));
    //    }
    //    unicodeFont.loadGlyphs(Integer.MAX_VALUE);
    //    unicodeFont.getFontFile();

    var renderingBackgroundColor=new Color(0,0,0,1);

    //    if(!fontEffect.isEmpty()&&unicodeFont.loadGlyphs(64)) {
    //      //      glyphPageComboModel.removeAllElements();
    //      int pageCount=unicodeFont.getGlyphPages().size();
    //      int glyphCount=0;
    //      for(int i=0;i<pageCount;i++) {
    //        //        glyphPageComboModel.addElement("Page "+(i+1));
    //        glyphCount+=((GlyphPage)unicodeFont.getGlyphPages().get(i)).getGlyphs().size();
    //      }
    //      //      glyphPagesTotalLabel.setText(String.valueOf(pageCount));
    //      //      glyphsTotalLabel.setText(String.valueOf(glyphCount));
    //    }
    //
    //    boolean sampleTextRadioSelected=false;
    //
    //    if(sampleTextRadioSelected) {
    //      int offset=unicodeFont.getYOffset(sampleText);
    //      if(offset>0) offset=0;
    //      unicodeFont.drawString(10,12,sampleText,WHITE,0,sampleText.length());
    //    }else {
    //      // GL11.glColor4f(renderingBackgroundColor.r, renderingBackgroundColor.g, renderingBackgroundColor.b,
    //      // renderingBackgroundColor.a);
    //      // fillRect(0, 0, unicodeFont.getGlyphPageWidth() + 2, unicodeFont.getGlyphPageHeight() + 2);
    //      //      int index=glyphPageCombo.getSelectedIndex();
    //      List pages=unicodeFont.getGlyphPages();
    //      //      if(index>=0&&index<pages.size()) {
    //      Texture texture=((GlyphPage)pages.get(0)).getTexture();
    //
    //      var page=((GlyphPage)pages.get(0));
    //      //      var glyph=page.getGlyphs().get(0);
    //      //      System.out.println(glyph.getU());
    //      //      System.out.println(texture.getWidth()+"x"+texture.getHeight());
    //
    //      glDisable(GL_TEXTURE_2D);
    //      glColor4f(renderingBackgroundColor.r,renderingBackgroundColor.g,renderingBackgroundColor.b,
    //        renderingBackgroundColor.a);
    //      glBegin(GL_QUADS);
    //      glVertex3f(0,0,0);
    //      glVertex3f(0,texture.getHeight(),0);
    //      glVertex3f(texture.getWidth(),texture.getHeight(),0);
    //      glVertex3f(texture.getWidth(),0,0);
    //      glEnd();
    //      glEnable(GL_TEXTURE_2D);
    //
    //      texture.bind();
    //      glColor4f(1,1,1,1);
    //      glBegin(GL_QUADS);
    //      glTexCoord2f(0,0);
    //      glVertex3f(0,0,0);
    //
    //      glTexCoord2f(0,1);
    //      glVertex3f(0,texture.getHeight(),0);
    //
    //      glTexCoord2f(1,1);
    //      glVertex3f(texture.getWidth(),texture.getHeight(),0);
    //
    //      glTexCoord2f(1,0);
    //      glVertex3f(texture.getWidth(),0,0);
    //      glEnd();
    //      //      }
    //    }
    //
    //    glDisable(GL_TEXTURE_2D);
    //    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    //    glDisableClientState(GL_VERTEX_ARRAY);

    String pathAndName=userDir+"/fontOut/test.fnt";

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