/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import model.*;
import rules.CaptureLogic;
import rules.ScoringLogic;
import ui.ConsoleUI; // Example UI dependency


/**
 *
 * @author Hp
 */


import java.util.ArrayList;
import java.util.List;

public class Game {

    private final List<Player> players;
    private final Deck deck;
    private final Table table;
    private final CaptureLogic captureLogic;
    private final ScoringLogic scoringLogic;
    private final ConsoleUI ui; // Inject or instantiate UI

    private int currentPlayerIndex;
    private Player lastCapturingPlayer;
    private boolean isGameOver;

    private static final int CARDS_PER_DEAL = 4;

    public Game(List<String> playerNames) {
        if (playerNames == null || playerNames.size() < 2) {
            throw new IllegalArgumentException("Basra requires at least 2 players.");
        }
        this.players = new ArrayList<>();
        for (String name : playerNames) {
            this.players.add(new Player(name));
        }
        this.deck = new Deck();
        this.table = new Table();
        this.captureLogic = new CaptureLogic();
        this.scoringLogic = new ScoringLogic();
        this.ui = new ConsoleUI(); // Replace with desired UI later
        this.currentPlayerIndex = 0; // Player 0 starts
        this.lastCapturingPlayer = null;
        this.isGameOver = false;
    }

    public void setupGame() {
        deck.shuffle();
        dealInitialFloor();
        dealHands();
        lastCapturingPlayer = null; // Reset for new game
        isGameOver = false;
    }

    private void dealInitialFloor() {
        table.clear();
        List<Card> floorCards = new ArrayList<>();
        boolean needsRedeal = true;

        while(needsRedeal) {
            floorCards.clear();
            // Ensure we don't run out of cards during setup
             if (deck.cardsRemaining() < CARDS_PER_DEAL) {
                 throw new IllegalStateException("Not enough cards in deck for initial floor setup.");
             }

            // Peek first, then deal if valid
            List<Card> potentialFloor = deck.peekTop(CARDS_PER_DEAL);
            boolean containsInvalid = potentialFloor.stream()
                                        .anyMatch(card -> card.isJack() || card.isSpecialSevenDiamonds());

            if (!containsInvalid) {
                needsRedeal = false;
                for (int i = 0; i < CARDS_PER_DEAL; i++) {
                     floorCards.add(deck.dealCard());
                }
            } else {
                // Invalid card found, reshuffle the deck and try again
                System.out.println("Initial floor contains Jack or 7D, reshuffling deck...");
                 deck.initializeDeck(); // Recreate full deck
                 deck.shuffle();
            }
        }
        table.addCards(floorCards);
        System.out.println("Initial Table Setup:");
        ui.displayTable(table); // Use UI to show table
    }


    private void dealHands() {
        if (deck.isEmpty()) return; // Cannot deal if deck is empty

        System.out.println("Dealing hands...");
        for (int i = 0; i < CARDS_PER_DEAL; i++) {
            for (Player player : players) {
                if (!deck.isEmpty()) { // Check again for each card
                    player.addCardToHand(deck.dealCard());
                } else {
                    break; // Stop dealing if deck runs out mid-deal
                }
            }
             if (deck.isEmpty()) break;
        }
    }

    public void startGameLoop() {
        setupGame();

        while (!isGameOver) {
            Player currentPlayer = players.get(currentPlayerIndex);

            // Check if hands are empty and deal new ones if possible
            if (currentPlayer.getHand().isEmpty()) {
                if (!deck.isEmpty()) {
                    dealHands();
                    // If after dealing, hand is still empty (deck ran out), game might end soon
                    if (currentPlayer.getHand().isEmpty()) {
                       checkGameOver(); // Check if game should end now
                       if(isGameOver) break; // Exit loop if game over
                    }
                } else {
                     checkGameOver(); // Deck is empty, hands might be empty -> check end condition
                     if(isGameOver) break; // Exit loop if game over
                }
            }

             // If player still has no cards after attempting deal (deck empty), skip turn or end game
            if (currentPlayer.getHand().isEmpty()) {
                System.out.println(currentPlayer.getName() + " has no cards and deck is empty. Skipping turn.");
                moveToNextPlayer();
                checkGameOver(); // Check again after potentially skipping all players
                 if(isGameOver) break;
                continue; // Skip to next player's turn
            }


            // ---- Player Turn ----
            ui.displayGameState(this); // Show current state via UI
            PlayerAction action = ui.getPlayerAction(currentPlayer); // Get validated input via UI

            processPlayerAction(currentPlayer, action);
            // ---- End Player Turn ----

            checkGameOver(); // Check if the game ended after this turn
            if (!isGameOver) {
                 moveToNextPlayer();
            }
        }

        endGame();
    }

