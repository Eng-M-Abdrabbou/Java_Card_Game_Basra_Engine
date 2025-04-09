/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;
import model.*;
/**
 *
 * @author Hp
 */
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ScoringLogic {

    // Define point values (adjust as needed per specific Basra rules)
    private static final int POINTS_MOST_CARDS = 3;
    private static final int POINTS_PER_BASRA = 10;
    private static final int POINTS_JACK = 1;
    private static final int POINTS_ACE = 1;
    private static final int POINTS_SEVEN_DIAMONDS = 1; // Kommy
    private static final int POINTS_TWO_CLUBS = 1;
    private static final int POINTS_TEN_DIAMONDS = 1;


    public void calculatePlayerScore(Player player) {
        int score = 0;
        List<Card> pile = player.getCapturePile();

        // 1. Score for Basras
        score += player.getBasraCount() * POINTS_PER_BASRA;

        // 2. Score for specific cards
        for (Card card : pile) {
            if (card.isJack()) score += POINTS_JACK;
            if (card.getRank() == Rank.ACE) score += POINTS_ACE;
            if (card.getRank() == Rank.TWO && card.getSuit() == Suit.CLUBS) score += POINTS_TWO_CLUBS;
            if (card.getRank() == Rank.TEN && card.getSuit() == Suit.DIAMONDS) score += POINTS_TEN_DIAMONDS;
            if (card.isSpecialSevenDiamonds()) score += POINTS_SEVEN_DIAMONDS;
             // Add other card points if rules require
        }

        player.setScore(score); // Set the base score excluding "most cards" bonus
    }

    // Call this *after* calculating individual base scores
    public void applyMostCardsBonus(List<Player> players) {
        if (players == null || players.size() < 2) return; // Need at least 2 players for comparison

        Player playerWithMostCards = null;
        int maxCards = -1;
        boolean tie = false;

        for (Player player : players) {
            int cardCount = player.getCapturePileSize();
            if (cardCount > maxCards) {
                maxCards = cardCount;
                playerWithMostCards = player;
                tie = false;
            } else if (cardCount == maxCards) {
                tie = true;
            }
        }

        // Award bonus only if there's no tie and someone captured cards
        if (!tie && playerWithMostCards != null && maxCards > 0) {
            playerWithMostCards.setScore(playerWithMostCards.getScore() + POINTS_MOST_CARDS);
            System.out.println(playerWithMostCards.getName() + " gets bonus for most cards (" + maxCards + ")");
        } else if (tie) {
             System.out.println("Tie for most cards! No bonus awarded.");
        }
    }
}
