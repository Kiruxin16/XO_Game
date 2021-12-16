package Logic;

import java.util.Random;
import java.util.Scanner;

public class GameMeth {
    public static  int sizeM =3;
    public static  int winStreakM =3;
    public static final char DOT_X='X';
    public static final char DOT_O ='O';
    public static final char DOT_EMPTY='.';
    public static boolean isGameFinished;
    public static Scanner scan = new Scanner(System.in);
    public static Random rand = new Random();
    public static int logRow;               //Запоминаем, куда игрок поставил последнюю фишку.
    public static int logColumn;
    public static String result;
    public static MarkCoords start =new MarkCoords();
    public static MarkCoords finish =new MarkCoords();



    public static char[][] map;

    public static void initialMap(){
        map=new char[sizeM][sizeM];
        for (int i = 0; i< sizeM; i++) {
            for (int j = 0; j< sizeM; j++) {
                map[i][j]=DOT_EMPTY;
            }

        }
    }

    public static void printMap(){
        System.out.print("  ");
        for (int i = 1; i <= sizeM; i++) {
            System.out.print(i+" ");
        }
        System.out.println();

        for (int i = 0; i< sizeM; i++) {
            System.out.print(i+1+" ");
            for (int j = 0; j< sizeM; j++) {
                System.out.printf("%c ",map[i][j]);
            }
            System.out.println();
        }
    }

    public static void userTurn(int column, int row){
        if (isCellValid(row, column)) {
            map[row][column] = DOT_X;
            logRow = row;
            logColumn = column;
            go();
        }

    }

    public static boolean isCellValid(int row,int column){
        if (row <0 || column<0 || row>= sizeM || column>= sizeM){
            return false;
        }
        return map[row][column]==DOT_EMPTY;
    }

   public static boolean isThatWin(char dot) {
       int column = 0, row = 0, diagStr = 0, diagRev = 0; //счетчики полос совпадения
       MarkCoords tempStart1=new MarkCoords();
       MarkCoords tempStart2=new MarkCoords();


       //проверка горизонталей и вертикалей
       for (int i = 0; i < sizeM; i++) {
           for (int j = 0; j < sizeM; j++) {
               if (map[i][j] == dot) {
                   if (row ==0){
                       tempStart1.setCoords(j,i);
                   }
                   row++;
                   if (row == winStreakM) {
                       start=tempStart1;
                       finish.setCoords(j,i);
                       return true;
                   }
               } else {
                   row = 0;
               }
               if (map[j][i] == dot) {
                   if (column ==0){
                       tempStart2.setCoords(i,j);
                   }
                   column++;
                   if (column == winStreakM) {
                       start=tempStart2;
                       finish.setCoords(i,j);
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
       for (int i = 0; i < sizeM - winStreakM +1; i++) {
           for (int j = 0; j < sizeM - winStreakM +1; j++) {
               if(map[i][j]==dot||map[sizeM -i-1][j]==dot) {
                   for (int k = 0; k < winStreakM; k++) {
                       if (map[i + k][j + k] == dot) {
                           if (diagStr==0){
                               tempStart1.setCoords(j,i);
                           }
                           diagStr++;
                       }
                       if (map[sizeM - (i + k+1)][j + k] == dot) {
                           if(diagRev==0){
                               tempStart2.setCoords(j,sizeM-i-1);
                           }
                           diagRev++;
                       }
                   }
                   if (diagStr == winStreakM) {
                       start=tempStart1;
                       finish.setCoords(j+winStreakM,i+winStreakM);
                       return true;
                   }
                   if (diagRev==winStreakM){
                       start=tempStart2;
                       finish.setCoords(j+winStreakM,sizeM-i-1-winStreakM);
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
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeM; j++) {
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
                if (logRow + i >= 0 && logColumn + j >= 0 && logRow + i < sizeM && logColumn + j < sizeM) {//если находим второй крест
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
                if (logRow + 2 * i >= 0 && logColumn + 2 * j >= 0 && logRow + 2 * i < sizeM && logColumn + 2 * j < sizeM) {
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
            row= rand.nextInt(sizeM);
            column=rand.nextInt(sizeM);
            if(isCellValid(row,column)){
                map[row][column]= DOT_O;
                return;
            }
        }
    }

    public static void go(){
        isGameFinished=true;
        printMap();
        if (isThatWin(DOT_X)) {
            result="Поздравляем! Вы выйграли!";
            return;
        }
        if (isDraw()) {
            result="Ничья";
            return;
        }

        aiTurn();
        printMap();
        if (isThatWin(DOT_O)) {
            result ="Выйграл компьютер";
            return;
        }
        if (isDraw()) {
            result="Ничья";
            return;
        }


        isGameFinished=false;


    }





}


