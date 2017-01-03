package com.chenzhuo.toycode.brickwall;

public class FirstLevel extends BrickLevel {
    
  public FirstLevel(int count2, int count3) {
    bricks = new int[count2 + count3];
    int index = 0;
    while (index < count2) {
      bricks[index++] = 2;
    }
    while (index < count2 + count3) {
      bricks[index++] = 3;
    }
  }
  
  @Override
  public boolean next() {
    return Util.nextLexicalPerm(bricks);
  }
}
