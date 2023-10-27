package writers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.DocTree.Kind;
import com.sun.source.doctree.ParamTree;
import com.sun.source.doctree.ReferenceTree;
import com.sun.source.doctree.SeeTree;

public class BaseWriter{

  // Some utilities

  public static final String MODE_JAVASCRIPT="js";
  public static final String jsonDir="../../processing-website/content/references/translations/en/processing/";

  public BaseWriter() {}

  protected static BufferedWriter makeWriter(String anchor) throws IOException {
    return makeWriter(anchor,false);
  }

  protected static String getWriterPath(String anchor,Boolean isLocal) {
    if(!isLocal) {
      return Shared.i().getOutputDirectory()+"/"+anchor;
    }else {
      return Shared.i().getLocalOutputDirectory()+anchor;
    }
  }

  protected static BufferedWriter makeWriter(String anchor,Boolean isLocal)
    throws IOException {
    FileWriter fw=new FileWriter(getWriterPath(anchor,isLocal));

    return new BufferedWriter(fw);
  }

  protected static String getTimestamp() {
    Calendar now=Calendar.getInstance();
    Locale us=new Locale("en");

    return (now.getDisplayName(Calendar.MONTH,Calendar.LONG,us)+
      " "+
      now.get(Calendar.DAY_OF_MONTH)+
      ", "+
      now.get(Calendar.YEAR)+
      " "+
      FileUtils.nf(now.get(Calendar.HOUR),2)+
      ":"+
      FileUtils.nf(now.get(Calendar.MINUTE),2)+
      ":"+
      FileUtils.nf(now.get(Calendar.SECOND),2)+
      now.getDisplayName(Calendar.AM_PM,Calendar.SHORT,us).toLowerCase()+
      " "+
      TimeZone
        .getDefault()
        .getDisplayName(
          TimeZone.getDefault().inDaylightTime(now.getTime()),
          TimeZone.SHORT,
          us));
  }

  protected static String getAnchor(Element element) {
    String ret=getAnchorFromName(getName(element));

    Element parent=element.getEnclosingElement();
    if(parent!=null&&
      Shared.i().isClassOrInterface(parent)&&
      !Shared.i().isRootLevel(parent)) {
      ret=element.getEnclosingElement().getSimpleName()+"_"+ret;
    }

    if(!Shared.i().isCore(element)) {
      //add package name to anchor
      String[] parts=Shared
        .i()
        .getContainingPackage(element)
        .getSimpleName()
        .toString()
        .split("\\.");
      String pkg=parts[parts.length-1]+"/";
      if(pkg.equals("data/")||pkg.equals("opengl/")) {
        // ret=ret;
      }else ret=pkg+ret;
    }

    return ret;
  }

  protected static String getLocalAnchor(Element element) {
    String ret=getAnchorFromName(getName(element));
    Element parent=element.getEnclosingElement();
    if(parent!=null&&Shared.i().isClassOrInterface(parent)) {
      ret=parent.getSimpleName().toString()+"_"+ret;
    }
    return ret;
  }

  protected static String getReturnTypes(ExecutableElement element) {
    String ret=nameInPDE(element.getReturnType().toString());

    if(element.getEnclosingElement()!=null) {
      for(Element subElement:element
        .getEnclosingElement()
        .getEnclosedElements()) {
        if(!Shared.i().isMethod(subElement)) {
          continue;
        }

        ExecutableElement methodElement=(ExecutableElement)subElement;

        if(methodElement.getSimpleName().equals(element.getSimpleName())&&
          !methodElement.getReturnType().equals(element.getReturnType())) {
          String name=getSimplifiedType(
            nameInPDE(methodElement.getReturnType().toString()));
          if(!ret.contains(name)) {
            // add return type name if it's not already included
            ret+=", "+name;
          }
        }
      }
    }

    // add "or" (split first to make sure we don't mess up the written description)
    ret=ret.replaceFirst(",([^,]+)$",", or$1");
    if(!ret.matches(".+,.+,.+")) {
      ret=ret.replace(",","");
    }

    return ret;
  }

