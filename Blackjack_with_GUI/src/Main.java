import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
       try {
    	   ServerSocket serverSocket = new ServerSocket(1912, 50, InetAddress.getByName("140.118.147.14"));
            System.out.println("Server is waiting for client connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("A new client connected!");
                // Handle client in a separate thread
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
       
    } 
      	
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Game setup
        	
        	
            Player myPlayer = new Player();
            myPlayer.setBalance(100); // Initialize player balance
            dealer myDealer = new dealer();

            boolean continueGame = true;

            while (continueGame) {
                Deck myDeck = new Deck(1);
                myDeck.shuffle();
                myPlayer.resetHand();
                myDealer.resetHand();

                out.println("Enter your bet amount:" + myPlayer.getBalance());
                out.println("BALANCE:" + myPlayer.getBalance());

                // Handle betting
                int betAmount;
                while (true) {
                    try {
                        betAmount = Integer.parseInt(in.readLine());
                        if(betAmount < 0) {
                        	betAmount = betAmount *(-1);
                        }
                        myPlayer.setBet(betAmount);
                        myPlayer.adjustBalance(-betAmount);
                        out.println("BET:" + betAmount);
                        out.println("BALANCE:" + myPlayer.getBalance());
                        break;
                    } catch (IllegalArgumentException e) {
                        out.println("Invalid bet: " + myPlayer.getBalance());
                    }
                }

                // Dealer and player draw initial cards
                myDealer.addCard(myDeck.drawCard());
                myDealer.addCard(myDeck.drawCard());
                myPlayer.receive(myDeck.drawCard());
                myPlayer.receive(myDeck.drawCard());
                
                sendPlayerHand(myPlayer, out);
                sendDealerHand(myDealer, out);
                
                out.println("BALANCE:" + myPlayer.getBalance());
                
                // Send initial card totals
                out.println("PLAYER VALUE:" + myPlayer.totalcard1());
                out.println("DEALER VALUE:" + myDealer.calculateHandValue());
                

                boolean playerTurn = true;
                while (playerTurn) {
                   // Display player's hand and total
                	out.println("PLAYER VALUE:" + myPlayer.totalcard1());

                    if (myPlayer.totalcard1() >= 21) break;

                    // Ask player for action
                    out.println("Enter 1 to Hit or 2 to Stand:");
                    int choice = Integer.parseInt(in.readLine());

                    if (choice == 1) { // Hit
                        myPlayer.receive(myDeck.drawCard());
                        out.println("You drew a new card!");
                    	out.println("PLAYER VALUE:" + myPlayer.totalcard1());
                        
                        sendDealerHand(myDealer, out); // Reveal dealer's hand
                        sendPlayerHand(myPlayer, out); // Ensure player's hand is updated
                        if (myPlayer.totalcard1() > 21) {
                            sendDealerHand(myDealer, out); // Reveal dealer's hand
                            sendPlayerHand(myPlayer, out); // Ensure player's hand is updated
                            out.println("YOU LOOSE" + myPlayer.getBalance());
                            out.println("ROUND OVER");
                            break;
                        }
                    } else if (choice == 2) { // Stand
                       
                        playerTurn = false;
                    }
                }
                
                sendDealerHand(myDealer, out); // Reveal dealer's hand
                sendPlayerHand(myPlayer, out); // Ensure player's hand is updated

                if (myPlayer.totalcard1() <= 21) {
                    // Dealer’s turn
                    while (myDealer.calculateHandValue() < 17) {
                        myDealer.addCard(myDeck.drawCard());
                        out.println("DEALER VALUE:" + myDealer.calculateHandValue());

                    }
                    
                    
                    sendDealerHand(myDealer, out); // Reveal dealer's hand
                    sendPlayerHand(myPlayer, out); // Ensure player's hand is updated                    

                    // Determine the outcome
                    if (myPlayer.totalcard1() > myDealer.calculateHandValue() && myPlayer.totalcard1() <= 21) {
                        if (myPlayer.totalcard1() == 21) {
                            myPlayer.adjustBalance(betAmount * 2.5);
                            out.println("YOU WIN" + myPlayer.getBalance());

                        } else {
                            myPlayer.adjustBalance(betAmount * 2);
                            out.println("YOU WIN" + myPlayer.getBalance());

                        }
                    } else if (myDealer.calculateHandValue() > 21) {
                        myPlayer.adjustBalance(betAmount * 2); 
                        out.println("YOU WIN" + myPlayer.getBalance());

                    } else {
                        out.println("YOU LOOSE" + myPlayer.getBalance());

                    }
                }

                out.println("Your current balance: " + myPlayer.getBalance());
                out.println("BALANCE:" + myPlayer.getBalance());

                // Check if the player can continue
                if (myPlayer.getBalance() <= 0) {
                	out.println("YOU LOOSE"  + myPlayer.getBalance());

                    continueGame = false;
                } else {
                    out.println("Do you want to play another game?");
                    int choice = Integer.parseInt(in.readLine());
                    if (choice != 1) {
                        continueGame = false;
                    }
                }
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void sendPlayerHand(Player player, PrintWriter out) {
        if (player.getHand().isEmpty()) {
            out.println("HAND:EMPTY");
            return;
        }

        StringBuilder serializedHand = new StringBuilder("HAND:");
        for (Card card : player.getHand()) {
            serializedHand.append(card.rank.name())
                          .append(" OF ")
                          .append(card.suit.name())
                          .append(",");
        }

        if (serializedHand.length() > 5) { // Remove trailing comma
            serializedHand.deleteCharAt(serializedHand.length() - 1);
        }

        String message = serializedHand.toString();
        System.out.println("Sending hand to client: " + message); // Debug log
        out.println(message);
    }
    
    private void sendDealerHand(dealer myDealer, PrintWriter out) {
        if (myDealer.getHand().isEmpty()) {
            out.println("HAND:EMPTY");
            return;
        }

        StringBuilder serializedHand = new StringBuilder("DEALER HAND:");
        for (Card card : myDealer.getHand()) {
            serializedHand.append(card.rank.name())
                          .append(" OF ")
                          .append(card.suit.name())
                          .append(",");
        }

        if (serializedHand.length() > 12) { // Remove trailing comma
            serializedHand.deleteCharAt(serializedHand.length() - 1);
        }

        String message = serializedHand.toString();
        System.out.println("Sending hand to client: " + message); // Debug log
        out.println(message);
    }


    // Method to display player choices

}
