import java.util.Random;
import java.util.Scanner;

public class GameMeth {
    public static final int SIZE=5;
    public static final int WIN_STREAK=4;
    public static final char DOT_X='X';
    public static final char DOT_O ='O';
    public static final char DOT_EMPTY='.';

    public static Scanner scan = new Scanner(System.in);
    public static Random rand = new Random();
    public static int logRow;               //Запоминаем, куда игрок поставил последнюю фишку.
    public static int logColumn;

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
        System.out.print("  ");
        for (int i = 1; i <=SIZE ; i++) {
            System.out.print(i+" ");
        }
        System.out.println();

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
        logRow=row;
        logColumn=column;

    }

    public static boolean isCellValid(int row,int column){
        if (row <0 || column<0 || row>=SIZE || column>=SIZE ){
            return false;
        }
        return map[row][column]==DOT_EMPTY;
    }

   public static boolean isThatWin(char dot) {
       int column = 0, row = 0, diagStr = 0, diagRev = 0; //счетчики полос совпадения

       //проверка горизонталей и вертикалей
       for (int i = 0; i < SIZE; i++) {
           for (int j = 0; j < SIZE; j++) {
               if (map[i][j] == dot) {
                   row++;
                   if (row == WIN_STREAK) {
                       return true;
                   }
               } else {
                   row = 0;
               }
               if (map[j][i] == dot) {
                   column++;
                   if (column == WIN_STREAK) {
                       return true;
                   }
               } else {
                   column = 0;
               }
           }
           column = 0;
           row = 0;
       }
       //проверка диагоналей
       //идея - пройтись по области клеток от которых можно выстроить победный ряд
       //от каждой клетки попытаться его проверить
       //проверяем параллельно с левого и правого верхних углов.
       for (int i = 0; i <SIZE-WIN_STREAK+1; i++) {
           for (int j = 0; j <SIZE-WIN_STREAK+1; j++) {
               if(map[i][j]==dot||map[SIZE-i-1][j]==dot) {
                   for (int k = 0; k < WIN_STREAK; k++) {
                       if (map[i + k][j + k] == dot) {
                           diagStr++;
                       }
                       if (map[SIZE - (i + k+1)][j + k] == dot) {
                           diagRev++;
                       }
                   }
                   if (diagStr == WIN_STREAK || diagRev == WIN_STREAK) {
                       return true;
                   }
                   diagStr=0;
                   diagRev=0;
               }
           }
       }
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

        //Логика следущая: проверяем от места, куда последний раз сходил игрок
        int row, column;
        for(int i=-1;i<=1;i++){                //двойным циклом мы формируем таблицу векторов
            for(int j=-1;j<=1;j++) {            //с координатами направления i j и по ней ищем есть ли вокруг еще крестики
                if (i == 0 && j == 0) {               //нулевой вектор нас не интересует
                    continue;
                }
                if (logRow + i >= 0 && logColumn + j >= 0 && logRow + i < SIZE && logColumn + j < SIZE) {//если находим второй крест
                    if (map[logRow + i][logColumn + j] == DOT_X) {                                       //проверяем можно ли поставить 0
                        if (isCellValid(logRow - i, logColumn - j)) {                        //рядом со стороны точки отсчета
                            map[logRow - i][logColumn - j] = DOT_O;                 //если да , ставим
                            return;
                        }
                        if (isCellValid(logRow + 2 * i, logColumn + 2 * j)) {            // пробуем поставить 0 в линию со стороны
                            map[logRow + 2 * i][logColumn + 2 * j] = DOT_O;                          // найденного крестика
                            return;
                        }
                    }
                }
                //хитрый пользователь может поставить крестики через одну клетку. Если не заблокировать, он соединит их
                //получив выйгрышную комбинацию
                if (logRow + 2 * i >= 0 && logColumn + 2 * j >= 0 && logRow + 2 * i < SIZE && logColumn + 2 * j < SIZE) {
                    if (map[logRow + 2 * i][logColumn + 2 * j] == DOT_X) {
                        if (isCellValid(logRow + i, logColumn + j)) {
                            map[logRow + i][logColumn + j] = DOT_O;  //если можно, ставим в линию между крестиками пользователя
                            return;

                        }
                    }
                }
            }
        }
        //Психанули. Снова ставим наугад!
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


