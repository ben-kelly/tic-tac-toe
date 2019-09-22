import java.util.Scanner;

/**
 * Created by bkelly on 2019/06/05.
 */
public class TicTacToe {
    static Scanner in = new Scanner(System.in);
    static int player1Wins = 0; //The number of games Player 1 has won
    static int player2Wins = 0; //The number of games Player 2 has won

    public static void main(String[] args) {
        printInstructions();
        run(false, true);
    }
    
    //This method runs a game of Tic-Tac-Toe.
    public static void run(boolean multipleGames, boolean player1Turn) {
        //Create and print a blank grid
        char[][] grid = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        printGrid(grid);

        int move; //Number of moves taken in the game
        for (move = 0; move < 9; ++move) {
            char player; //Letter that represents each player
            if (player1Turn) {
                player = 'X';
                System.out.println("It is Player 1's turn.");
            } else {
                player = 'O';
                System.out.println("It is Player 2's turn.");
            }

            //Make a move until a valid move is made. Then, print the result.
            while (!makeMove(grid, player));
            printGrid(grid);

            //If a player has won, stop making moves. The game is over
            if (hasThreeInARow(grid, player)) {
                break;
            }

            //Change to the other player's turn
            player1Turn = !player1Turn;
        }

        /*
         * If the game ended...
         *  -after all possible moves have been made, then it is a tie.
         *  -during Player 1's turn, then Player 1 wins
         *  -during Player 2's turn, then Player 2 wins
         */
        if (move == 9) {
            System.out.println("It's a tie.");
        } else if (player1Turn) {
            System.out.println("Player 1 wins!");
            player1Wins++;
        } else {
            System.out.println("Player 2 wins!");
            player2Wins++;
        }

        //If multiple games are being played, display the total score
        if (multipleGames) {
            printScore();
        }

        //Ask the players if they want to play again
        printEndMessage(player1Turn);
    }

    //This method prints the instructions of the game.
    public static void printInstructions() {
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("Take turns entering letters corresponding to " +
                           "positions on the grid:");
        char[][] example = {{'q','w','e'},{'a','s','d'},{'z','x','c'}};
        printGrid(example);
        System.out.println("Player 1 is X, Player 2 is O.");
        System.out.println("Your goal is to get 3 of your own letter in a " +
                           "row vertically, \nhorizontally, or diagonally.");
        System.out.println("In each new game, the loser of the last game " +
                           "goes first.");
    }

    //This method asks players if they want to play another game.
    public static void printEndMessage(boolean player1Turn) {
        System.out.println("Play again?\t(Y/N)");
        char answer = Character.toLowerCase(in.next().charAt(0));

        //Check for valid response
        while (answer != 'y' && answer != 'n') {
            System.out.println("INVALID RESPONSE. Please enter Y or N.");
            answer = Character.toLowerCase(in.next().charAt(0));
        }

        //Start a new game or print goodbye message
        if (answer == 'y') {
            //Start a new game where the loser goes first.
            System.out.println("Starting new game.");
            run(true, !player1Turn);
        } else {
            printGoodbyeMessage();
        }
    }

    /*
     * This methods prints a congratulatory message to the winner and thanks
     * both players for playing.
     */
    public static void printGoodbyeMessage() {
        System.out.println();
        if (player1Wins != player2Wins) {
            if (player1Wins > player2Wins) {
                System.out.println("Congratulations to Player 1!");
            } else {
                System.out.println("Congratulations to Player 2!");
            }
        }
        System.out.println("Thank you for playing.");
    }

    //This method prints the number of games won by each player.
    public static void printScore() {
        //Check if more than 1 games have been won and adjust message
        if (player1Wins != 1) {
            System.out.println("Player 1 has won " + player1Wins + " games.");
        } else {
            System.out.println("Player 1 has won 1 game.");
        }

        if (player2Wins != 1) {
            System.out.println("Player 2 has won " + player2Wins + " games.");
        } else {
            System.out.println("Player 2 has won 1 game.");
        }
    }

    /*
     * This method takes input from a player and updates the grid accordingly. 
     * It returns true on a successful move and false on an invalid move.
     */
    public static boolean makeMove(char[][] grid, char player) {
        char move = Character.toLowerCase(in.next().charAt(0));
        int index;

        //Translate the player's input to a cell on the grid
        switch (move) {
            case 'q':
                index = 0;
                break;
            case 'w':
                index = 1;
                break;
            case 'e':
                index = 2;
                break;
            case 'a':
                index = 3;
                break;
            case 's':
                index = 4;
                break;
            case 'd':
                index = 5;
                break;
            case 'z':
                index = 6;
                break;
            case 'x':
                index = 7;
                break;
            case 'c':
                index = 8;
                break;
            default:
                //If invalid input, prompt the user for another input
                System.out.println("INVALID MOVE. Please enter a valid move.");
                return false;
        }

        //Update the grid if the cell is not occupied
        if (grid[index / 3][index % 3] == ' ') {
            grid[index / 3][index % 3] = player;
            return true;
        } else {
            //If the cell is already occupied, prompt the user for another
            //input
            System.out.println("INVALID MOVE. Please enter a valid move.");
            return false;
        }
    }

    //This method prints the grid to the console.
    public static void printGrid(char[][] grid) {
        System.out.println();
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid.length - 1; ++j) {
                System.out.print(grid[i][j] + "|");
            }
            System.out.println(grid[i][grid.length - 1]);
        }
        System.out.println();
    }

    /*
     * This method checks if there exists on the grid any sequence of 3 
     * identical characters in either a horizontal, vertical, or diagonal 
     * direction. It returns true if there is and false is there is not.
     */
    public static boolean hasThreeInARow(char[][] grid, char player) {
        //Check row 0 and col 0
        if (grid[0][0] == player) {
            if ((grid[0][1] == player && grid[0][2] == player) ||
                (grid[1][0] == player && grid[2][0] == player)) {
                return true;
            }
        }

        //Check row 1, col 1, and diagonals
        if (grid[1][1] == player) {
            if ((grid[1][0] == player && grid[1][2] == player) ||
                (grid[0][1] == player && grid[2][1] == player) ||
                (grid[0][0] == player && grid[2][2] == player) ||
                (grid[2][0] == player && grid[0][2] == player)) {
                return true;
            }
        }

        //Check row 2 and col 2
        if (grid[2][2] == player) {
            if ((grid[2][0] == player && grid[2][1] == player) ||
                (grid[0][2] == player && grid[1][2] == player)) {
                return true;
            }
        }

        return false;
    }
}
