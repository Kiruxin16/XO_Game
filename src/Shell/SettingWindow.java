package Shell;

import Logic.GameMeth;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingWindow extends JFrame {

    static final int WINDOW_POS_X=GameWindow.WINDOW_POS_X+50;
    static final int WINDOW_POS_Y=GameWindow.WINDOW_POS_Y+50;
    static final int WINDOW_WITH =400;
    static final int WINDOW_HEIGHT=300;

    private GameWindow gameWindow;
    private JButton btnNewGame;
    private JSlider slFieldSize;
    private JSlider slWinStreak;

    private static final int MIN_FIELD_SIZE =3;
    private static final int MAX_FIELD_SIZE =10;



    public SettingWindow(GameWindow gameWindow) {
        this.gameWindow =gameWindow;
        setBounds(WINDOW_POS_X,WINDOW_POS_Y,WINDOW_WITH,WINDOW_HEIGHT);
        setTitle("Tic");
        setLayout(new GridLayout(5,1));
        add(new Label("Размер:"));
        slFieldSize=new JSlider(MIN_FIELD_SIZE,MAX_FIELD_SIZE,MIN_FIELD_SIZE);

        slFieldSize.setMajorTickSpacing(1);
        slFieldSize.setPaintLabels(true);
        slFieldSize.setPaintTicks(true);
        add(slFieldSize);


        add(new Label("Полоса выйгрыша:"));
        slWinStreak=new JSlider(MIN_FIELD_SIZE,MIN_FIELD_SIZE,MIN_FIELD_SIZE);
        slWinStreak.setMajorTickSpacing(1);
        slWinStreak.setPaintLabels(true);
        slWinStreak.setPaintTicks(true);
        add(slWinStreak);


        slFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                slWinStreak.setMaximum(slFieldSize.getValue());
            }
        });

        btnNewGame = new JButton("Начать!");
        add(btnNewGame);

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size =slFieldSize.getValue();
                int winStreak = slWinStreak.getValue();

                GameMeth.sizeM=size;
                GameMeth.winStreakM=winStreak;
                GameMeth.initialMap();
                GameMeth.isGameFinished=false;

                gameWindow.startNewGame(size,winStreak);
                setVisible(false);
            }
        });

        setVisible(false);
    }
}
