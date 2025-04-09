/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_card_game_basra_engine;

import engine.Game;
import java.util.*;
/**
 *
 * @author Hp
 */
public class BasraGameMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Welcome to Basra!");
        Scanner input = new Scanner(System.in);
            // TODO: Get player names from user input or command line args
      //  List<String> playerNames = List.of("Player 1", "Player 2");
        System.out.println("Please write the name of player 1 below:");
      String player1 = input.nextLine();
      
      System.out.println("Welcome to Basra "+player1+"! "
              + "\n Now Please write the name of player 1 below:");
      String player2 = input.nextLine();
       System.out.println("Welcome to Basra "+player2+"! \n now lets begin our game!");
       
        List<String> playerNames = new ArrayList<>(Arrays.asList(player1, player2));
        try {
            Game basraGame = new Game(playerNames);
            basraGame.startGameLoop(); // Start the game
        } catch (IllegalArgumentException e) {
            System.err.println("Error setting up game: " + e.getMessage());
        } catch (Exception e) {
             System.err.println("An unexpected error occurred: " + e.getMessage());
             e.printStackTrace(); // For debugging
        } finally {
             System.out.println("\nThank you for playing!");
        }
        
    }
    
}
