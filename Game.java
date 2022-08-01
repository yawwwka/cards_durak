import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private int players_count, cards_count;
    private boolean gameFlag = true;
    private String koz;

    ArrayList<String> p1 = new ArrayList<>();
    ArrayList<String> p2 = new ArrayList<>();

    ArrayList<ArrayList> players = new ArrayList<>();

    ArrayList<String> cards = new ArrayList<>();

    String[] cards36 = {
            "6C", "6B", "6P", "6K",
            "7C", "7B", "7P", "7K",
            "8C", "8B", "8P", "8K",
            "9C", "9B", "9P", "9K",
            "10C", "10B", "10P", "10K",
            "11C", "11B", "11P", "11K",
            "12C", "12B", "12P", "12K",
            "13C", "13B", "13P", "13K",
            "14C", "14B", "14P", "14K"
    };

    String[] cards52 = {
            "2C", "2B", "2P", "2K",
            "3C", "3B", "3P", "3K",
            "4C", "4B", "4P", "4K",
            "5C", "5B", "5P", "5K",
            "6C", "6B", "6P", "6K",
            "7C", "7B", "7P", "7K",
            "8C", "8B", "8P", "8K",
            "9C", "9B", "9P", "9K",
            "10C", "10B", "10P", "10K",
            "11C", "11B", "11P", "11K",
            "12C", "12B", "12P", "12K",
            "13C", "13B", "13P", "13K",
            "14C", "14B", "14P", "14K"
    };

    private Scanner scanner = new Scanner(System.in);

    Game(int players_count, int cards_count){
        this.players_count = players_count;
        this.cards_count = cards_count;

        giveawayCards();
        game();
    }

    void giveawayCards(){
        if (cards_count == 1){
            cards.addAll(Arrays.asList(cards36));
        } else{
            cards.addAll(Arrays.asList(cards52));
        }

        Random random = new Random();

        int card_ind;
        while (p2.size() != 6){
            card_ind = random.nextInt(cards.size());
            p1.add(cards.get(card_ind));
            cards.remove(card_ind);
            card_ind = random.nextInt(cards.size());
            p2.add(cards.get(card_ind));
            cards.remove(card_ind);
        }

        players.add(p1);
        players.add(p2);

        // System.out.println(p1 + "\n" + p2 + "\n" + cards.size());

        koz = cards.get(random.nextInt(cards.size()));
        StringBuilder string = new StringBuilder(koz);
        string.deleteCharAt(0);
        koz = string.toString();
    }

    void game(){
        ArrayList<String> moves = new ArrayList<>();
        int player_num = 1, player_num1, attack_index;
        boolean wait_for_move = true, moveFl = false;
        String move, attack_move;

        while (gameFlag){
            System.out.println("-------------------------------------------------------------------------");

            if (player_num > players_count){
                player_num = 1;
            }

            System.out.printf("Ход игрока номер %d, козырь - %s. \n", player_num, koz);
            if (player_num == 1){
                System.out.printf("Карты на руке: %s \n", p1.toString());
                player_num1 = 2;
            } else{
                System.out.printf("Карты на руке: %s \n", p2.toString());
                player_num1 = 1;
            }

            wait_for_move = true;
            while (wait_for_move){
                System.out.print("Введите карту которой ходите начать ход: ");
                move = scanner.nextLine();
                if (players.get(player_num - 1).contains(move)){
                    players.get(player_num - 1).remove(move);
                    moves.add(move);
                    moveFl = true;

                    while (moveFl){
                        System.out.printf("Отбивается игрок номер %d, " +
                                "\nкарты на руках - %s \n", player_num1, players.get(player_num1 - 1));

                        System.out.println("Введите 'Забираю' или карту, которой хотите побить: ");
                        move = scanner.nextLine();

                        if (players.get(player_num1 - 1).contains(move)){
                            players.get(player_num1 - 1).remove(move);
                            moves.add(move);

                            attack_index = moves.indexOf(move) - 1;
                            attack_move = moves.get(attack_index);

                            StringBuilder attack_prefix = new StringBuilder(attack_move);
                            StringBuilder attack_suffix = new StringBuilder(attack_move);
                            StringBuilder defence_prefix = new StringBuilder(move);
                            StringBuilder defence_suffix = new StringBuilder(move);

                            if (move.length() == 3) {
                                defence_prefix.deleteCharAt(2);
                                defence_suffix.deleteCharAt(0);
                                defence_suffix.deleteCharAt(0);
                            } else {
                                defence_prefix.deleteCharAt(1);
                                defence_suffix.deleteCharAt(0);
                            }

                            if (attack_move.length() == 3) {
                                attack_prefix.deleteCharAt(2);
                                attack_suffix.deleteCharAt(0);
                                attack_suffix.deleteCharAt(0);
                            } else {
                                attack_prefix.deleteCharAt(1);
                                attack_suffix.deleteCharAt(0);
                            }

                            if (defence_suffix.toString().equals(attack_suffix.toString()) &&
                                    Integer.parseInt(defence_prefix.toString()) >
                                            Integer.parseInt(attack_prefix.toString())){
                                System.out.printf("Игрок номер %d, закончить ход или подкинуть?" +
                                        "\nЧтобы закончить введите 'Бито', чтобы подкинуть введите карту.\n" +
                                                "Ваши карты: %s\n",
                                        player_num, players.get(player_num - 1));
                                System.out.print("Ваш ввод: ");
                                move = scanner.nextLine();
                                if (move.equals("Бито")){
                                    moveFl = false;
                                } else if (players.get(player_num - 1).contains(move)){
                                    StringBuilder podkid = new StringBuilder(move);
                                    if (podkid.length() == 3){
                                        podkid.deleteCharAt(2);
                                    } else{
                                        podkid.deleteCharAt(1);
                                    }

                                    if (podkid.toString().equals(defence_prefix.toString()) ||
                                            podkid.toString().equals(attack_prefix.toString())){
                                        players.get(player_num - 1).remove(move);
                                        moves.add(move);
                                    } else {
                                        System.out.println("Вы не можете подкинуть эту карту.");
                                        moveFl = false;
                                    }


                                } else {
                                    System.out.println("У вас нет такой карты.");
                                    moveFl = false;
                                }
                            } else {
                                System.out.printf("%s не бьёт %s \n", move, attack_move);
                                players.get(player_num1 - 1).add(move);
                            }

                        } else if (move.equals("Забираю")){
                            player_num--;
                            players.get(player_num1 - 1).addAll(moves);
                            moveFl = false;
                        }
                    }
                    moves.clear();
                    wait_for_move = false;
                }
            }

            if (players.get(0).isEmpty()){
                System.out.println("Победил игрок номер 1");
                gameFlag = false;
            } else if (players.get(1).isEmpty()){
                System.out.println("Победил игрок номер 2");
                gameFlag = false;
            }

            player_num++;
        }
    }
}