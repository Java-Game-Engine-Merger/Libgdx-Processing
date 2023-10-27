package writers;

import java.io.IOException;
import java.util.ArrayList;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

public class LibraryWriter extends BaseWriter{

  PackageElement element;
  String pkg;
  String dir;

  static TemplateWriter templateWriter;
  static ArrayList<String> writtenLibraries;

  private String libDir="../../processing-website/content/references/translations/en/";

  public LibraryWriter() {
    if(templateWriter==null) {
      templateWriter=new TemplateWriter();
    }
    if(writtenLibraries==null) {
      writtenLibraries=new ArrayList<String>();
    }
  }

  public void write(PackageElement element) {
    // check for files that haven't been read

    String name=element.getSimpleName().toString();

    if(writtenLibraries.contains(name)) {
      if(Shared.i().isNoisy()) {
        System.out.println("Already processed "+name+". Skipping.");
      }
      return;
    }
    writtenLibraries.add(name);

    String[] parts=name.split("\\.");
    String pkg=parts[parts.length-1]+"/";
    if(pkg.equals("data/")||pkg.equals("opengl/")) dir=jsonDir;
    else dir=libDir+pkg;
    //grab all relevant information for the element

    for(Element subElement:element.getEnclosedElements()) {
      if(Shared.i().isClassOrInterface(subElement)) {
        TypeElement classElement=(TypeElement)subElement;
        // document the class if it has a @webref tag
        try {
          if(Shared.i().isNoisy()) {
            System.out.println(
              "Loading: "+classElement.getQualifiedName().toString()+".");
          }
          new ClassWriter().write(classElement,dir);
        }catch(IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }
}
