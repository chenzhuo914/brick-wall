package com.chenzhuo.toycode.brickwall;
import java.util.HashSet;
import java.util.Set;

public abstract class BrickLevel {
  static final String BRICK2_STRING = "     |";
  static final String BRICK3_STRING = "        |";
  
  protected int[] bricks;

  public abstract boolean next();
  
  public Set<Integer> getBoundaries() {
    Set<Integer> result = new HashSet<Integer>();
    
    int boundary = 0;
    // don't include the last one (=width)
    for (int i = 0; i < bricks.length - 1; ++i) {
      boundary += bricks[i];
      result.add(Integer.valueOf(boundary));
    }
    return result;
  }
  
  public int[] getBricks() {
    return bricks;
  }
  
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("|");
    for (int i : bricks) {
      if (i == 2) {
        sb.append(BRICK2_STRING);
      } else if (i == 3) {
        sb.append(BRICK3_STRING);
      } else {
        return ("ERROR - Unknown brick size: " + i);
      }
    }
    
    return sb.toString();
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    for (int i = 0; i < bricks.length; ++i) {
      result = prime * result + bricks[i];
    }
    
    return result;
  }
}
