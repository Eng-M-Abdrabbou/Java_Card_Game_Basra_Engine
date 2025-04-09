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

public class Table {
    private final List<Card> floorCards;

    public Table() {
        this.floorCards = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (card != null) {
            this.floorCards.add(card);
        }
    }

    public void addCards(List<Card> cards) {
        if (cards != null) {
            this.floorCards.addAll(cards);
        }
    }

    public boolean removeCard(Card card) {
        return this.floorCards.remove(card);
    }

    public boolean removeCards(List<Card> cardsToRemove) {
        return this.floorCards.removeAll(cardsToRemove);
    }

    public List<Card> getCards() {
        // Return a copy
        return new ArrayList<>(this.floorCards);
    }

    public void clear() {
        this.floorCards.clear();
    }

    public boolean isEmpty() {
        return this.floorCards.isEmpty();
    }

     public int size() {
        return this.floorCards.size();
    }

    @Override
    public String toString() {
        return "Floor: " + floorCards.toString();
    }
}
