package CR.client.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import CR.client.Card;
import CR.client.Client;
import CR.client.ClientUtils;
import CR.client.ICardControls;

//yc73
//11/27/23
//import java.io.File;
import java.io.FileWriter;

public class ChatPanel extends JPanel {
    private static Logger logger = Logger.getLogger(ChatPanel.class.getName());
    private JPanel chatArea = null;
    private UserListPanel userListPanel;

    //yc73
    //11/27/23
    List<String> history = new ArrayList<String>(); 

    public ChatPanel(ICardControls controls){
        super(new BorderLayout(10, 10));
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        // wraps a viewport to provide scroll capabilities
        JScrollPane scroll = new JScrollPane(content);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        // no need to add content specifically because scroll wraps it
        wrapper.add(scroll);
        this.add(wrapper, BorderLayout.CENTER);

        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input, BoxLayout.X_AXIS));
        JTextField textValue = new JTextField();
        input.add(textValue);
        JButton button = new JButton("Send");
        // lets us submit with the enter key instead of just the button click
        textValue.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });
        button.addActionListener((event) -> {
            try {
                String text = textValue.getText().trim();
                if (text.length() > 0) {
                    Client.INSTANCE.sendMessage(text);
                    textValue.setText("");// clear the original text

                    // debugging
                    logger.log(Level.FINEST, "Content: " + content.getSize());
                    logger.log(Level.FINEST, "Parent: " + this.getSize());

                }
            } catch (NullPointerException e) {
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        chatArea = content;
        input.add(button);

        //yc73
        //11/27/23
        JButton saveButton = new JButton("Export Chat");
        saveButton.addActionListener((event) ->  {
            exportHistory();
        });
        //yc73
        //11/27/23
        input.add(saveButton);

        userListPanel = new UserListPanel(controls);
        this.add(userListPanel, BorderLayout.EAST);
        this.add(input, BorderLayout.SOUTH);
        this.setName(Card.CHAT.name());
        controls.addPanel(Card.CHAT.name(), this);
        chatArea.addContainerListener(new ContainerListener() {

            @Override
            public void componentAdded(ContainerEvent e) {
                if (chatArea.isVisible()) {
                    chatArea.revalidate();
                    chatArea.repaint();

                    //yc73
                    //12/4/23
                    //fixing scroll issue
                    //worked in class with toegel
                    try {
                        Thread.sleep(10);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    JScrollBar vertical = ((JScrollPane) chatArea.getParent().getParent()).getVerticalScrollBar();
                    vertical.setValue(vertical.getMaximum());

                }
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                if (chatArea.isVisible()) {
                    chatArea.revalidate();
                    chatArea.repaint();
                }
            }

        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // System.out.println("Resized to " + e.getComponent().getSize());
                // rough concepts for handling resize
                // set the dimensions based on the frame size
                Dimension frameSize = wrapper.getParent().getParent().getSize();
                int w = (int) Math.ceil(frameSize.getWidth() * .3f);
                
                userListPanel.setPreferredSize(new Dimension(w, (int)frameSize.getHeight()));
                userListPanel.revalidate();
                userListPanel.repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // System.out.println("Moved to " + e.getComponent().getLocation());
            }
        });
    }
    public void addUserListItem(long clientId, String clientName){
        userListPanel.addUserListItem(clientId, clientName);
    }
    public void removeUserListItem(long clientId){
        userListPanel.removeUserListItem(clientId);
    }
    public void clearUserList(){
        userListPanel.clearUserList();
    }
    public void addText(String text) {
        //yc73
        //11/27/23
        //recieved help from the professor during office hours
        history.add(text);

        JPanel content = chatArea;
        // add message

        //yc73
        //11/16/23
        //help from this website: https://www.javatpoint.com/java-jeditorpane
        //the "text/html" is what renders the HTML tags so they can be displayed correctly in the chat area
        JEditorPane textContainer = new JEditorPane("text/html", text);
        textContainer.setText("<html><body style='padding: 3; margin: 3; font-family: Arial;'>" + text + "</body></html>");

        // sizes the panel to attempt to take up the width of the container
        // and expand in height based on word wrapping
        textContainer.setLayout(null);
        textContainer.setPreferredSize(
                new Dimension(content.getWidth(), ClientUtils.calcHeightForText(this,text, content.getWidth())));
        textContainer.setMaximumSize(textContainer.getPreferredSize());
        textContainer.setEditable(false);
        ClientUtils.clearBackground(textContainer);
        // add to container and tell the layout to revalidate
        content.add(textContainer);
        // scroll down on new message
        JScrollBar vertical = ((JScrollPane) chatArea.getParent().getParent()).getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    public void exportHistory() {
                try {
                    String filename = JOptionPane.showInputDialog(new JFrame(), "Enter the file name that you want");
                    if (filename != null && !filename.isEmpty()) {
                        FileWriter fileWriter = new FileWriter(filename + ".txt");
                        for(String i:history) {
                            fileWriter.write("\n" + i);
                        }
                        fileWriter.close();
                    } 
                    
                    else {
                        //handles when client inputs an empty or null filename
                        JOptionPane.showMessageDialog(new JFrame(), "Invalid file name.");
                    }
                    
                } 
                
                catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame(), "Error occurred while saving chat history.");
                }

        }

    //yc73
    //12/5/23
    public void onMuted(String userMuteStatus, Long Id) {
        userListPanel.onMuted(userMuteStatus, Id);
    }

}