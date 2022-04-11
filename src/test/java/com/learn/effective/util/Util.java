package com.learn.effective.util;

import com.learn.effective.cache.CacheEntry;
import com.learn.effective.cache.DataBase;

public class Util {

  public static void populateDB(int quantity) {
    for (int i = 0; i < quantity; i++) {
      DataBase.putEntry(i, new CacheEntry(String.valueOf(i)));
    }
  }
}
