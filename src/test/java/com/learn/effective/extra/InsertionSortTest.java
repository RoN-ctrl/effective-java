package com.learn.effective.extra;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import com.learn.effective.extra.InsertionSort;
import org.junit.jupiter.api.Test;

class InsertionSortTest {

  InsertionSort insertionSort = new InsertionSort();

  @Test
  void insertionSortTest() {
    int[] input = {6, 2, 3, 4, 5, 1};
    insertionSort.sort(input);
    int[] expected = {1, 2, 3, 4, 5, 6};
    assertArrayEquals(expected, input);
  }
}