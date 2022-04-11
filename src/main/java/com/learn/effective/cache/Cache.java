package com.learn.effective.cache;

public interface Cache {

  void put(Integer key, CacheEntry value);

  CacheEntry get(Integer key);

  int size();

  int entryFrequency(Integer key);

  long cacheEvictions();

}
