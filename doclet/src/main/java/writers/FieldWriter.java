package writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

import org.json.JSONException;
import org.json.JSONObject;

public class FieldWriter extends BaseWriter{

  /**
   *
   * @param vars inherited from containing ClassDoc
   * @param doc
   * @throws IOException
   */

  public static void write(
    HashMap<String,String> vars,
    VariableElement element,
    String classname) throws IOException {
    TemplateWriter templateWriter=new TemplateWriter();

    JSONObject fieldJSON=new JSONObject();

    String fieldName;
    if(getName(element).contains("[]")) {
      fieldName=getName(element).replace("[]","");
    }else {
      fieldName=getName(element);
    }

    String fileName;
    if(classname!="") {
      fileName=jsonDir+classname+"_"+fieldName+".json";
    }else {
      fileName=jsonDir+fieldName+".json";
    }

    List<String> tags=Shared
      .i()
      .getTags(element)
      .get(Shared.i().getWebrefTagName());
    String category=getCategory(tags.get(0));
    String subcategory=getSubcategory(tags.get(0));

    try {
      fieldJSON.put("description",getWebDescriptionFromSource(element));
      fieldJSON.put("brief",getWebBriefFromSource(element));
      fieldJSON.put("category",category);
      fieldJSON.put("subcategory",subcategory);
      fieldJSON.put("name",getName(element));
      fieldJSON.put("related",getRelated(element));

      if(Shared.i().isRootLevel(element.getEnclosingElement())) {
        fieldJSON.put("type","other");
      }else {
        fieldJSON.put("type","field");
        fieldJSON.put(
          "classanchor",
          getLocalAnchor(element.getEnclosingElement()));
        fieldJSON.put("parameters",getParentParam(element));
        String syntax=templateWriter.writePartial(
          "field.syntax.partial",
          getSyntax(element));
        ArrayList<String> syntaxList=new ArrayList<String>();
        syntaxList.add(syntax);
        fieldJSON.put("syntax",syntaxList);
      }
    }catch(JSONException ex) {
      ex.printStackTrace();
    }

    try {
      FileWriter file=new FileWriter(fileName);
      file.write(fieldJSON.toString());
      file.close();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }

  public static void write(VariableElement element) throws IOException {
    String classname="";
    write(new HashMap<String,String>(),element,classname);
  }

  protected static HashMap<String,String> getSyntax(VariableElement element) {
    HashMap<String,String> map=new HashMap<String,String>();
    map.put("object",getInstanceName(element));
    map.put("field",getName(element));
    return map;
  }

  protected static ArrayList<JSONObject> getParentParam(
    VariableElement element) {
    ArrayList<JSONObject> retList=new ArrayList<JSONObject>();
    JSONObject ret=new JSONObject();
    try {
      ret.put("name",getInstanceName(element));
      ret.put("desc",getInstanceDescription(element));
    }catch(JSONException ex) {
      ex.printStackTrace();
    }
    retList.add(ret);
    return retList;
  }

  protected static HashMap<String,String> getParent(VariableElement element) {
    HashMap<String,String> parent=new HashMap<String,String>();
    Element cd=element.getEnclosingElement();
    parent.put("name",getInstanceName(element));
    parent.put("name",getInstanceName(element));
    parent.put("type",cd.getSimpleName().toString());
    parent.put("description",getInstanceDescription(element));
    return parent;
  }
}
