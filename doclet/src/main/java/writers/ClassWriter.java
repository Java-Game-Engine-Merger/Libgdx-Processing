package writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import org.json.JSONException;
import org.json.JSONObject;

public class ClassWriter extends BaseWriter{

  private TypeElement element;
  private String libDir;

  public ClassWriter() {}

  @SuppressWarnings("unchecked")
  public void write(TypeElement element) throws IOException {
    if(!needsWriting(element)) return;

    this.element=element;
    String classname=getName(element);
    String anchor=getAnchor(element);

    HashMap<String,String> vars=new HashMap<String,String>();

    JSONObject classJSON=new JSONObject();

    String fileName,folderName;
    if(libDir!=null) {
      fileName=libDir+classname+".json";
      folderName=libDir;
    }else {
      fileName=jsonDir+classname+".json";
      folderName=jsonDir;
    }

    List<String> tags=Shared
      .i()
      .getTags(element)
      .get(Shared.i().getWebrefTagName());
    String category=getCategory(tags.get(0));
    String subcategory=getSubcategory(tags.get(0));

    try {
      classJSON.put("type","class");
      // These vars will be inherited by method and field writers
      classJSON.put("name",classname);
      classJSON.put("classanchor",anchor);
      String desc=getWebDescriptionFromSource(element);
      if(desc!="") {
        classJSON.put("description",desc);
      }
      if(!Shared.i().isCore(element)) { // documenting a library
        classJSON.put("isLibrary","true");
        classJSON.put("csspath","../../");
      }

      classJSON.put("brief",getWebBriefFromSource(element));

      ArrayList<JSONObject> methodSet=new ArrayList<JSONObject>();
      ArrayList<JSONObject> fieldSet=new ArrayList<JSONObject>();

      // Write all @webref methods for core classes (the tag tells us where to link to it in the index)

      for(Element subElement:element.getEnclosedElements()) {
        if(Shared.i().isMethod(subElement)&&needsWriting(subElement)) {
          ExecutableElement methodElement=(ExecutableElement)subElement;
          if(!classname.equals("PGraphics")||
            getName(methodElement).equals("beginDraw()")||
            getName(methodElement).equals("endDraw()")) {
            MethodWriter.write(
              (HashMap<String,String>)vars.clone(),
              methodElement,
              classname,
              folderName);
            methodSet.add(getPropertyInfo(methodElement));
          }
        }else if(Shared.i().isField(subElement)&&needsWriting(subElement)) {
          VariableElement fieldElement=(VariableElement)subElement;
          FieldWriter.write(
            (HashMap<String,String>)vars.clone(),
            fieldElement,
            classname);
          fieldSet.add(getPropertyInfo(fieldElement));
        }
      }

      ArrayList<String> constructors=getConstructors();
      classJSON.put("category",category);
      classJSON.put("subcategory",subcategory);
      classJSON.put("methods",methodSet);
      classJSON.put("classFields",fieldSet);
      classJSON.put("constructors",constructors);
      classJSON.put("parameters",getParameters());
      classJSON.put("related",getRelated(element));
    }catch(JSONException ex) {
      ex.printStackTrace();
    }

    try {
      FileWriter file=new FileWriter(fileName);
      file.write(classJSON.toString());
      file.close();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }

  public void write(TypeElement element,String lib) throws IOException {
    libDir=lib;
    write(element);
  }

  protected ArrayList<JSONObject> getParameters() throws IOException {
    ArrayList<JSONObject> ret=new ArrayList<JSONObject>();

    for(Element subElement:element.getEnclosedElements()) {
      if(Shared.i().isConstructor(subElement)) {
        if(Shared.i().shouldOmit(subElement)) {
          continue;
        }
        if(subElement.getModifiers().contains(Modifier.PROTECTED)) continue;

        ExecutableElement constructorElement=(ExecutableElement)subElement;
        ret.addAll(parseParameters(constructorElement));
      }
    }

    removeDuplicateParameters(ret);

    return ret;
  }

  private ArrayList<String> getConstructors() {
    ArrayList<String> constructors=new ArrayList<String>();

    for(Element subElement:element.getEnclosedElements()) {
      if(Shared.i().isConstructor(subElement)) {
        if(Shared.i().shouldOmit(subElement)) {
          continue;
        }
        if(subElement.getModifiers().contains(Modifier.PROTECTED)) continue;

        ExecutableElement constructorElement=(ExecutableElement)subElement;

        String constructor=element.getSimpleName()+"(";

        for(VariableElement p:constructorElement.getParameters()) {
          constructor+=p.getSimpleName()+", ";
        }
        if(constructor.endsWith(", ")) {
          constructor=constructor.substring(0,constructor.length()-2)+")";
        }else {
          constructor+=")";
        }
        constructors.add(constructor);
      }
    }

    return constructors;
  }

  private JSONObject getPropertyInfo(Element element) {
    JSONObject ret=new JSONObject();
    try {
      ret.put("name",getName(element));
      ret.put("anchor",getLocalAnchor(element));
      ret.put("desc",getWebBriefFromSource(element));
    }catch(JSONException ex) {
      ex.printStackTrace();
    }
    return ret;
  }
}
