package org.eclipse.tm4e.core.internal.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.jdt.annotation.Nullable;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import pama1234.shift.misc.NonNull;

public final class ObjectCloner{
  private static final LoadingCache<Class<?>,Optional<Method>> CLONE_METHODS=CacheBuilder.newBuilder().weakKeys()
    .build(new CacheLoader<>() {
      @Override
      public Optional<Method> load(final Class<?> cls) {
        try {
          return Optional.of(cls.getMethod("clone"));
        }catch(final Exception ex) {
          return Optional.empty();
        }
      }
    });
  public static <@NonNull T> T deepClone(final T obj) {
    return deepClone(obj,new IdentityHashMap<>());
  }
  @SuppressWarnings("unchecked")
  private static <@NonNull T> T deepClone(final T obj,final Map<Object,@Nullable Object> clones) {
    final Object clone=clones.get(obj);
    if(clone!=null) return (T)clone;
    if(obj instanceof final List<?> list) {
      final List<Object> listClone=(List<Object>)shallowClone(list,()->new ArrayList<>(list));
      clones.put(list,listClone);
      listClone.replaceAll(v->deepCloneNullable(v,clones));
      return (T)listClone;
    }
    if(obj instanceof final Set<?> set) {
      final var setClone=(Set<@Nullable Object>)shallowClone(set,HashSet::new);
      clones.put(set,setClone);
      setClone.clear();
      for(final var e:set) {
        setClone.add(deepCloneNullable(e,clones));
      }
      return (T)setClone;
    }
    if(obj instanceof final Map<?,?> map) {
      final Map<Object,Object> mapClone=(Map<Object,Object>)shallowClone(map,()->new HashMap<>(map));
      clones.put(map,mapClone);
      mapClone.replaceAll((k,v)->deepCloneNullable(v,clones));
      return (T)mapClone;
    }
    if(obj.getClass().isArray()) {
      final int len=Array.getLength(obj);
      final var arrayType=obj.getClass().getComponentType();
      final var arrayClone=Array.newInstance(arrayType,len);
      clones.put(obj,arrayClone);
      for(int i=0;i<len;i++) {
        Array.set(arrayClone,i,deepCloneNullable(Array.get(obj,i),clones));
      }
      return (T)arrayClone;
    }
    final var shallowClone=shallowClone(obj,()->obj);
    clones.put(obj,shallowClone);
    return obj;
  }
  @Nullable
  private static <@Nullable T> T deepCloneNullable(final T obj,final Map<Object,@Nullable Object> clones) {
    if(obj==null) {
      return null;
    }
    return deepClone(obj,clones);
  }
  @SuppressWarnings("unchecked")
  private static <@NonNull T> T shallowClone(final T obj,final Supplier<T> fallback) {
    if(obj instanceof Cloneable) {
      try {
        final var objClass=obj.getClass();
        final var cloneMethod=CLONE_METHODS.get(objClass);
        if(cloneMethod.isPresent()) {
          return (T)cloneMethod.get().invoke(obj);
        }
      }catch(final Exception ex) {
        ex.printStackTrace();
      }
    }
    return fallback.get();
  }
  private ObjectCloner() {}
}
