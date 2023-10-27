package org.eclipse.tm4e.core.internal.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.Nullable;

import pama1234.Tools;

public final class StringUtils{
  private static final List<String> LIST_WITH_EMPTY_STRING=List.of("");
  private static final Pattern RRGGBB=Pattern.compile("^#[0-9a-f]{6}",Pattern.CASE_INSENSITIVE);
  private static final Pattern RRGGBBAA=Pattern.compile("^#[0-9a-f]{8}",Pattern.CASE_INSENSITIVE);
  private static final Pattern RGB=Pattern.compile("^#[0-9a-f]{3}",Pattern.CASE_INSENSITIVE);
  private static final Pattern RGBA=Pattern.compile("^#[0-9a-f]{4}",Pattern.CASE_INSENSITIVE);
  public static boolean isValidHexColor(final CharSequence hex) {
    // if(hex.isEmpty()) {
    if(Tools.isEmpty(hex)) {
      return false;
    }
    if(RRGGBB.matcher(hex).matches()) {
      // #rrggbb
      return true;
    }
    if(RRGGBBAA.matcher(hex).matches()) {
      // #rrggbbaa
      return true;
    }
    if(RGB.matcher(hex).matches()) {
      // #rgb
      return true;
    }
    if(RGBA.matcher(hex).matches()) {
      // #rgba
      return true;
    }
    return false;
  }
  public static String[] splitToArray(final String line,final char separator) {
    // if(line.isEmpty()) 
    if(Tools.isEmpty(line)) return new String[] {""};
    var tmp=new String[8];
    int count=0;
    int start=0;
    int end=line.indexOf(separator,0);
    while(end>=0) {
      if(count==tmp.length) { // check if array needs resize
        final var tmp2=new String[tmp.length+(tmp.length>>1)];
        System.arraycopy(tmp,0,tmp2,0,count);
        tmp=tmp2;
      }
      tmp[count]=line.substring(start,end);
      count++;
      start=end+1;
      end=line.indexOf(separator,start);
    }
    if(count==tmp.length) { // check if array needs resize
      final var tmp2=new String[tmp.length+1];
      System.arraycopy(tmp,0,tmp2,0,count);
      tmp=tmp2;
    }
    tmp[count]=line.substring(start);
    count++;
    if(count==tmp.length) {
      return tmp;
    }
    final var result=new String[count];
    System.arraycopy(tmp,0,result,0,count);
    return result;
  }
  public static List<String> splitToList(final String line,final char separator) {
    if(line.isEmpty()) return LIST_WITH_EMPTY_STRING;
    final var result=new ArrayList<String>(8);
    int start=0;
    int end=line.indexOf(separator,0);
    while(end>=0) {
      result.add(line.substring(start,end));
      start=end+1;
      end=line.indexOf(separator,start);
    }
    result.add(line.substring(start));
    return result;
  }
  public static int strcmp(final String a,final String b) {
    final int result=a.compareTo(b);
    if(result<0) {
      return -1;
    }else if(result>0) {
      return 1;
    }
    return 0;
  }
  public static int strArrCmp(@Nullable final List<String> a,@Nullable final List<String> b) {
    if(a==null&&b==null) {
      return 0;
    }
    if(a==null) {
      return -1;
    }
    if(b==null) {
      return 1;
    }
    final int len1=a.size();
    final int len2=b.size();
    if(len1==len2) {
      for(int i=0;i<len1;i++) {
        final int res=strcmp(a.get(i),b.get(i));
        if(res!=0) {
          return res;
        }
      }
      return 0;
    }
    return len1-len2;
  }
  public static String toString(@Nullable final Object object,final Consumer<StringBuilder> fieldsBuilder) {
    if(object==null) return "null";
    final var sb=new StringBuilder(object.getClass().getSimpleName());
    sb.append('{');
    fieldsBuilder.accept(sb);
    sb.append('}');
    return sb.toString();
  }
  private StringUtils() {}
}
