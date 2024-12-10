/* Dan Kolodiychik
My Own Tic Tac Toe game
This program will make a Tic Tac Toe game for CISS100
 */

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CISS100TTT2 {
    private static char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };
    private static final char USER = 'X', COMPUTER = 'O';
    private static final int SPACES = 3;

    public static void main(String[] args) {
        while (true) {
            printBoard();
            userMove();
            if (isGameOver()) {
                break;
            }
            printBoard();
            computerMove();
            if (isGameOver()) {
                break;
            }
        }
    }

    public static void printBoard() {
        System.out.println("Current board:");
        for (int i = 0; i < SPACES; i++) {
            for (int j = 0; j < SPACES; j++) {
                System.out.print(board[i][j]);
                if (j < SPACES - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < SPACES - 1) {
                System.out.println("---------");
            }
        }
    }

    public static void userMove() {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        while (true) {
            System.out.println("Enter your move (row and column from 0 to 2): ");
            col = scanner.nextInt();
            row = scanner.nextInt();
            if (row >= 0 && row < SPACES && col >= 0 && col < SPACES && board[row][col] == ' ') {
                board[row][col] = USER;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    public static void computerMove() {
        int[] bestMove = minimax(board, COMPUTER);
        board[bestMove[0]][bestMove[1]] = COMPUTER;
        System.out.println("Computer's move: " + bestMove[0] + " " + bestMove[1]);
    }

    public static int[] minimax(char[][] board, char player) {
        int[] bestMove = new int[2];
        int bestValue = (player == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < SPACES; i++) {
            for (int j = 0; j < SPACES; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int moveValue = minimaxValue(board, player);
                    board[i][j] = ' ';

                    if ((player == COMPUTER && moveValue > bestValue) || (player == USER && moveValue < bestValue)) {
                        bestValue = moveValue;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    public static int minimaxValue(char[][] board, char player) {
        if (checkWin(COMPUTER)) {
            return 1;
        }
        if (checkWin(USER)) {
            return -1;
        }
        if (isBoardFull()) {
            return 0;
        }

        char opponent = (player == COMPUTER) ? USER : COMPUTER;
        int bestValue = (player == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < SPACES; i++) {
            for (int j = 0; j < SPACES; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int moveValue = minimaxValue(board, opponent);
                    board[i][j] = ' ';

                    if ((player == COMPUTER && moveValue > bestValue) || (player == USER && moveValue < bestValue)) {
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestValue;
    }

    public static boolean isBoardFull() {
        for (int i = 0; i < SPACES; i++) {
            for (int j = 0; j < SPACES; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkWin(char player) {

        for (int i = 0; i < SPACES; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }

        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }
        return false;
    }

    public static boolean isGameOver() {
        if (checkWin(USER)) {
            printBoard();
            System.out.println("You win!");
            return true;
        }
        if (checkWin(COMPUTER)) {
            printBoard();
            System.out.println("Computer wins!");
            return true;
        }
        if (isBoardFull()) {
            printBoard();
            System.out.println("It's a draw!");
            return true;
        }
        return false;
    }
}