  protected static String getSimplifiedType(String str) {
    if(str.equals("long")) {
      return "int";
    }
    if(str.equals("double")) {
      return "float";
    }

    return str;
  }

  protected static String getDimensionality(Element element) {
    String dimensionality="";
    TypeMirror type=element.asType();
    while(type.getKind()==TypeKind.ARRAY) {
      dimensionality=dimensionality.concat("[]");
      ArrayType arrayType=(ArrayType)type;
      type=arrayType.getComponentType();
    }
    return dimensionality;
  }

  protected static String getName(Element element) { // handle
    String ret=element.getSimpleName().toString();
    if(Shared.i().isMethod(element)) {
      ret=ret.concat("()");
    }else if(Shared.i().isField(element)) {
      // add [] if needed
      ret=ret.concat(getDimensionality(element));
    }
    return ret;
  }

  protected static String getAnchorFromName(String name) {
    // change functionName() to functionName_
    if(name.endsWith("()")) {
      name=name.replace("()","_");
    }
    // change "(some thing)" to something
    if(name.contains("(")&&name.contains(")")) {
      int start=name.lastIndexOf("(")+1;
      int end=name.lastIndexOf(")");
      name=name.substring(start,end);
      name=name.replace(" ","");
    }
    // change thing[] to thing
    if(name.contains("[]")) {
      name=name.replaceAll("\\[\\]","");
    }
    // change "some thing" to "some_thing.html"
    name=name.replace(" ","_");
    return name;
  }

  protected static String getBasicDescriptionFromSource(Element element) {
    return getBasicDescriptionFromSource(longestText(element));
  }

  protected static String getBriefDescriptionFromSource(Element element) {
    List<String> sta=Shared.i().getTags(element).get("brief");
    if(sta!=null&&sta.size()>0) {
      return sta.get(0);
    }
    return getBasicDescriptionFromSource(element);
  }

  protected static String getWebDescriptionFromSource(Element element) {
    List<String> sta=Shared.i().getTags(element).get("webDescription");
    if(sta!=null&&sta.size()>0) {
      return sta.get(0);
    }
    return getBasicDescriptionFromSource(element);
  }

  protected static String getWebBriefFromSource(Element element) {
    List<String> sta=Shared.i().getTags(element).get("webBrief");
    if(sta!=null&&sta.size()>0) {
      return sta.get(0);
    }
    return getBasicDescriptionFromSource(element);
  }

  protected static String longestText(Element element) {
    String s=Shared.i().getCommentFullBodyText(element);

    if(Shared.i().isWebref(element)) {
      //override longest rule if the element has an @webref tag
      return s;
    }

    for(Element subElement:element
      .getEnclosingElement()
      .getEnclosedElements()) {
      if((Shared.i().isMethod(element)&&!Shared.i().isMethod(subElement))||
        (Shared.i().isField(element)&&!Shared.i().isField(subElement))) {
        continue;
      }
      if(subElement.getSimpleName().equals(element.getSimpleName())) {
        String comment=Shared.i().getCommentFullBodyText(subElement);
        if(comment.length()>s.length()) {
          s=comment;
        }
      }
    }
    return s;
  }

  protected static String getBasicDescriptionFromSource(String s) {
    String[] sa=s.split("(?i)(<h\\d>Advanced:?</h\\d>)|(=advanced)");
    if(sa.length!=0) s=sa[0];
    return s;
  }

  protected static String getAdvancedDescriptionFromSource(Element element) {
    return getAdvancedDescriptionFromString(longestText(element));
  }

  private static String getAdvancedDescriptionFromString(String s) {
    String[] sa=s.split("(?i)(<h\\d>Advanced:?</h\\d>)|(=advanced)");
    if(sa.length>1) s=sa[1];
    return s;
  }

  protected static HashMap<String,String> getDefaultDescriptionVars() {
    HashMap<String,String> vars=new HashMap<String,String>();
    vars.put("description title","Description");
    vars.put("description text","");
    return vars;
  }

