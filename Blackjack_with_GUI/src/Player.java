import java.util.ArrayList;

public class Player {
    private ArrayList<Card> carta; // Player's hand
    private int i = 0; // Index for adding cards
    private int balance; // Player's current balance
    private int bet; // Current bet amount

    public Player() {
        carta = new ArrayList<>();
        balance = 0; // Initialize balance (can be set when starting the game)
        bet = 0;
    }
    
    public ArrayList<Card> getHand() {
        return new ArrayList<>(carta); // Return a copy to preserve encapsulation
    }

    // Returns a formatted string for the card received instead of printing.
    public String receive(Card card) {
        carta.add(card);
        String cardInfo = "╔═════════════════════════════════════╗\n" +
                          "\t" + carta.get(i).getValue() + " of " + card.suit + "\n" +
                          "╚═════════════════════════════════════╝";
        i++;
        return cardInfo;
    }

    // Returns the entire player's hand as a formatted string instead of printing.
    public String printallcards() {
        StringBuilder handDescription = new StringBuilder();
        //handDescription.append("\n\tNew Player Hand!\n");
        //handDescription.append("╔═════════════════════════════════════╗\n");
        for (int i = 0; i < carta.size(); i++) {
            Card cardlist = carta.get(i);
            handDescription.append("\t").append(cardlist.getValue()).append(" OF ").append(cardlist.suit).append("\n");
        }
       // handDescription.append("╚═════════════════════════════════════╝");
        return handDescription.toString();
    }

    // Returns the total value of the player's cards as a string, indicating if they busted.
    
   

    // Keeps the total calculation for comparison or use in other logic.
    public int totalcard1() {
        int total = 0;
        for (Card card : carta) {
            total += card.getValue();
        }
        return total;
    }

    public void resetHand() {
        carta.clear(); // Clear all cards from the player's hand
        i = 0;         // Reset the card counter
    }
    
    
    // Set the player's balance at the start of the game
    public void setBalance(int amount) {
        this.balance = amount;
    }

    // Get the player's current balance
    public double getBalance() {
        return this.balance;
    }

    // Set the player's bet
    public void setBet(int betAmount) {
        if (betAmount > balance) {
            throw new IllegalArgumentException("Insufficient balance to place the bet!");
        }
        this.bet = betAmount;
    }

    // Get the player's current bet
    public int getBet() {
        return this.bet;
    }

    // Adjust the player's balance after a win/loss
    public void adjustBalance(double amount) {
        this.balance += amount;
    }

    // Reset bet after each round
    public void resetBet() {
        this.bet = 0;
    }
    
    
    
}


