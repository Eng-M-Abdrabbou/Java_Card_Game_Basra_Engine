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
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards; 

    public Deck() {
        this.cards = new Stack<>();
        initializeDeck();
    }

    public void initializeDeck() {
        cards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.push(new Card(suit, rank));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public Card dealCard() {
        if (isEmpty()) {
            return null; // Or throw an exception
        }
        return cards.pop();
    }

    public int cardsRemaining() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public List<Card> peekTop(int count) {
        List<Card> topCards = new ArrayList<>();
        if (count > cards.size()) count = cards.size();
        for (int i = 0; i < count; i++) {
            topCards.add(cards.get(cards.size() - 1 - i)); // Peek from the top
        }
        return topCards;
    }
}
