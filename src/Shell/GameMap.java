package Shell;

import Logic.GameMeth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMap extends JPanel {
    private GameWindow gameWindow;
    private int size;
    private int winStreak;


    private int cellHeight;
    private int cellWight;

    private boolean isInit;

    public GameMap(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setBackground(Color.ORANGE);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellX = e.getX()/cellWight;
                int cellY = e.getY()/cellHeight;
                if (!GameMeth.isGameFinished) {
                    GameMeth.userTurn(cellX, cellY);
                }
            }
        });
    }

    void initNewGame(int size,int winStreak){
        this.size=size;
        this.winStreak=winStreak;

        isInit =true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (!isInit){
            return;
        }

        cellHeight=getHeight()/size;
        cellWight=getWidth()/size;

        g.setColor(Color.BLACK);
        ((Graphics2D)g).setStroke(new BasicStroke(3f));


        for (int i = 1; i <size ; i++) {
            int y =i*cellHeight;
            int x =i*cellWight;
            g.drawLine(0,y,getWidth(),y);
            g.drawLine(x,0,x,getHeight());
        }
        for (int i = 0; i <GameMeth.sizeM ; i++) {
            for (int j = 0; j <GameMeth.sizeM ; j++) {
                if(GameMeth.map[i][j]==GameMeth.DOT_X){
                    drawX(g,i,j);
                }

            }

        }
        repaint();

       /* g.setColor(Color.BLUE);
        ((Graphics2D)g).setStroke(new BasicStroke(5f));
        g.drawLine(100,200,300,200);
*/

    }

    private void drawX(Graphics g, int cellY,int cellX){
        g.setColor(Color.RED);
        ((Graphics2D)g).setStroke(new BasicStroke(6f));

        g.drawLine((cellX * cellWight)+15, (cellY*cellHeight)+15,
                (cellX*cellWight+cellWight)-15,(cellY*cellHeight+cellHeight)-15);
        g.drawLine((cellX * cellWight)+15, (cellY*cellHeight+cellHeight)-15,
                (cellX*cellWight+cellWight)-15,(cellY*cellHeight)+15);

    }
}
