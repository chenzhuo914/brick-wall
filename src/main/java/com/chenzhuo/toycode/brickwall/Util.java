package com.chenzhuo.toycode.brickwall;

public class Util {

  public static boolean nextLexicalPerm(int[] data) {
    if (data.length <= 1) {
      return false;
    }
    
    int k = data.length - 2;
    while (data[k] >= data[k + 1]) {
        k--;
        if (k < 0) {
            return false;
        }
    }
    int l = data.length - 1;
    while (data[k] >= data[l]) {
        l--;
    }
    Util.swap(data, k, l);
    int length = data.length - (k + 1);
    for (int i = 0; i < length / 2; i++) {
        Util.swap(data, k + 1 + i, data.length - i - 1);
    }
    return true;
  }
  
  public static void swap(int[] data, int i, int j) {
    int temp = data[i];
    data[i] = data[j];
    data[j] = temp;
  }
  
  public static int countDigits(int number) {
    int digits = 1;
    while (number > 10) {
      digits++;
      number /= 10;
    }
    
    return digits;
  }
}
