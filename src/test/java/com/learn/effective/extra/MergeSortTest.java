package com.learn.effective.extra;

import static org.junit.jupiter.api.Assertions.*;

import com.learn.effective.extra.MergeSort;
import org.junit.jupiter.api.Test;

class MergeSortTest {

  @Test
  void mergeSortTest() {
    MergeSort mergeSort = new MergeSort();

    int[] actual = { 5, 1, 6, 2, 3, 4 };
    int[] expected = { 1, 2, 3, 4, 5, 6 };
    mergeSort.sort(actual, actual.length);
    assertArrayEquals(expected, actual);
  }
}