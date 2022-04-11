package com.learn.effective.cache.impl;

import com.learn.effective.cache.Cache;
import com.learn.effective.cache.CacheEntry;
import com.learn.effective.cache.DataBase;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheImpl implements Cache {

  private static final int CLEAN_UP_PERIOD_MILLIS = 5000;
  private static final int EXPIRY_TIME_MILLIS = 5000;
  private static final int INITIAL_FREQUENCY = 1;

  private static long cacheEvictions;

  private final int cacheCapacity;
  private final Map<Integer, CacheObject> cache;

  public CacheImpl(int cacheCapacity) {
    this.cacheCapacity = cacheCapacity;
    cache = new ConcurrentHashMap<>(cacheCapacity);

    Thread cleanerThread = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          Thread.sleep(CLEAN_UP_PERIOD_MILLIS);
          cache.entrySet().removeIf(entry -> Optional.ofNullable(entry.getValue())
              .map(CacheObject::isExpired)
              .orElse(false));
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });
    cleanerThread.setDaemon(true);
    cleanerThread.start();
  }

  @Override
  public void put(Integer key, CacheEntry value) {
    if (cache.containsKey(key)) {
      log.info("Value with key {} is already present", key);
      return;
    }
    if (cache.size() < cacheCapacity) {
      cache.put(key, CacheObject.builder()
          .cacheEntry(value)
          .dateTimeAdded(System.currentTimeMillis())
          .frequency(INITIAL_FREQUENCY)
          .build());
    } else {
      cache.remove(findLeastFrequentlyUsedEntry(cache));
      put(key, value);
    }
    log.info("Value with key {} is added successfully", value);
  }

  @Override
  public CacheEntry get(Integer key) {
    log.info("Get value by key: {}", key);
    if (cache.containsKey(key)) {
      CacheObject cacheObject = cache.get(key);
      cacheObject.setFrequency(cacheObject.getFrequency() + 1);
      cacheObject.setDateTimeAdded(System.currentTimeMillis());
      return cacheObject.getCacheEntry();
    } else {
      CacheEntry cacheEntry = DataBase.getEntry(key);
      put(key, cacheEntry);
      return cacheEntry;
    }
  }

  private int findLeastFrequentlyUsedEntry(Map<Integer, CacheObject> map) {
    int key = -1;
    int minFrequency = Integer.MAX_VALUE;
    int currentFrequency;

    for (Entry<Integer, CacheObject> integerPairEntry : map.entrySet()) {
      currentFrequency = integerPairEntry.getValue().getFrequency();
      if (currentFrequency < minFrequency) {
        minFrequency = currentFrequency;
        key = integerPairEntry.getKey();
      }
    }

    log.debug("Least frequently used key is: {}", key);
    return key;
  }

  @Override
  public int entryFrequency(Integer key) {
    if (cache.containsKey(key)) {
      return cache.get(key).getFrequency();
    }
    return 0;
  }

  @Override
  public int size() {
    return cache.size();
  }

  @Override
  public long cacheEvictions() {
    return cacheEvictions;
  }

  @Builder
  @Getter
  @Setter
  protected static class CacheObject {

    private CacheEntry cacheEntry;
    private long dateTimeAdded;
    private int frequency;

    public boolean isExpired() {
      if (System.currentTimeMillis() - dateTimeAdded > EXPIRY_TIME_MILLIS) {
        log.debug("Object {} is expired", cacheEntry);
        cacheEvictions++;
        return true;
      }
      return false;
    }
  }
}