  /*
   * Get all the syntax possibilities for a method
   */
  protected static ArrayList<HashMap<String,String>> getSyntax(
    Element element,
    String instanceName) throws IOException {
    ArrayList<HashMap<String,String>> ret=new ArrayList<HashMap<String,String>>();

    for(Element subElement:element
      .getEnclosingElement()
      .getEnclosedElements()) {
      if(!Shared.i().isMethod(subElement)||Shared.i().shouldOmit(subElement)) {
        continue;
      }
      if(subElement.getModifiers().contains(Modifier.PROTECTED)) continue;

      ExecutableElement methodElement=(ExecutableElement)subElement;

      if(methodElement.getSimpleName().equals(element.getSimpleName())) {
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("name",methodElement.getSimpleName().toString());
        map.put("object",instanceName);

        ArrayList<HashMap<String,String>> parameters=new ArrayList<HashMap<String,String>>();
        String params="";
        for(VariableElement parameter:methodElement.getParameters()) {
          params=params+parameter.getSimpleName()+", ";
          HashMap<String,String> paramMap=new HashMap<String,String>();
          paramMap.put("parameter",parameter.getSimpleName().toString());
          parameters.add(paramMap);
        }

        if(params.endsWith(", ")) {
          params=params.substring(0,params.lastIndexOf(", "));
        }

        map.put("parameters",params);
        if(!ret.contains(map)) {
          //don't put in duplicate function syntax
          ret.add(map);
        }
      }
    }

    return ret;
  }

  private static String removePackage(String name) { // keep everything after the last dot
    if(name.contains(".")) {
      return name.substring(name.lastIndexOf(".")+1);
    }
    return name;
  }

  private static String nameInPDE(String fullName) {
    if(fullName.contains("<")&&fullName.endsWith(">")) { // if this type uses Java generics
      String parts[]=fullName.split("<");
      String generic=removePackage(parts[0]);
      String specialization=removePackage(parts[1]);
      specialization=specialization.substring(0,specialization.length()-1);
      return generic+"&lt;"+specialization+"&gt;";
    }
    return removePackage(fullName);
  }

  protected static String getUsage(Element element) {
    List<String> tags=Shared.i().getTags(element).get("usage");
    if(tags.size()!=0) {
      return tags.get(0);
    }
    tags=Shared.i().getTags(element.getEnclosingElement()).get("usage");
    if(tags.size()!=0) {
      return tags.get(0);
    }
    // return empty string if no usage is found
    return "";
  }

  protected static String getInstanceName(Element element) {
    List<String> tags=Shared
      .i()
      .getTags(element.getEnclosingElement())
      .get("instanceName");
    if(tags!=null&&tags.size()>0) {
      return tags.get(0).split("\\s")[0];
    }
    return "";
  }

  protected static String getInstanceDescription(Element element) {
    List<String> tags=Shared
      .i()
      .getTags(element.getEnclosingElement())
      .get("instanceName");
    if(tags!=null&&tags.size()>0) {
      String s=tags.get(0);
      return s.substring(s.indexOf(" "));
    }
    return "";
  }

  protected static ArrayList<JSONObject> getParameters(
    ExecutableElement element) throws IOException {
    //get parent
    Element cd=element.getEnclosingElement();
    ArrayList<JSONObject> ret=new ArrayList<JSONObject>();

    if(!Shared.i().isRootLevel(cd)) {
      //add the parent parameter if this isn't a function of PApplet
      JSONObject parent=new JSONObject();
      try {
        ArrayList<String> paramType=new ArrayList<String>();
        paramType.add(cd.getSimpleName().toString());
        parent.put("name",getInstanceName(element));
        parent.put("type",paramType);
        parent.put("description",getInstanceDescription(element));
        ret.add(parent);
      }catch(JSONException ex) {
        ex.printStackTrace();
      }
    }

    // get parameters from this and all other declarations of method
    for(Element subElement:cd.getEnclosedElements()) {
      if(!Shared.i().isMethod(subElement)||Shared.i().shouldOmit(subElement)) {
        continue;
      }
      if(subElement.getModifiers().contains(Modifier.PROTECTED)) continue;

      ExecutableElement method=(ExecutableElement)subElement;
      if(subElement.getSimpleName().equals(element.getSimpleName())) {
        ret.addAll(parseParameters(method));
      }
    }

    removeDuplicateParameters(ret);

    return ret;
  }

