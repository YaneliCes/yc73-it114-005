package CR.client.views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import CR.client.Card;
import CR.client.ICardControls;

public class Menu extends JMenuBar{
    public Menu(ICardControls controls){
        JMenu roomsMenu = new JMenu("Rooms");
        JMenuItem roomsSearch = new JMenuItem("Search");
        roomsSearch.addActionListener((event) -> {
            controls.show(Card.ROOMS.name());
        });
        roomsMenu.add(roomsSearch);
        this.add(roomsMenu);

        //yc73
        //12/7/23
        JMenu tipsMenu = new JMenu("Tips");
        JMenuItem tipsCommand = new JMenuItem("Info: Commands");
        tipsCommand.addActionListener((event) -> {
            controls.show(Card.TIPS.name());
        });
        tipsMenu.add(tipsCommand);
        this.add(tipsMenu);

        //yc73
        //11/28/23
        //recieved help from the professor during office hours
        //updated: 12/7/23
        JMenu saveChatMenu = new JMenu("Save Chat");
        JMenuItem exportCommand = new JMenuItem("Export");
        exportCommand.addActionListener((event) -> {
            controls.onExport();
        });
        saveChatMenu.add(exportCommand);
        this.add(saveChatMenu);

        //yc73
        //12/8/23
        JMenu commandMenu = new JMenu("Commands");

        JMenuItem flipCommand = new JMenuItem("Flip Coin");
        flipCommand.addActionListener((event) -> {
            controls.sendFlip();
        });

        JMenuItem rollCommand = new JMenuItem("Roll Dice");
        rollCommand.addActionListener((event) -> {
            controls.appendRoll("/roll ");
        });

        JMenuItem muteCommand = new JMenuItem("Mute");
        muteCommand.addActionListener((event) -> {
            controls.appendMute("/mute ");
        });

        JMenuItem unmuteCommand = new JMenuItem("Unmute");
        unmuteCommand.addActionListener((event) -> {
            controls.appendUnmute("/unmute ");
        });

        JMenuItem pmCommand = new JMenuItem("PM");
        pmCommand.addActionListener((event) -> {
            controls.appendPM("@");
        });

        commandMenu.add(flipCommand);
        commandMenu.add(rollCommand);
        commandMenu.add(muteCommand);
        commandMenu.add(unmuteCommand);
        commandMenu.add(pmCommand);
        this.add(commandMenu);

    }
}