import java.util.InputMismatchException;
import java.util.Scanner;

public class GameMenu {
    static int players_count = 0, cards_count = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try{
            System.out.print("Введите кол-во игроков: ");
            players_count = scanner.nextInt(5);
            if (players_count <= 1) {
                throw new InputMismatchException();
            }

            System.out.println("Выберите кол-во карт для игры. \n1) 36 \n2) 52");
            System.out.print("Ввод: ");
            cards_count = scanner.nextInt(3);
            if (cards_count < 1){
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e){
            System.out.println("Вы ввели не число, или число слишком маленькое/большое.");
        }

        System.out.println("Если хотите начать игру - введите 'S',\nЕсли хотите выйти - введите 'E'");
        String user_input = scanner.nextLine();

        game_start();
    }

    static void game_start(){
        String user_input = scanner.nextLine();
        if (user_input.equals("S")) {
            System.out.println("Начинаем игру...");
            new Game(players_count, cards_count);
        } else if (user_input.equals("E")){
            System.out.println("Выход....");
        } else{
            System.out.println("Введите 'S' для старта или 'E' для выхода.");
            game_start();
        }
    }
}