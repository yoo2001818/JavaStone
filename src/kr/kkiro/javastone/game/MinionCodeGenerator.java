package kr.kkiro.javastone.game;

public interface MinionCodeGenerator {
  public MinionListener generate(Session session, Minion minion);
}
