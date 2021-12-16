package Shell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    static final int WINDOW_POS_X=500;
    static final int WINDOW_POS_Y=300;
    static final int WINDOW_WITH =500;
    static final int WINDOW_HEIGHT=555;
    private SettingWindow settingWindow;
    private GameMap gameMap;
    private ResultWindow resultWindow;


    public GameWindow(){
        setBounds(WINDOW_POS_X,WINDOW_POS_Y,WINDOW_WITH,WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Крестики нолики");


         settingWindow=new SettingWindow(this);
         gameMap =new GameMap(this);
         add(gameMap,BorderLayout.CENTER);

        JPanel buttonField = new JPanel(new GridLayout(1,2));
        JButton btnNewGame = new JButton("Начать новую игру.");
        JButton btnExit = new JButton("Выход.");
        buttonField.add(btnNewGame);
        buttonField.add(btnExit);
        add(buttonField,BorderLayout.SOUTH);

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingWindow.setVisible(true);

            }
        });



        setVisible(true);

    }


    void startNewGame(int size,int winStreak){
        gameMap.initNewGame(size,winStreak);

    }
}
