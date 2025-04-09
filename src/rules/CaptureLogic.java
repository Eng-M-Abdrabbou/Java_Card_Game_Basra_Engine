/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Hp
 */
public class CaptureLogic {

    
    //Determines which cards (if any) from the table can be captured by the played card.
    //Returns a list of cards from the table to be captured. 
    //Returns empty list if no capture.
     
    public List<Card> findCapturableCards(Card playedCard, Table table) {
        List<Card> floorCards = table.getCards();
        List<Card> captured = new ArrayList<>();

        // Rule 1: Jack captures all (or 7♦ acting as Jack)
        if (playedCard.isJack() || playedCard.isSpecialSevenDiamonds()) {
            if (!floorCards.isEmpty()) {
                return new ArrayList<>(floorCards);
            } else {
                return captured;
            }
        }

        // Rule 2: Match by Rank
        List<Card> matchingRankCards = floorCards.stream()
                .filter(floorCard -> floorCard.getRank() == playedCard.getRank())
                .collect(Collectors.toList());

        if (!matchingRankCards.isEmpty()) {
            // Capture all cards on the floor with the same rank as the played card
            captured.addAll(matchingRankCards);
            return captured;
        }

        // Rule 3: No match found (add other rules like sum capture here if needed)
        return captured; // Return empty list
    }

    
     // Checks if a capture action results in a Basra.
     // Assumes captureCards includes *only* cards from the floor (doesn't include the played card itself yet).
     
    public boolean isBasra(List<Card> capturedFloorCards, Table table) {
       // A Basra occurs if the captured floor cards constitute ALL cards currently on the floor.
       // Check if the number of captured cards equals the total number of cards on the floor *before* removal.
       return !capturedFloorCards.isEmpty() && capturedFloorCards.size() == table.size();
    }
}
