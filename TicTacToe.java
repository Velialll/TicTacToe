package ua.jhogan.TicTacToe;

import java.util.*;

/**
 * Клас, що реалізує гру "Хрестики-нулики" (Tic-Tac-Toe).
 */
public class TicTacToe {

    // Константи, що визначають символи гравця і комп'ютера на ігровому полі.
    public static final char PLAYER_SYMBOL = 'X';
    public static final char CPU_SYMBOL = 'O';
    // Списки, що зберігають позиції гравця і комп'ютера на ігровому полі.
    static ArrayList<Integer> playerPositions = new ArrayList<>();
    static ArrayList<Integer> cpuPositions = new ArrayList<>();

    /**
     * Головний метод гри. Керує ходами гравця і комп'ютера, виведенням гри на консоль та визначенням результату гри.
     */
    public static void main(String[] args) {

        while (true) {
            //Створили ігрову дошку та вивели її
            char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
                    {'-', '+', '-', '+', '-'},
                    {' ', '|', ' ', '|', ' '},
                    {'-', '+', '-', '+', '-'},
                    {' ', '|', ' ', '|', ' '}};


            printGameBoard(gameBoard);


            while (true) {
                // Створили об'єкт Scanner для зчитування введення з клавіатури.
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter your placement (1-9):");
                int playerPos = scanner.nextInt();

                // Перевірка, чи введена позиція вже зайнята гравцем або комп'ютером.
                while (playerPositions.contains(playerPos) || cpuPositions.contains(playerPos)) {
                    System.out.println("Position taken! Enter a correct Position");
                    playerPos = scanner.nextInt();
                }
                // Розміщення фішки гравця на ігровому полі.
                placePiece(gameBoard, playerPos, "player");
                String result = checkWinner();

                // Перевірка результату гри. Якщо гра закінчилася, виводиться результат та гра завершується.
                if (result.length() > 0) {
                    System.out.println(result);
                    break;
                }

                // Генерування ходу комп'ютера та розміщення фішки.
                Random random = new Random();
                int cpuPos = random.nextInt(9) + 1;
                while (playerPositions.contains(cpuPos) || cpuPositions.contains(cpuPos)) {
                    cpuPos = random.nextInt(9) + 1;
                }
                placePiece(gameBoard, cpuPos, "cpu");

                // Виведення оновленого стану гри на консоль та перевірка результату.
                printGameBoard(gameBoard);
                result = checkWinner();
                if (result.length() > 0) {
                    System.out.println(result);
                    break;
                }
            }
            // Питаємо гравця, чи він хоче продовжити гру.
            Scanner scannerReplay = new Scanner(System.in);
            System.out.println("Do you want to play again? (yes/no)");
            String replayChoice = scannerReplay.next();

            if ("yes".equalsIgnoreCase(replayChoice)) {
                // Очищення ігрового поля і списків позицій для нової гри
                for (int i = 0; i < gameBoard.length; i++) {
                    for (int j = 0; j < gameBoard[i].length; j++) {
                        gameBoard[i][j] = ' ';
                    }
                }
                playerPositions.clear();
                cpuPositions.clear();
            } else {
                break; // Вихід з головного циклу, якщо гравець вирішив не грати більше
            }
        }
    }

    /**
     * Виводить ігрове поле в текстовому вигляді на консоль.
     *
     * @param gameBoard Масив, що представляє ігрове поле.
     */
    public static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char r : row) {
                System.out.print(r);//Виводить символ на консоль (без переходу на новий рядок).
            }
            System.out.println();//Переходить на новий рядок для виводу нового ігрового ряду.
        }

    }

    /**
     * Розміщує символ гравця або комп'ютера на ігровому полі з вказаною позицією.
     *
     * @param gameBoard Масив, що представляє ігрове поле.
     * @param pos       Позиція, на якій потрібно розмістити фішку (від 1 до 9).
     * @param user      Рядок, що вказує, чи це рух гравця ("player") чи комп'ютера ("cpu").
     */
    public static void placePiece(char[][] gameBoard, int pos, String user) {

        char symbol = ' ';

        //Визначає символ гравця(x) або комп'ютера (0).
        if (user.equals("player")) {
            symbol = PLAYER_SYMBOL;
            playerPositions.add(pos);//Додаємо позицію гравця до списку
        } else if (user.equals("cpu")) {
            symbol = CPU_SYMBOL;
            cpuPositions.add(pos);//Додаємо позицію комп'ютера до списку
        }

        switch (pos) {
            case 1 -> gameBoard[0][0] = symbol;
            case 2 -> gameBoard[0][2] = symbol;
            case 3 -> gameBoard[0][4] = symbol;
            case 4 -> gameBoard[2][0] = symbol;
            case 5 -> gameBoard[2][2] = symbol;
            case 6 -> gameBoard[2][4] = symbol;
            case 7 -> gameBoard[4][0] = symbol;
            case 8 -> gameBoard[4][2] = symbol;
            case 9 -> gameBoard[4][4] = symbol;
            default -> {
            }
        }

    }

    /**
     * Перевіряє стан гри та визначає переможця.
     *
     * @return Рядок що містить результат гри: "Congratulation you won!", у випадку перемоги гравця,
     * та "CPU wins! Try Again" у випадку перемоги комп'ютера або "A draw!" у випадку нічиєї.
     */
    public static String checkWinner() {

        //Список комбінацій для перемоги у грі.
        List<Integer> topRow = Arrays.asList(1, 2, 3);
        List<Integer> midRow = Arrays.asList(4, 5, 6);
        List<Integer> botRow = Arrays.asList(7, 8, 9);

        List<Integer> leftColumn = Arrays.asList(1, 4, 7);
        List<Integer> midColumn = Arrays.asList(2, 5, 8);
        List<Integer> rightColumn = Arrays.asList(3, 6, 9);

        List<Integer> cross1 = Arrays.asList(1, 5, 9);
        List<Integer> cross2 = Arrays.asList(3, 5, 7);

        //Список що містить усі можливі комбінації для перемоги у грі.
        List<List<Integer>> winningConditions = new ArrayList<>();
        winningConditions.add(topRow);
        winningConditions.add(midRow);
        winningConditions.add(botRow);
        winningConditions.add(leftColumn);
        winningConditions.add(midColumn);
        winningConditions.add(rightColumn);
        winningConditions.add(cross1);
        winningConditions.add(cross2);

        //Перевіряємо кожну комбінацію для перемоги.
        for (List<Integer> winningCombination : winningConditions) {
            if (playerPositions.containsAll(winningCombination)) {
                return "Congratulation you won!";
            } else if (cpuPositions.containsAll(winningCombination)) {
                return "CPU wins! Try Again";
            }
            // Перевіряємо, чи гра завершилася в нічию.
            if (playerPositions.size() + cpuPositions.size() == 9) {
                return "A draw!";
            }
        }//Якщо жодна з комбінації не виконалась або гра не закінчилась нічиєю, повертаємо пустий рядок.
        return "";
    }

}
