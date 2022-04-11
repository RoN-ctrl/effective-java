package com.learn.effective.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.learn.effective.extra.BinarySearch;
import java.util.List;
import org.junit.jupiter.api.Test;

class BinarySearchTest {

  @Test
  void binarySearchTest() {
    List<Integer> testList = List.of(10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
    BinarySearch binarySearch = new BinarySearch();

    int recursively = binarySearch.searchRecursively(testList, 16, 0, testList.size() - 1);
    int iteratively = binarySearch.searchIteratively(testList, 16);

    assertEquals(6, recursively);
    assertEquals(6, iteratively);
  }

  @Test
  void binarySearchUnsortedTest() {
    List<Integer> testList = List.of(10, 25, 5, 54, 22, 11, 9, 17, 36, 19);
    BinarySearch binarySearch = new BinarySearch();

    int iteratively = binarySearch.searchUnsorted(testList, 9);

    assertEquals(1, iteratively);
  }
}