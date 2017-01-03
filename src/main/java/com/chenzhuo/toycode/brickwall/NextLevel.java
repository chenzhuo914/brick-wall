package com.chenzhuo.toycode.brickwall;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class NextLevel extends BrickLevel {
  
  // this class is used to record ohter alternatives
  private class Alternative {
    public int index;
    public int brick;
    
    public Alternative(int index, int brick) {
      this.index = index;
      this.brick = brick;
    }
  }
  
  final private int width;
  final private Set<Integer> prevBoundaries;

  private int curLength;  // current length so far
  private List<Integer> bricksList;
  private Stack<Alternative> alternatives;
  
  public NextLevel(BrickLevel prevLevel, int width) {
    this.width = width;
    this.prevBoundaries = prevLevel.getBoundaries();

    curLength = 0;
    bricksList = new ArrayList<Integer>();
    alternatives = new Stack<Alternative>();
  }
  
  private boolean canAddBrick(int i) {
    if (curLength >= width) {
      return false;
    }
    
    int nextLength = curLength + i;
    return (nextLength == width ||
            (nextLength < width && !prevBoundaries.contains(Integer.valueOf(nextLength))));
  }
  
  private boolean canAddBrick2() {
    return canAddBrick(2);
  }
  
  private boolean canAddBrick3() {
    return canAddBrick(3);
  }
  
  private boolean tryAddBricks() {
    boolean canAddBrick2 = canAddBrick2();
    boolean canAddBrick3 = canAddBrick3();
    if (canAddBrick2) {
      if (canAddBrick3) {
        alternatives.push(new Alternative(bricksList.size(), 3));
      }
      
      bricksList.add(Integer.valueOf(2));
      curLength += 2;
      return true;
      
    } else if (canAddBrick3) {
      bricksList.add(Integer.valueOf(3));
      curLength += 3;
      return true;
    }
    
    return false;
  }
  
  // convert result from List<Integer> to int[]
  public void saveResult() {
    bricks = new int[bricksList.size()];
    for (int i = 0; i < bricksList.size(); ++i) {
      bricks[i] = bricksList.get(i).intValue();
    }
  }
  
  private boolean checkSucceed() {
    return (curLength == width);
  }
  
  public boolean build() {
    if (checkSucceed()) {
      saveResult();
      return true;
    }
    
    while (tryAddBricks()) {
      if (checkSucceed()) {
        saveResult();
        return true;
      }
    }
    
    return next();
  }
  
  @Override
  public boolean next() {
    if (alternatives.isEmpty()) {
      return false;
    }
    
    Alternative alt = alternatives.pop();
    bricksList = bricksList.subList(0, alt.index);
    bricksList.add(Integer.valueOf(alt.brick));
    
    // update curLength
    curLength = 0;
    for (Integer i : bricksList) {
      curLength += i.intValue();
    }
    
    return build();
  }
}
