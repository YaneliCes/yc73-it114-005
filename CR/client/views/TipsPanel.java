package CR.client.views;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CR.client.ICardControls;
import CR.client.Card;

public class TipsPanel extends JPanel {
    JPanel container;
    JLabel message;

    public TipsPanel(ICardControls controls) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(13, 13, 4, 4));
        container = new JPanel(new BoxLayout(this, BoxLayout.Y_AXIS));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        
        String instructionsText = "<html><font size='5'; color='blue'><u>List of Commands</u></font><br><br>Flip a Coin:<br>/flip<br><br>Dice Roll:<br>/roll #<br>/roll #d#<br><br>Send a Private Message:<br>@ExistingUsername<br><br>Muting Users (<font size='2'>You won't recieve messages from this user</font>):<br>/mute ExistingUsername<br><br>Unmute User (<font size='2'>You will recieve messages from this user again</font>):<br>/unmute ExistingUsername</html>";

        JLabel instructionsLabel = new JLabel(instructionsText);
        container.add(instructionsLabel);

        JButton back = new JButton("Go Back");
        back.addActionListener((event) -> {
            controls.show("CHAT");
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(back, BorderLayout.SOUTH);

        add(instructionsLabel, BorderLayout.NORTH); 
        add(back, BorderLayout.SOUTH);

        this.setName(Card.TIPS.name());
        controls.addPanel(Card.TIPS.name(), this);
        
    }

}