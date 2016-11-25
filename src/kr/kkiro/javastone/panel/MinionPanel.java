package kr.kkiro.javastone.panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import kr.kkiro.javastone.game.Minion;

public class MinionPanel extends JPanel {

  protected Minion minion;
  private JLabel minionHealth;
  private JLabel minionStrength;
  private JLabel minionImage;
  private JLabel sleepingLabel;
  
  public MinionPanel() {
    setToolTipText("하수인");
    setLayout(null);
    setSize(new Dimension(80, 80));
    setPreferredSize(new Dimension(50, 50));
    setMinimumSize(new Dimension(50, 50));
    setMaximumSize(new Dimension(50, 50));
    setBackground(Color.BLACK);
    
    minionHealth = new JLabel("1");
    minionHealth.setBackground(new Color(255, 102, 102));
    minionHealth.setOpaque(true);
    minionHealth.setHorizontalAlignment(SwingConstants.TRAILING);
    minionHealth.setBounds(35, 36, 15, 14);
    add(minionHealth);
    
    minionStrength = new JLabel("1");
    minionStrength.setOpaque(true);
    minionStrength.setBackground(new Color(255, 204, 102));
    minionStrength.setBounds(0, 36, 15, 14);
    add(minionStrength);
    
    sleepingLabel = new JLabel("ZZ");
    sleepingLabel.setOpaque(true);
    sleepingLabel.setHorizontalAlignment(SwingConstants.TRAILING);
    sleepingLabel.setBounds(28, 1, 21, 14);
    add(sleepingLabel);
    
    minionImage = new JLabel("");
    minionImage.setVerticalAlignment(SwingConstants.TOP);
    minionImage.setBounds(1, 1, 48, 48);
    add(minionImage);
  }
  
  public MinionPanel(Minion minion) {
    this();
    this.setMinion(minion);
  }
  
  public void setMinion(Minion minion) {
    this.minion = minion;
    updateView();
  }
  
  public Minion getMinion() {
    return minion;
  }
  
  public void updateView() {
    minionHealth.setText(Integer.toString(minion.getHealth()));
    minionStrength.setText(Integer.toString(minion.getStrength()));
    sleepingLabel.setVisible(!minion.isReady());
    minionImage.setIcon(new ImageIcon(minion.getIcon()));
    setToolTipText(minion.getName());
    setBackground(minion.getInteractSeq() == minion.getSession().getSeqId() ? Color.BLUE : 
        minion.isTaunt() ? Color.RED : Color.BLACK);
    // That's all for now.
  }
}
