package kr.kkiro.javastone.util;

import java.util.Random;

public class RandomUtil {
  private static Random random;
  
  public static Random getRandom() {
    if (random == null) random = new Random();
    return random;
  }
}
