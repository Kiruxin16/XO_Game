import java.util.Random;
import java.util.Scanner;

public class GameMeth {
    public static final int SIZE=3;
    public static final int WIN_STREAK=3;
    public static final char DOT_X='X';
    public static final char DOT_O ='O';
    public static final char DOT_EMPTY='.';

    public static Scanner scan = new Scanner(System.in);
    public static Random rand = new Random();

    public static char[][] map;

    public static void initialMap(){
        map=new char[SIZE][SIZE];
        for (int i = 0; i<SIZE; i++) {
            for (int j = 0; j<SIZE; j++) {
                map[i][j]=DOT_EMPTY;
            }

        }
    }

    public static void printMap(){
        System.out.println("  1 2 3");
        for (int i = 0; i<SIZE; i++) {
            System.out.print(i+1+" ");
            for (int j = 0; j<SIZE; j++) {
                System.out.printf("%c ",map[i][j]);
            }
            System.out.println();
        }
    }

    public static void userTurn(){
        int row,column;
        do {
            System.out.println("Введите номер ряда и колонны.");
            row = scan.nextInt() - 1;
            column = scan.nextInt() - 1;
            if (!isCellValid(row,column)){
                System.out.println("Вы ввели коордиаты не верно. Попробуйте еще.");
            }
        }
        while (!isCellValid(row,column));
        map[row][column]=DOT_X;

    }

    public static boolean isCellValid(int row,int column){
        if (row <0 || column<0 || row>=SIZE || column>=SIZE ){
            return false;
        }
        return map[row][column]==DOT_EMPTY;
    }

    public static boolean isThatWin(char dot){
        if (map[0][0]==dot&&map[0][1]==dot&&map[0][2]==dot){return true;}
        if (map[1][0]==dot&&map[1][1]==dot&&map[1][2]==dot){return true;}
        if (map[2][0]==dot&&map[2][1]==dot&&map[2][2]==dot){return true;}
        if (map[0][0]==dot&&map[1][0]==dot&&map[2][0]==dot){return true;}
        if (map[0][1]==dot&&map[1][1]==dot&&map[2][1]==dot){return true;}
        if (map[0][2]==dot&&map[1][2]==dot&&map[2][2]==dot){return true;}
        if (map[0][0]==dot&&map[1][1]==dot&&map[2][2]==dot){return true;}
        if (map[0][2]==dot&&map[1][1]==dot&&map[2][0]==dot){return true;}
        return false;
    }

    public static boolean isDraw(){
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if(map[i][j]==DOT_EMPTY){
                    return false;
                }
            }

        }
        return true;
    }

    public static void aiTurn() {
        int row, column;
        while (true){
            row= rand.nextInt(SIZE);
            column=rand.nextInt(SIZE);
            if(isCellValid(row,column)){
                map[row][column]= DOT_O;
                return;
            }
        }
    }





}


