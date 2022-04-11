package com.learn.effective.cache.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.learn.effective.util.Util;
import com.learn.effective.cache.Cache;
import com.learn.effective.cache.DataBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CacheImplTest {

  @BeforeAll
  static void setUp() {
    Util.populateDB(10);
  }

  @Test
  void putTest() {
    int cacheCapacity = 2;
    Cache cache = new CacheImpl(cacheCapacity);
    cache.put(0, DataBase.getEntry(0));
    cache.put(1, DataBase.getEntry(1));
    cache.put(2, DataBase.getEntry(2));

    assertNotNull(cache.get(2));
    assertEquals(cacheCapacity, cache.size());
  }

  @Test
  void getTest() {
    int cacheCapacity = 2;
    Cache cache = new CacheImpl(cacheCapacity);
    cache.put(0, DataBase.getEntry(0));
    cache.get(0);

    assertNotNull(cache.get(0));
    assertEquals(3, cache.entryFrequency(0));
    assertNotNull(cache.get(1));
  }
}