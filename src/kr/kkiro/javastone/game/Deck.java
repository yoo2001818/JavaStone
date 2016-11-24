package kr.kkiro.javastone.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kr.kkiro.javastone.util.RandomUtil;

public class Deck {
  protected List<Card> cards = new ArrayList<Card>();
  
  public Deck() {
    
  }
  
  void push(Card card) {
    cards.add(card);
  }
  Card pop() {
    return cards.remove(cards.size() - 1);
  }
  void unshift(Card card) {
    cards.add(0, card);
  }
  Card shift() {
    return cards.remove(0);
  }
  
  int size() {
    return cards.size();
  }
  
  void shuffle() {
    Random random = RandomUtil.getRandom();
    for (int i = 0; i < cards.size() - 2; ++i) {
      int j = random.nextInt(cards.size() - i) + i;
      Card iCard = cards.get(i);
      Card jCard = cards.get(j);
      cards.set(i, jCard);
      cards.set(j, iCard);
    }
  }
}