  protected static void removeDuplicateParameters(ArrayList<JSONObject> ret) {
    // combine duplicate parameter names with differing types
    try {
      for(JSONObject parameter:ret) {
        String desc=parameter.getString("description");
        JSONArray paramArray=parameter.getJSONArray("type");
        if(!desc.endsWith(": ")) {
          // if the chosen description has actual text
          // e.g. float: something about the float
          for(JSONObject parameter2:ret) {
            String desc2=parameter2.getString("description");

            if(parameter2.getString("name").equals(parameter.getString("name"))) {
              // freshen up our variable with the latest description

              JSONArray paramArray2=parameter2.getJSONArray("type");
              String addItem="";

              for(int i=0;i<paramArray.length();i++) {
                for(int j=0;j<paramArray2.length();j++) {
                  if(!paramArray.getString(i).equals(paramArray2.getString(j))) {
                    addItem=paramArray2.getString(j);
                  }
                }
              }

              if(addItem!="") {
                paramArray.put(addItem);
              }
            }
          }
        }

        ArrayList<String> newList=new ArrayList<String>();

        for(int i=0;i<paramArray.length();i++) {
          if(!newList.contains(paramArray.getString(i))) {
            newList.add(paramArray.getString(i));
          }
        }

        parameter.put("type",newList);
      }
      //remove parameters without descriptions
      for(int i=ret.size()-1;i>=0;i--) {
        if(ret.get(i).getString("description").equals("")) {
          ret.remove(i);
        }
      }
    }catch(JSONException ex) {
      ex.printStackTrace();
    }
  }

  protected static ArrayList<JSONObject> parseParameters(
    ExecutableElement element) {
    ArrayList<JSONObject> paramList=new ArrayList<JSONObject>();
    for(VariableElement param:element.getParameters()) {
      String type=getSimplifiedType(nameInPDE(param.asType().toString()));
      String name=param.getSimpleName().toString();
      String desc="";

      for(ParamTree parameter:Shared.i().getElementParamTags(element)) {
        if(parameter.getName().toString().equals(name)) {
          desc=desc.concat(parameter.getDescription().toString());
        }
      }

      JSONObject paramJSON=new JSONObject();

      try {
        ArrayList<String> paramType=new ArrayList<String>();
        paramType.add(type);
        paramJSON.put("name",name);
        paramJSON.put("type",paramType);
        paramJSON.put("description",desc);
      }catch(JSONException ex) {
        ex.printStackTrace();
      }

      paramList.add(paramJSON);
    }

    return paramList;
  }

  protected static ArrayList<SeeTree> getAllSeeTags(Element element) {
    ArrayList<SeeTree> ret=new ArrayList<SeeTree>();
    Element cd=element.getEnclosingElement();
    if(cd!=null&&Shared.i().isMethod(element)) {
      // if there is a containing class, get @see tags for all
      // methods with the same name as this one
      // Fixes gh issue 293
      for(Element subElement:cd.getEnclosedElements()) {
        if(!Shared.i().isMethod(subElement)) continue;

        if(subElement.getSimpleName().equals(element.getSimpleName())) {
          for(SeeTree tag:Shared.i().getElementSeeTags(subElement)) {
            ret.add(tag);
          }
        }
      }
    }else { // if no containing class (e.g. doc is a class)
      // just grab the see tags in the class doc comment
      for(SeeTree tag:Shared.i().getElementSeeTags(element)) {
        ret.add(tag);
      }
    }
    return ret;
  }

