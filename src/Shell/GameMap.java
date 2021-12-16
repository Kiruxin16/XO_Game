package Shell;

import Logic.GameMeth;
import Logic.MarkCoords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMap extends JPanel {
    private GameWindow gameWindow;
    private ResultWindow resultWindow;
    private int size;
    private int winStreak;


    private int cellHeight;
    private int cellWight;

    private boolean isInit;

    public GameMap(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setBackground(Color.ORANGE);
        resultWindow =new ResultWindow(this);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellX = e.getX()/cellWight;
                int cellY = e.getY()/cellHeight;
                if (!GameMeth.isGameFinished) {
                    GameMeth.userTurn(cellX, cellY);
                }
                if (GameMeth.isGameFinished){
                    ResultWindow.endMess.setText(GameMeth.result);
                    resultWindow.setVisible(true);
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
                if(GameMeth.map[i][j]==GameMeth.DOT_O){
                    drawO(g,i,j);
                }

            }

        }
        if (GameMeth.isGameFinished && !GameMeth.isDraw()){
            finishLine(g,GameMeth.start,GameMeth.finish);
        }

        repaint();

       /* g.setColor(Color.BLUE);
        ((Graphics2D)g).setStroke(new BasicStroke(5f));
        g.drawLine(100,200,300,200);
*/

    }

    private void drawX(Graphics g, int cellY,int cellX){
        g.setColor(Color.GREEN);
        ((Graphics2D)g).setStroke(new BasicStroke(6f));

        g.drawLine((cellX * cellWight)+15, (cellY*cellHeight)+15,
                (cellX*cellWight+cellWight)-15,(cellY*cellHeight+cellHeight)-15);
        g.drawLine((cellX * cellWight)+15, (cellY*cellHeight+cellHeight)-15, //обратная полоса
                (cellX*cellWight+cellWight)-15,(cellY*cellHeight)+15);

    }

    private void drawO(Graphics g, int cellY,int cellX){
        g.setColor(Color.BLUE);
        ((Graphics2D)g).setStroke(new BasicStroke(6f));

        g.drawOval(cellX*cellWight+15,cellY*cellHeight+10,cellWight-30,cellHeight-20);
    }

    private void finishLine (Graphics g,MarkCoords start,MarkCoords finish){
        g.setColor(Color.RED);
        ((Graphics2D)g).setStroke(new BasicStroke(4f));
        if(start.x==finish.x){
            g.drawLine(start.x*cellWight+cellWight/2,start.y*cellHeight,finish.x*cellWight+cellWight/2,finish.y*cellHeight);
        }else if(start.y==finish.y){
            g.drawLine(start.x*cellWight,start.y*cellHeight+cellHeight/2,finish.x*cellWight,finish.y*cellHeight+cellHeight/2);
        }else{
            g.drawLine(start.x*cellWight,start.y*cellHeight,finish.x*cellWight,finish.y*cellHeight);
        }


    }
}
