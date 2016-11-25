package kr.kkiro.javastone.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import kr.kkiro.javastone.game.Hero;
import kr.kkiro.javastone.game.Minion;
import kr.kkiro.javastone.game.Player;
import kr.kkiro.javastone.game.card.Card;

public class PlayerSection extends JPanel {
  
  public static interface SelectListener {
    void entitySelected(Object entity);
  }

  protected JPanel heroSection;
  private JPanel minionSection;
  private JPanel heroWeapon;
  private JPanel heroAbility;
  private JPanel heroDisplay;
  private JLabel costLabel;
  private JLabel heroMessageLabel;
  private JLabel heroWeaponAttack;
  private JLabel heroWeaponShield;
  private JLabel heroWeaponImage;
  private JLabel heroAttack;
  private JLabel heroHealth;
  private JLabel heroImage;
  private JLabel heroAbilityCost;
  private JLabel heroAbilityImage;
  
  private List<SelectListener> selectListeners = new LinkedList<>();
  
  protected boolean inverted;
  protected Player player;
  
  public PlayerSection() {
    setLayout(new BorderLayout(0, 0));
    
    heroSection = new JPanel();
    add(heroSection, BorderLayout.NORTH);
    heroSection.setBorder(new EmptyBorder(3, 3, 3, 3));
    heroSection.setLayout(new BoxLayout(heroSection, BoxLayout.X_AXIS));
    
    JPanel heroMessage = new JPanel();
    heroMessage.setBorder(new EmptyBorder(0, 0, 0, 10));
    heroMessage.setMaximumSize(new Dimension(32767, 100));
    heroMessage.setMinimumSize(new Dimension(0, 0));
    heroMessage.setPreferredSize(new Dimension(0, 0));
    heroSection.add(heroMessage);
    heroMessage.setLayout(new BorderLayout(0, 0));
    
    costLabel = new JLabel("1/10");
    heroMessage.add(costLabel, BorderLayout.NORTH);
    
    heroMessageLabel = new JLabel("빛이 당신을 태울 것입니다!");
    heroMessageLabel.setHorizontalAlignment(SwingConstants.TRAILING);
    heroMessage.add(heroMessageLabel);
    
    Component verticalStrut = Box.createVerticalStrut(14);
    heroMessage.add(verticalStrut, BorderLayout.SOUTH);
    
    JPanel heroWeaponFiller = new JPanel();
    heroWeaponFiller.setBorder(null);
    heroWeaponFiller.setPreferredSize(new Dimension(50, 50));
    heroWeaponFiller.setSize(new Dimension(50, 50));
    heroWeaponFiller.setMaximumSize(new Dimension(50, 50));
    heroWeaponFiller.setMinimumSize(new Dimension(50, 50));
    heroSection.add(heroWeaponFiller);
    heroWeaponFiller.setLayout(null);
    
    heroWeapon = new JPanel();
    heroWeapon.setLocation(0, 0);
    heroWeaponFiller.add(heroWeapon);
    heroWeapon.setBorder(null);
    heroWeapon.setLayout(null);
    heroWeapon.setSize(new Dimension(50, 50));
    heroWeapon.setPreferredSize(new Dimension(50, 50));
    heroWeapon.setMinimumSize(new Dimension(50, 50));
    heroWeapon.setMaximumSize(new Dimension(50, 50));
    heroWeapon.setBackground(Color.BLACK);
    
    heroWeaponAttack = new JLabel("2");
    heroWeaponAttack.setBounds(1, 36, 24, 14);
    heroWeapon.add(heroWeaponAttack);
    
    heroWeaponShield = new JLabel("8");
    heroWeaponShield.setBounds(25, 36, 24, 14);
    heroWeapon.add(heroWeaponShield);
    heroWeaponShield.setHorizontalAlignment(SwingConstants.TRAILING);
    
    heroWeaponImage = new JLabel("");
    heroWeaponImage.setVerticalAlignment(SwingConstants.TOP);
    heroWeaponImage.setIcon(new ImageIcon("/data/yoo2001818/문서/placeholder.png"));
    heroWeaponImage.setBounds(1, 1, 48, 48);
    heroWeapon.add(heroWeaponImage);
    
    Component horizontalStrut = Box.createHorizontalStrut(5);
    heroSection.add(horizontalStrut);
    
    heroDisplay = new JPanel();
    heroDisplay.setBorder(null);
    heroDisplay.setMaximumSize(new Dimension(80, 80));
    heroDisplay.setSize(new Dimension(80, 80));
    heroDisplay.setPreferredSize(new Dimension(80, 80));
    heroDisplay.setMinimumSize(new Dimension(80, 80));
    heroDisplay.setBackground(Color.BLACK);
    heroSection.add(heroDisplay);
    heroDisplay.setLayout(null);
    heroDisplay.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        fireEntitySelected(player.getHero());
      }
    });
    
    heroAttack = new JLabel("30");
    heroAttack.setOpaque(true);
    heroAttack.setBounds(0, 66, 24, 14);
    heroAttack.setBackground(new Color(255, 204, 102));
    heroDisplay.add(heroAttack);
    
    heroHealth = new JLabel("30");
    heroHealth.setOpaque(true);
    heroHealth.setHorizontalAlignment(SwingConstants.TRAILING);
    heroHealth.setBounds(56, 66, 24, 14);
    heroHealth.setBackground(new Color(255, 102, 102));
    heroDisplay.add(heroHealth);
    
    heroImage = new JLabel("");
    heroImage.setIcon(new ImageIcon("/data/yoo2001818/문서/placeholderHero.png"));
    heroImage.setVerticalAlignment(SwingConstants.TOP);
    heroImage.setBounds(1, 1, 78, 78);
    heroDisplay.add(heroImage);
    
    Component horizontalStrut_1 = Box.createHorizontalStrut(5);
    heroSection.add(horizontalStrut_1);
    
    JPanel heroAbilityFiller = new JPanel();
    heroAbilityFiller.setBorder(null);
    heroAbilityFiller.setSize(new Dimension(50, 50));
    heroAbilityFiller.setPreferredSize(new Dimension(50, 50));
    heroAbilityFiller.setMinimumSize(new Dimension(50, 50));
    heroAbilityFiller.setMaximumSize(new Dimension(50, 50));
    heroSection.add(heroAbilityFiller);
    heroAbilityFiller.setLayout(null);
    
    heroAbility = new JPanel();
    heroAbility.setLocation(0, 0);
    heroAbilityFiller.add(heroAbility);
    heroAbility.setLayout(null);
    heroAbility.setSize(new Dimension(50, 50));
    heroAbility.setPreferredSize(new Dimension(50, 50));
    heroAbility.setMinimumSize(new Dimension(50, 50));
    heroAbility.setMaximumSize(new Dimension(50, 50));
    heroAbility.setBorder(null);
    heroAbility.setBackground(Color.BLACK);
    heroAbility.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        fireEntitySelected(player.getHero().getAbility());
      }
    });
    
    heroAbilityCost = new JLabel("8");
    heroAbilityCost.setOpaque(true);
    heroAbilityCost.setBackground(new Color(204, 204, 204));
    heroAbilityCost.setHorizontalAlignment(SwingConstants.RIGHT);
    heroAbilityCost.setBounds(36, 0, 14, 14);
    heroAbility.add(heroAbilityCost);
    
    heroAbilityImage = new JLabel("");
    heroAbilityImage.setIcon(new ImageIcon("/data/yoo2001818/문서/placeholder.png"));
    heroAbilityImage.setVerticalAlignment(SwingConstants.TOP);
    heroAbilityImage.setBounds(1, 1, 48, 48);
    heroAbility.add(heroAbilityImage);
    
    Component horizontalGlue = Box.createHorizontalGlue();
    heroSection.add(horizontalGlue);
    
    minionSection = new JPanel();
    add(minionSection, BorderLayout.CENTER);
    minionSection.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    minionSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
  }
  
  public PlayerSection(boolean inverted) {
    this();
    this.inverted = inverted;
    if (inverted) {
      remove(this.heroSection);
      add(this.heroSection, BorderLayout.SOUTH);
    }
  }
  
  public PlayerSection(Player player) {
    this();
    this.setPlayer(player);
  }
  
  public PlayerSection(Player player, boolean inverted) {
    this(inverted);
    this.setPlayer(player);
  }
  
  public Player getPlayer() {
    return player;
  }
  
  public void setPlayer(Player player) {
    this.player = player;
    updateView();
  }
  
  public boolean isInverted() {
    return inverted;
  }
  
  public void setInverted(boolean inverted) {
    this.inverted = inverted;
  }
  
  public void addSelectListener(SelectListener l) {
    selectListeners.add(l);
  }
  
  public void removeSelectListener(SelectListener l) {
    selectListeners.remove(l);
  }
  
  protected void fireEntitySelected(Object entity) {
    for (SelectListener l : selectListeners) {
      l.entitySelected(entity);
    }
  }
  
  public void updateView() {
    // Update cost information
    costLabel.setText(player.getPoints() + "/" + player.getMaxPoints());
    // Update hero information
    Hero hero = player.getHero();
    heroDisplay.setBackground(hero.getInteractSeq() == hero.getSession().getSeqId() ?
        Color.BLUE : Color.BLACK);
    heroMessageLabel.setText(inverted ? hero.getStartAgainst() : hero.getStart());
    // Hide hero stuff for now
    heroWeapon.setVisible(false);
    Card abilityCard = hero.getAbility().getCard();
    heroAbility.setVisible(abilityCard != null);
    if (abilityCard != null) {
      heroAbilityImage.setIcon(new ImageIcon(abilityCard.getIcon()));
      heroAbilityCost.setText(Integer.toString(abilityCard.getCost()));
    }
    heroAttack.setVisible(hero.getStrength() != 0);
    heroAttack.setText(Integer.toString(hero.getStrength()));
    heroHealth.setText(Integer.toString(hero.getHealth()));
    // Run diff algorithm for minions - This is done by removing excess node / adding node.
    // (Which is really inefficient)
    Component[] panels = minionSection.getComponents();
    for (int i = 0; i < panels.length; ++i) {
      Component component = panels[i];
      if (!(component instanceof MinionPanel)) continue;
      MinionPanel minionPanel = (MinionPanel) component;
      // Check bounds, then remove excess node
      if (i >= player.getMinions().size()) {
        // Possible ConcurrentModificationException?
        minionSection.remove(minionPanel);
      } else {
        Minion minion = player.getMinions().get(i);
        minionPanel.setMinion(minion);
      }
    }
    for (int i = panels.length; i < player.getMinions().size(); ++i) {
      // Add moar nodes
      MinionPanel minionPanel = new MinionPanel(player.getMinions().get(i));
      minionSection.add(minionPanel);
      minionPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          // Notify selection to the above object
          fireEntitySelected(((MinionPanel)e.getComponent()).getMinion());
        }
      });
    }
    minionSection.invalidate();
    minionSection.doLayout();
    minionSection.repaint();
  }

}
