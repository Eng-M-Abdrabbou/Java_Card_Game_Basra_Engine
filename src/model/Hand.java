/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Hp
 */
import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (card != null) {
            this.cards.add(card);
        }
    }

    public boolean removeCard(Card card) {
        return this.cards.remove(card);
    }

    public List<Card> getCards() {
        return new ArrayList<>(this.cards);
    }

    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

    public int size() {
        return this.cards.size();
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}