  protected static ArrayList<String> getRelated(Element element)
    throws IOException {
    ArrayList<String> related=new ArrayList<String>();

    // keep track of class members so that links to methods in e.g. PGraphics
    // that are copied into PApplet are correctly linked.
    HashMap<String,Element> classMethods=new HashMap<String,Element>();
    HashMap<String,Element> classFields=new HashMap<String,Element>();

    if(Shared.i().isMethod(element)||Shared.i().isField(element)) {
      // fill our maps
      Element containingClass=element.getEnclosingElement();
      for(Element subElement:containingClass.getEnclosedElements()) {
        if(Shared.i().isMethod(subElement)&&((needsWriting(subElement)))) {
          classMethods.put(subElement.getSimpleName().toString(),subElement);
        }else if(Shared.i().isField(subElement)&&needsWriting(subElement)) {
          classFields.put(subElement.getSimpleName().toString(),subElement);
        }
      }
    }

    // add link to each @see item
    for(SeeTree tag:getAllSeeTags(element)) {
      HashMap<String,String> map=new HashMap<String,String>();
      List<? extends DocTree> ref=tag.getReference();

      for(DocTree d:ref) {
        if(d.getKind()==Kind.REFERENCE) {
          ReferenceTree r=(ReferenceTree)d;
          String signature=r.getSignature();

          Element refElement=Shared
            .i()
            .getElementFromSignature(signature,element);

          if(refElement!=null) {
            if(Shared.i().isMethod(refElement)&&
              classMethods.containsKey(refElement.getSimpleName().toString())) {
              // link to the member as it is in this class, instead of
              // as it is in another class
              Element prior=classMethods.get(
                refElement.getSimpleName().toString());

              refElement=prior;
            }else if(Shared.i().isField(refElement)&&
              classFields.containsKey(refElement.getSimpleName().toString())) {
                Element prior=classFields.get(
                  refElement.getSimpleName().toString());
                refElement=prior;
              }
          }
          if(needsWriting(refElement)||syntaxEquivalentNeedsWriting(refElement)) {
            // add links to things that are actually written
            map.put("anchor",getAnchor(refElement));
            related.add(getAnchor(refElement));
          }else if(refElement!=null) {
            System.out.println(
              "[RELATED ERROR] Related reference found but not in webref : "+
                refElement+
                " from "+
                element.getEnclosingElement().getSimpleName().toString()+
                "#"+
                element);
          }else {
            System.out.println(
              "[RELATED ERROR] Related reference not found or misspelled \""+
                signature+
                "\" from "+
                element.getEnclosingElement().getSimpleName().toString()+
                "#"+
                element);
          }
        }
      }
    }

    // add link to each @see_external item
    List<String> tags=Shared
      .i()
      .getTags(element)
      .get(Shared.i().getSeeAlsoTagName());
    if(tags!=null) {
      for(String tag:tags) {
        related.add(tag.concat("_"));
      }
    }

    return related;
  }

  protected static String getEvents(Element element) {
    return "";
  }

  /**
   * Modes should support all API, so if XML not explicitly states "not supported", then assume it
   * does.
   */
  protected static boolean isModeSupported(Element element,String mode) {
    return true;
  }

  protected static boolean needsWriting(Element element) {
    return (element!=null)&&Shared.i().isWebref(element);
  }

  protected static boolean syntaxEquivalentNeedsWriting(Element element) {
    if(element==null||
      !(Shared.i().isMethod(element)|Shared.i().isConstructor(element))) return false;

    boolean exists=false;
    for(Element subElement:element
      .getEnclosingElement()
      .getEnclosedElements()) {
      if(!Shared.i().isMethod(subElement)||Shared.i().shouldOmit(subElement)) {
        continue;
      }

      ExecutableElement methodElement=(ExecutableElement)subElement;

      if(methodElement.getSimpleName().equals(element.getSimpleName())) {
        if(Shared.i().isWebref(subElement)) {
          exists=true;
          break;
        }
      }
    }

    return exists;
  }

  public static String getCategory(String webref) {
    String firstPart=webref.split("\\s")[0];
    String[] parts=firstPart.split(":");
    if(parts.length>1) {
      return parts[0];
    }
    return firstPart;
  }

  public static String getSubcategory(String webref) {
    String subcategory;
    if(webref.split(":").length>1) subcategory=webref.split(":")[1];
    else subcategory="";
    return subcategory;
  }
}
