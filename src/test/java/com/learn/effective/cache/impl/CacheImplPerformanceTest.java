package com.learn.effective.cache.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.learn.effective.util.Util;
import com.learn.effective.cache.Cache;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Slf4j
class CacheImplPerformanceTest {

  private static final int DB_CAPACITY = 1_000_000;
  private static final int CACHE_CAPACITY = 10_000;

  @BeforeAll
  static void setUp() {
    Util.populateDB(DB_CAPACITY);
  }

  @Test
  void allNewPerformanceTest() {
    log.info("started allNewPerformanceTest()");

    Cache cache = new CacheImpl(CACHE_CAPACITY);
    long start;
    long finalTime;

    log.info("first 10k");
    start = System.currentTimeMillis();
    for (int i = 0; i < CACHE_CAPACITY; i++) {
      cache.get(i);
    }
    finalTime = System.currentTimeMillis() - start;
    log.info("time: {}", finalTime);

    log.info("second 10k");
    start = System.currentTimeMillis();
    for (int i = CACHE_CAPACITY; i < CACHE_CAPACITY * 2; i++) {
      cache.get(i);
    }
    finalTime = System.currentTimeMillis() - start;
    log.info("time: {}", finalTime);
  }

  @Test
  void randomPerformanceTest() {
    log.info("started randomPerformanceTest()");

    Cache cache = new CacheImpl(CACHE_CAPACITY);
    long start;
    long finalTime;

    log.info("random first 10k");
    start = System.currentTimeMillis();
    for (int i = 0; i < CACHE_CAPACITY; i++) {
      cache.get(i);
    }
    finalTime = System.currentTimeMillis() - start;
    log.info("time: {}", finalTime);

    SecureRandom random = new SecureRandom();
    log.info("random second 10k");
    start = System.currentTimeMillis();
    for (int i = 0; i < CACHE_CAPACITY; i++) {
      cache.get(random.nextInt(DB_CAPACITY));
    }
    finalTime = System.currentTimeMillis() - start;
    log.info("time: {}", finalTime);
  }

  @Test
  void cacheEvictionsTest() {
    log.info("started cacheEvictionsTest()");

    Cache cache = new CacheImpl(CACHE_CAPACITY);
    SecureRandom random = new SecureRandom();

    for (int i = 0; i < CACHE_CAPACITY * 10; i++) {
      cache.get(random.nextInt(DB_CAPACITY));
    }

    log.info("Expired cache entries: {}", cache.cacheEvictions());
  }
}