    private void processPlayerAction(Player player, PlayerAction action) {
        Card playedCard = action.getCardToPlay();
        System.out.println(player.getName() + " plays: " + playedCard);

        // 1. Remove card from player's hand
        boolean removed = player.playCardFromHand(playedCard);
        if (!removed) {
             // This shouldn't happen if UI validates correctly, but good failsafe
             System.err.println("ERROR: Card " + playedCard + " not found in " + player.getName() + "'s hand!");
             // Maybe force player to choose again or handle error gracefully
             return;
        }

        // 2. Check for captures
        List<Card> capturableFloorCards = captureLogic.findCapturableCards(playedCard, table);

        if (!capturableFloorCards.isEmpty()) {
            // Capture occurred!
            boolean basra = captureLogic.isBasra(capturableFloorCards, table);

            // Create list of all cards captured (played card + floor cards)
            List<Card> allCapturedCards = new ArrayList<>(capturableFloorCards);
            allCapturedCards.add(playedCard); // Include the card played

            // Update table (remove captured floor cards)
            table.removeCards(capturableFloorCards);

            // Update player's capture pile
            player.addCardsToCapturePile(allCapturedCards);

            // Update last capturer
            lastCapturingPlayer = player;

            System.out.println(player.getName() + " captures: " + capturableFloorCards + " with " + playedCard);

            if (basra) {
                player.incrementBasraCount();
                System.out.println("***** BASRA! for " + player.getName() + " *****");
            }

        } else {
            // No capture, add card to table
            System.out.println(playedCard + " added to the floor.");
            table.addCard(playedCard);
        }

        ui.displayTable(table); // Show table after the play
    }

    private void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

     private void checkGameOver() {
        if (deck.isEmpty()) {
            // Deck is empty, check if all players' hands are also empty
            boolean allHandsEmpty = players.stream().allMatch(p -> p.getHand().isEmpty());
            if (allHandsEmpty) {
                isGameOver = true;
            }
        } else {
             isGameOver = false; // Game not over if deck still has cards
        }
    }


    private void endGame() {
        System.out.println("\n================ GAME OVER ================");

        // Award remaining floor cards to the last player who captured
        if (lastCapturingPlayer != null && !table.isEmpty()) {
            List<Card> remainingCards = table.getCards();
            System.out.println(lastCapturingPlayer.getName() + " (last capturer) takes remaining floor cards: " + remainingCards);
            lastCapturingPlayer.addCardsToCapturePile(remainingCards);
            table.clear();
        } else if (!table.isEmpty()){
            System.out.println("No captures made in the last round. Remaining floor cards are discarded: " + table.getCards());
            table.clear(); // Or handle according to specific rules if no one captured last round
        }

        // Calculate scores
        System.out.println("\n--- Calculating Scores ---");
        for (Player player : players) {
            scoringLogic.calculatePlayerScore(player); // Calculate base score
             System.out.println(player.getName() + " Pile: " + player.getCapturePileSize() + " cards, " + player.getBasraCount() + " Basras.");
        }

        // Apply bonus for most cards captured
        scoringLogic.applyMostCardsBonus(players);

        // Determine and announce winner
        Player winner = players.stream()
                            .max((p1, p2) -> Integer.compare(p1.getScore(), p2.getScore()))
                            .orElse(null); // Find player with highest score

        System.out.println("\n--- Final Scores ---");
        for (Player player : players) {
             System.out.println(player.getName() + ": " + player.getScore() + " points");
        }

        if (winner != null) {
             // Check for ties in score
             long winnersCount = players.stream().filter(p -> p.getScore() == winner.getScore()).count();
             if (winnersCount > 1) {
                 System.out.println("\nResult: It's a tie!");
             } else {
                 System.out.println("\nWinner: " + winner.getName() + " with " + winner.getScore() + " points!");
             }
        } else {
            System.out.println("\nNo winner could be determined.");
        }
    }

    // Getters for UI to display state
     public List<Player> getPlayers() {
        return new ArrayList<>(players); // Return copy
    }

    public Table getTable() {
        return table; // Often UI needs direct (but read-only) access or specific methods
    }

     public Player getCurrentPlayer() {
        if (!players.isEmpty()) {
            return players.get(currentPlayerIndex);
        }
        return null;
    }

     public int getDeckSize() {
         return deck.cardsRemaining();
     }
}
