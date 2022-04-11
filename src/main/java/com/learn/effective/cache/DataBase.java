package com.learn.effective.cache;

import java.util.HashMap;
import java.util.Map;

public class DataBase {

  private static final Map<Integer, CacheEntry> map = new HashMap<>();

  private DataBase() {
  }

  public static CacheEntry getEntry(Integer key) {
    return map.get(key);
  }

  public static void putEntry(Integer key, CacheEntry cacheEntry) {
    map.put(key, cacheEntry);
  }

}
