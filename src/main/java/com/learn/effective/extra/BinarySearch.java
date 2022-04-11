package com.learn.effective.extra;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinarySearch {

  public int searchRecursively(List<Integer> list, int key, int low, int high) {
    int mid = low + ((high - low) / 2);

    if (high < low) {
      return -1;
    }

    if (key == list.get(mid)) {
      return mid;
    } else if (key < list.get(mid)) {
      return searchRecursively(list, key, low, mid - 1);
    } else {
      return searchRecursively(list, key, mid + 1, high);
    }
  }

  public int searchIteratively(List<Integer> list, int key) {
    int index = -1;

    int low = 0;
    int high = list.size() - 1;

    while (low <= high) {
      int mid = low  + ((high - low) / 2);
      if (list.get(mid) < key) {
        low = mid + 1;
      } else if (list.get(mid) > key) {
        high = mid - 1;
      } else if (list.get(mid) == key) {
        index = mid;
        break;
      }
    }
    return index;
  }

  public int searchUnsorted(List<Integer> unsortedList, int key) {
    MergeSort mergeSort = new MergeSort();
    int[] array = unsortedList.stream().mapToInt(Integer::intValue).toArray();
    mergeSort.sort(array, array.length);
    List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList());

    int index = -1;

    int low = 0;
    int high = list.size() - 1;

    while (low <= high) {
      int mid = low  + ((high - low) / 2);
      if (list.get(mid) < key) {
        low = mid + 1;
      } else if (list.get(mid) > key) {
        high = mid - 1;
      } else if (list.get(mid) == key) {
        index = mid;
        break;
      }
    }
    return index;
  }
}
