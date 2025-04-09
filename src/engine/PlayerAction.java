/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import model.Card;
/**
 *
 * @author Hp
 */
public class PlayerAction {
    private final Card cardToPlay;

    public PlayerAction(Card cardToPlay) {
        if (cardToPlay == null) {
            throw new IllegalArgumentException("Card to play cannot be null");
        }
        this.cardToPlay = cardToPlay;
    }

    public Card getCardToPlay() {
        return cardToPlay;
    }
}