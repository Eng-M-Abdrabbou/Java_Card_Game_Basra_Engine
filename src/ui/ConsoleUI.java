/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;


import engine.Game;
import engine.PlayerAction;
import model.Card;
import model.Player;
import model.Table;

/**
 *
 * @author Hp
 */

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayTable(Table table) {
        System.out.println("----------------------------------------");
        System.out.println("Floor: " + table.getCards());
        System.out.println("----------------------------------------");
    }

    public void displayPlayerHand(Player player) {
        System.out.println(player.getName() + "'s Hand: " + player.getHand().getCards());
    }

    public void displayGameState(Game game) {
        System.out.println("\n=========== Turn: " + game.getCurrentPlayer().getName() + " ===========");
        displayTable(game.getTable());
        System.out.println("Deck Cards Remaining: " + game.getDeckSize());
        for(Player p : game.getPlayers()){
             System.out.println(p.getName() + ": " + p.getCapturePileSize() + " captured cards, " + p.getBasraCount() + " Basras.");
        }
        displayPlayerHand(game.getCurrentPlayer());
    }

    public PlayerAction getPlayerAction(Player player) {
        List<Card> hand = player.getHand().getCards();
        if (hand.isEmpty()) {
             System.out.println(player.getName() + " has no cards to play!");
             // This case should ideally be handled before calling getPlayerAction in Game loop
             return null; // Or throw exception
        }

        while (true) {
            System.out.print(player.getName() + ", choose card to play (e.g., KH, 7D, T S): ");
            String input = scanner.nextLine().trim().toUpperCase();

            Card chosenCard = parseCardInput(input, hand);

            if (chosenCard != null) {
                // Validate if the chosen card is actually in the player's hand
                if (hand.contains(chosenCard)) {
                    return new PlayerAction(chosenCard);
                } else {
                    System.out.println("Error: Card '" + input + "' not found in your hand. Try again.");
                    displayPlayerHand(player); // Show hand again
                }
            } else {
                 System.out.println("Invalid input format. Use RankSuit (e.g., AH, 2C, TS, JD, QC, KD). Try again.");
            }
        }
    }

    // Helper to parse input like "KH", "7D" into a Card object
    private Card parseCardInput(String input, List<Card> availableCards) {
        // This is a simplified parser. A robust one would handle more cases.
        for(Card card : availableCards) {
            if(card.toString().equalsIgnoreCase(input)) {
                return card;
            }
        }
         // Fallback or more complex parsing if needed (e.g., handling spaces like "10 S")
        // For simplicity, we'll rely on the exact toString() match for now.
        return null; // Indicate parsing failure
    }

     public void close() {
        scanner.close();
    }
}
