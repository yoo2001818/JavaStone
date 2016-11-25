package kr.kkiro.javastone.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import kr.kkiro.javastone.game.card.Card;

public class CardView extends JPanel {
  private JLabel cardName;
  private JLabel cardIcon;
  private JTextPane cardDescription;
  
  private Card card;

  /**
   * Create the panel.
   */
  public CardView() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
    JPanel panel = new JPanel();
    panel.setBackground(new Color(0, 0, 0));
    panel.setPreferredSize(new Dimension(50, 50));
    panel.setMaximumSize(new Dimension(50, 50));
    panel.setMinimumSize(new Dimension(50, 50));
    add(panel);
    panel.setLayout(null);
    
    cardIcon = new JLabel("");
    cardIcon.setBounds(1, 1, 48, 48);
    panel.add(cardIcon);
    
    cardName = new JLabel("CardName");
    cardName.setFont(new Font("Dialog", Font.BOLD, 12));
    cardName.setAlignmentX(0.5f);
    add(cardName);
    
    cardDescription = new JTextPane();
    cardDescription.setEditable(false);
    cardDescription.setText("cardDescription");
    add(cardDescription);

  }
  
  public CardView(Card card) {
    this();
    setCard(card);
  }
  
  public void setCard(Card card) {
    this.card = card;
    updateView();
  }
  
  public Card getCard() {
    return card;
  }
  
  public void updateView() {
    if (card == null) {
      this.setVisible(false);
      return;
    }
    this.setVisible(true);
    cardIcon.setIcon(new ImageIcon(card.getIcon()));
    cardName.setText(card.toString());
    cardDescription.setText(card.getDescription());
    StyledDocument doc = cardDescription.getStyledDocument();
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    doc.setParagraphAttributes(0, doc.getLength(), center, false);
  }

}
