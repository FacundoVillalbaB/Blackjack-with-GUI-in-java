import java.util.ArrayList;
import java.util.List;

class dealer {
    private List<Card> hand = new ArrayList<>();
    int i = 0;

    // Adds a card to the dealer's hand and returns a message about the card added.
    public String addCard(Card card) {
        hand.add(card);
        return "Dealer received: " + card.getValue() + " of " + card.suit;
    }
    
    public ArrayList<Card> getHand() {
        return new ArrayList<>(hand); // Return a copy to preserve encapsulation
    }

    // Calculates the total value of the dealer's hand, accounting for Aces, and returns the total.
    public int calculateHandValue() {
        int totalValue = 0;
        int aceCount = 0;

        for (Card card : hand) {
            totalValue += card.getValue();
            if (card.toString().startsWith("A")) {
                aceCount++;
            }
        }
        

        // Adjust total value if it exceeds 21 and there are Aces.
        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;
    }
    
    public void resetHand() {
        hand.clear(); // Clear all cards from the dealer's hand
    }

    // Returns a string representing the dealer's hand.
    public String showHand() {
        StringBuilder handDescription = new StringBuilder();
        handDescription.append("\n╔═════════════════════════════════════╗\n");
        for (Card card : hand) {
            handDescription.append("\t").append(card.getValue()).append(" of ").append(card.suit).append("\n");
        }
        handDescription.append("╚═════════════════════════════════════╝");
        return handDescription.toString();
    }

    // Returns the total value of the dealer's hand as a formatted string, indicating if they busted.
    public String totalcarddealer() {
        int total = calculateHandValue();
        
        if (total > 21) {
            return "\n═════════════════════════════\n" +
                   "   Dealer Total: " + total + " (Busted!)\n" +
                   "═════════════════════════════";
        } else {
            return "\n═════════════════════════════\n" +
                   "   Dealer Total of cards: " + total + "\n" +
                   "═════════════════════════════";
        }
    }
}
