package kr.kkiro.javastone.game;

import kr.kkiro.javastone.game.card.Card;

public class HeroData {
  protected String name = "제이나";
  protected Card heroSpell;
  protected String start = "후회하게 해드리죠.";
  protected String startAgainst = "과연 그럴 수 있을까요?";
  protected String suicide = "이번엔 당신이 이겼어요.";
  protected String thanks = "고마워요.";
  protected String impressed = "놀랍군요!";
  protected String oops = "이런! 실수했네요.";
  protected String wellPlayed = "잘 했어요.";
  protected String greetings = "반가워요.";
  protected String threaten = "제 마법이 당신을 조각낼 거에요.";
  
  public String getName() {
    return name;
  }
  public Card getHeroSpell() {
    return heroSpell;
  }
  public String getStart() {
    return start;
  }
  public String getStartAgainst() {
    return startAgainst;
  }
  public String getSuicide() {
    return suicide;
  }
  public String getThanks() {
    return thanks;
  }
  public String getImpressed() {
    return impressed;
  }
  public String getOops() {
    return oops;
  }
  public String getWellPlayed() {
    return wellPlayed;
  }
  public String getGreetings() {
    return greetings;
  }
  public String getThreaten() {
    return threaten;
  }
  
  
}
