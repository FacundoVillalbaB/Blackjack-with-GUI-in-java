public class Card {

    // Enum for card ranks (value of the cards)
    public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        // Value of the rank (used to calculate points in a game like Blackjack)
        public int value;

        // Constructor to assign value to each rank
        Rank(int value) {
            this.value = value;
        }

        // Method to retrieve the value of the rank
        public int getValue() {
            return value;
        }
    }

    // Enum for card suits
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES;
    }

    // Fields to store the rank and suit of the card
    public Rank rank;
    public Suit suit;

    // Constructor to create a Card object with a specified rank and suit
    public Card(Rank ranks, Suit suits) {
        this.rank = ranks;
        this.suit = suits;
    }

    // Mthod for the rank of the card
    public Rank rank() {
        return rank;
    }

    // Method for the suit of the card
    public Suit suit() {
        return suit;
    }

    // Method to retrieve the point value of the card 
    public int getValue() {
        return rank.getValue();
    }

    // Overriding the toString() method for better string representation of the card
    @Override
    public String toString() {
        return rank.getValue() + " OF " + suit;  
    }

}