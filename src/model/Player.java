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

public class Player {
    private final String name;
    private final Hand hand;
    private final List<Card> capturePile; // Cards captured for scoring
    private int basraCount;
    private int score; // Calculated at the end

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.capturePile = new ArrayList<>();
        this.basraCount = 0;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public List<Card> getCapturePile() {
        return new ArrayList<>(capturePile); // Return copy
    }

    public void addCardToHand(Card card) {
        this.hand.addCard(card);
    }

    public boolean playCardFromHand(Card card) {
       return this.hand.removeCard(card);
    }

    public void addCardsToCapturePile(List<Card> captured) {
        if (captured != null) {
            this.capturePile.addAll(captured);
        }
    }

    public int getBasraCount() {
        return basraCount;
    }

    public void incrementBasraCount() {
        this.basraCount++;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCapturePileSize() {
        return capturePile.size();
    }

    @Override
    public String toString() {
        return "Player{" +
               "name='" + name + '\'' +
               ", hand=" + hand.size() + " cards" +
               ", captured=" + capturePile.size() + " cards" +
               ", basras=" + basraCount +
               '}';
    }
}
