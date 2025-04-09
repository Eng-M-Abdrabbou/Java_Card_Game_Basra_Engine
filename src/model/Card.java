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
import java.util.Objects;

public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isJack() {
        return this.rank == Rank.JACK;
    }

    public boolean isSpecialSevenDiamonds() {
        return this.rank == Rank.SEVEN && this.suit == Suit.DIAMONDS;
    }

    @Override
public String toString() {
    String rankStr = "";
    switch (rank) {
        case ACE:
            rankStr = "A";
            break;
        case KING:
            rankStr = "K";
            break;
        case QUEEN:
            rankStr = "Q";
            break;
        case JACK:
            rankStr = "J";
            break;
        case TEN:
            rankStr = "T";
            break;
        default:
            rankStr = String.valueOf(rank.getNumericValue());
            break;
    }

    String suitStr = "";
    switch (suit) {
        case HEARTS:
            suitStr = "H";
            break;
        case DIAMONDS:
            suitStr = "D";
            break;
        case CLUBS:
            suitStr = "C";
            break;
        case SPADES:
            suitStr = "S";
            break;
    }
    return rankStr + suitStr;
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
}
