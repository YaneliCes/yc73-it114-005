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
        //11/28/23
        //recieved help from the professor during office hours
        JMenuItem onExport = new JMenuItem("Export");
        onExport.addActionListener((event) -> {
            controls.onExport();
        });
        roomsMenu.add(onExport);

        this.add(roomsMenu);

    }
}