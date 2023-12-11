package CR.client;

import javax.swing.JPanel;

public interface ICardControls {
    void next();
    void previous();
    void show(String cardName);
    void addPanel(String name, JPanel panel);
    void connect();

    //yc73
    //11/27/23
    void onExport();

    //yc73
    //12/8/23
    void sendFlip();

    //yc73
    //12/8/23
    void appendRoll(String message);

    //yc73
    //12/8/23
    void appendMute(String message);


    //yc73
    //12/8/23
    void appendUnmute(String message);


    //yc73
    //12/8/23
    void appendPM(String message);

}