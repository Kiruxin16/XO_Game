package Shell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ResultWindow extends JFrame {

    static final int WINDOW_POS_X=GameWindow.WINDOW_POS_X+100;
    static final int WINDOW_POS_Y=GameWindow.WINDOW_POS_Y+200;
    static final int WINDOW_WITH =355;
    static final int WINDOW_HEIGHT=155;
    static Label endMess;




    public ResultWindow(GameMap gameMap) {
        setBounds(WINDOW_POS_X,WINDOW_POS_Y,WINDOW_WITH,WINDOW_HEIGHT);
        setTitle("Результат:");
        setUndecorated(true);
        setLayout(new BorderLayout());
        JPanel btnPanel =new JPanel();
        btnPanel.setLayout(new FlowLayout());
        add(btnPanel,BorderLayout.SOUTH);
        Button okBtn =new Button("ОК!");
        btnPanel.add(okBtn);
        endMess= new Label("");
        endMess.setFont(new Font("Dialog",Font.BOLD,20));
        add(endMess,BorderLayout.CENTER);


        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setVisible(false);
    }
}
