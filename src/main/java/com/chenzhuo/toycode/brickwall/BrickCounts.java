package com.chenzhuo.toycode.brickwall;

import java.util.ArrayList;
import java.util.List;

public class BrickCounts {

  public int count2; // number of bricks of width 2
  public int count3; // number of bricks of width 3

  public BrickCounts(int count2, int count3) {
      this.count2 = count2;
      this.count3 = count3;
  }
  
  // Find all possible brick combinations for a level (don't consider sequence here) 
  public static List<BrickCounts> getPossibleBrickCounts(int width) {
    List<BrickCounts> result = new ArrayList<BrickCounts>();
    if (width <2) {
      return result;
    }
    
    int count2 = width / 2;
    int count3 = 0;
    int remainder = width - count2 * 2;
    
    // replace 2-width to 3-width according to remainder to make up width
    count2 -= remainder;
    count3 += remainder;
    result.add(new BrickCounts(count2, count3));
    
    // continue 2-width to 3-width substitution
    while (count2 >= 3) {
      count2 -= 3;
      count3 += 2;
      result.add(new BrickCounts(count2, count3));
    }
    
    return result;
  }
}
