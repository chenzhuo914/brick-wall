package com.chenzhuo.toycode.brickwall;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BrickWall {  
  private int width;
  private int height;
  
  private BrickLevel[] levels;
  private long solutions;
  
  
  public BrickWall(int width, int height) {
    this.width = width;
    this.height = height;
    
    levels = new BrickLevel[height];
    solutions = 0;
  }
  
  private void solve1() {
    solutions = 0;
    
    List<BrickCounts> firstLevelBrickCounts = BrickCounts.getPossibleBrickCounts(width);
    for (BrickCounts bc : firstLevelBrickCounts) {
      // this loop can be parallelized!
      levels[0] = new FirstLevel(bc.count2, bc.count3);
      if (build(0)) {
        while(next()) {
          // do nothing
        }
      }
    }
    
    printResult();
  }
  
  // build a solution from given level l onwards
  private boolean build(int l) {
    for (int i = l + 1; i < height; ++i) {
      NextLevel nl = new NextLevel(levels[i - 1], width);
      levels[i] = nl;
      if (!nl.build()) {
        return false;
      }
    }

    logAndPrintSolution();
    return true;
  }
  
  private boolean next() {
    // find a next brick combination in latest possible level
    int l = height - 1;
    for (; l >= 0; --l) {
      if (levels[l].next()) {
        break;
      }
    }
    
    if (l == height -1) {
      // found a solution by just changing the last level
      logAndPrintSolution();
      return true;
    }
    

    if (l < 0) {
      // no solution
      return false;
    }
    
    if (build(l)) {
      return true;
    } else {
      return next();
    }
  }
  
  
  // log and print current solution
  public void logAndPrintSolution() {
    solutions++;
    /*
    System.out.println("Solution #" + solutions + ":");
    
    int digits = Util.countDigits(height);    
    for (int i = 1; i <= height; ++i) {
      System.out.print("Level ");
      int paddings = digits - Util.countDigits(i);
      for (int j = 0; j < paddings; ++j) {
        System.out.print(" ");
      }
      System.out.print(i + ":  ");
      System.out.println(levels[i - 1]);
    }
    */
  }
  
  public void printResult() {
    System.out.println("Found " + solutions + " solutions!");
  }

  // don't actually iterate all levels, but just build a curLevel -> nextLevel map that covers
  // all possibilities, then do the math calculation
  private void solve2() {
    solutions = 0;
    
    Map<Integer, List<Integer>> levelMapping = buildLevelMapping();
    Map<Integer, Long> countsMap = aggregateCounts(levelMapping, height - 1);
    
    // do calculation
    for (Long v : countsMap.values()) {
      solutions += v.longValue();
    }
    
    printResult();
  }
  
  private Map<Integer, List<Integer>> buildLevelMapping() {
    // build up mappings
    Map<Integer, List<Integer>> result = new HashMap<>();
    List<BrickCounts> firstLevelBrickCounts = BrickCounts.getPossibleBrickCounts(width);
    FirstLevel curLevel = null;
    for (BrickCounts bc : firstLevelBrickCounts) {
      curLevel = new FirstLevel(bc.count2, bc.count3);
      
      do {
        Integer mapKey = Integer.valueOf(curLevel.hashCode());
        
        if (!result.containsKey(mapKey)) {
          NextLevel nextLevel = new NextLevel(curLevel, width);
          List<Integer> mapValue = new ArrayList<Integer>();
        
          if (nextLevel.build()) {
            do {
              mapValue.add(Integer.valueOf(nextLevel.hashCode()));
            } while (nextLevel.next());
          }
          
          result.put(mapKey, mapValue);
        }
      } while (curLevel.next());
    }
    
    return result;
  }
  
  // param level means how many levels behind each key
  private Map<Integer, Long> aggregateCounts(Map<Integer, List<Integer>> levelMapping, int level) {
    int size = levelMapping.size();
    
    Map<Integer, Long> result = new HashMap<>(size);
    for (Entry<Integer, List<Integer>> entry : levelMapping.entrySet()) {
      result.put(entry.getKey(), Long.valueOf(entry.getValue().size()));
    }
    
    for (int i = 0; i < level - 1; ++i) {
      // copy result of previous level
      Map<Integer, Long> countPrevLevel = new HashMap<>(result);
      result.clear();
      
      for (Entry<Integer, List<Integer>> entry : levelMapping.entrySet()) {
        Integer key = entry.getKey();
        long count = 0;
        for (Integer v : entry.getValue()) {
          count += countPrevLevel.get(v).longValue();
        }
        result.put(key, Long.valueOf(count));
      }
    }

    return result;
  }
  
  public static void main(String[] args) {
    //BrickWall wall = new BrickWall(1, 2);    //0 solution
    //BrickWall wall = new BrickWall(2, 5);    //1 solution
    //BrickWall wall = new BrickWall(4, 2);    //0 solution
    //BrickWall wall = new BrickWall(5, 100);  //2 solution
    //BrickWall wall = new BrickWall(9, 3);    //8 solutions
    //BrickWall wall = new BrickWall(10, 20);  //35424 solutions!
    //BrickWall wall = new BrickWall(20, 10);  //94082988 solutions
    BrickWall wall = new BrickWall(32, 10);  //806844323190414 solutions
    wall.solve2();
    wall.solve1();
  }
}
