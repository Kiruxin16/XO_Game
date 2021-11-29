public class Main {

    public static void main(String[] args) {
        GameMeth.initialMap();
        GameMeth.printMap();

        while (true)
        {
            GameMeth.userTurn();
            GameMeth.printMap();
            if (GameMeth.isThatWin(GameMeth.DOT_X)){
                System.out.println("Поздравляем! Вы выйграли!");
                break;
            }
            if (GameMeth.isDraw()){
                System.out.println("Ничья");
                break;
            }

            GameMeth.aiTurn();
            GameMeth.printMap();
            if (GameMeth.isThatWin(GameMeth.DOT_O)){
                System.out.println("Выйграл компьютер");
                break;
            }
            if (GameMeth.isDraw()){
                System.out.println("Ничья");
            }

        }
    }
}
