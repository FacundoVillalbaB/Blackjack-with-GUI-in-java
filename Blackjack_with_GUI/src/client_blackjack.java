import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;



public class client_blackjack extends JFrame implements ActionListener {
	
	//END SCREEN
	JLabel end_text = new JLabel();
	JLabel newbalancetext = new JLabel();
	
    JLabel background_label = new JLabel();
    JLabel hitstand_label = new JLabel();
    JLabel pfp_background_label = new JLabel();
    JLabel balanceLabel = new JLabel();
    JLabel betLabel = new JLabel();
    JLabel playerValueLabel = new JLabel();
    JLabel dealerValueLabel  = new JLabel();
    JLabel dealerTitle = new JLabel();
    JLabel playerTitle = new JLabel();
    JLabel gameinfotext = new JLabel();
    JLabel pfp = new JLabel();
    JLabel backofcard_label = new JLabel();
    
    JButton hit_button = new JButton();
    JButton stand_button = new JButton();
    
    JLayeredPane layeredpane = new JLayeredPane();

    private JPanel playerCardPanel;
    private JPanel dealerCardPanel;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    
    
    client_blackjack(String player_picture) {
        // Initialize GUI components
        initializeGUI(player_picture);
        // Start server communication
        new Thread(this::startserver).start();

        // Add a WindowListener to ensure cleanup
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeResources();
                System.exit(0); // Terminate the program
            }
        });
    }

    private void initializeGUI(String player_picture) {
    	//PFP
		ImageIcon character_picture = new ImageIcon(player_picture);
		Image resizedcharacter_picture = character_picture.getImage().getScaledInstance(240, 240, Image.SCALE_SMOOTH);
		ImageIcon resizedpfp = new ImageIcon(resizedcharacter_picture);
		
		pfp.setIcon(resizedpfp);
		pfp.setOpaque(true);
		pfp.setBounds(1024-240, 40, 240, 240);
		pfp.setVisible(false);
		layeredpane.add(pfp);
    	
        // Background
        ImageIcon background = new ImageIcon("background.jpg");
        Image background_img = background.getImage();
        Image temp = background_img.getScaledInstance(1024, 640, Image.SCALE_SMOOTH);
        background = new ImageIcon(temp);
        background_label.setIcon(background);
        background_label.setBounds(0, 0, 1024, 640);
        
        // pfp background
        pfp_background_label.setOpaque(true);
        pfp_background_label.setBackground(new Color(0xF5F5DC));
        pfp_background_label.setBounds(1024-240, 0, 240, 640);
        layeredpane.add(pfp_background_label);
        
        //game info text
        gameinfotext = new JLabel("GAME INFO");
        gameinfotext.setFont(new Font("SimSun", Font.BOLD, 20));
        gameinfotext.setForeground(Color.black);
        gameinfotext.setBounds(840, 0, 300, 30); // Position on the GUI
        gameinfotext.setVisible(false);
        layeredpane.add(gameinfotext, Integer.valueOf(4));        

        // Hit/Stand background
        hitstand_label.setOpaque(true);
        hitstand_label.setBackground(new Color(0xF5F5DC));
        hitstand_label.setBounds(0, 540, 1024, 100);

        //Back of card
        ImageIcon backofcard = new ImageIcon("Cards/back.png");
        Image cardIcon_img = backofcard.getImage();
        Image cardIcon_temp = cardIcon_img.getScaledInstance(100, 145, Image.SCALE_SMOOTH);
        backofcard = new ImageIcon(cardIcon_temp);
        backofcard_label.setIcon(backofcard);
        backofcard_label.setBounds(298, 105, 100, 145);
        backofcard_label.setVisible(false);
        layeredpane.add(backofcard_label,Integer.valueOf(5));
        
        // Hit button
        hit_button.setBounds(100, 547, 200, 50);
        hit_button.setText("HIT");
        hit_button.setFont(new Font("SimSun", Font.BOLD, 20));
        hit_button.addActionListener(this);

        // Stand button
        stand_button.setBounds(350, 547, 200, 50);
        stand_button.setText("STAND");
        stand_button.setFont(new Font("SimSun", Font.BOLD, 20));
        stand_button.addActionListener(this);
        
        hit_button.setVisible(false);
        stand_button.setVisible(false);
        pfp_background_label.setVisible(false);
        hitstand_label.setVisible(false);
        
        // Card panel to display player cards
        playerCardPanel = new JPanel();
        playerCardPanel.setLayout(new FlowLayout());
        playerCardPanel.setBounds(0, 320, 800, 200);
        playerCardPanel.setOpaque(false); // Make the card panel transparent
        
        // Card panel to display dealer cards
        dealerCardPanel = new JPanel();
        dealerCardPanel.setLayout(new FlowLayout());
        dealerCardPanel.setBounds(0, 100, 800, 200);
        dealerCardPanel.setOpaque(false); // Make the card panel transparent
        

        // Balance Label
        balanceLabel = new JLabel("Balance: $0");
        balanceLabel.setFont(new Font("SimSun", Font.BOLD, 20));
        balanceLabel.setForeground(Color.black);
        balanceLabel.setBounds(790, 350, 300, 30); // Position on the GUI
        balanceLabel.setVisible(false);
        layeredpane.add(balanceLabel, Integer.valueOf(3));

        // Bet Label
        betLabel = new JLabel("Bet: $0");
        betLabel.setFont(new Font("SimSun", Font.BOLD, 20));
        betLabel.setForeground(Color.black);
        betLabel.setBounds(790, 400, 300, 30); // Position on the GUI
        betLabel.setVisible(false);
        layeredpane.add(betLabel, Integer.valueOf(3));

        // Player Value Label
        playerValueLabel = new JLabel("Player Total: 0");
        playerValueLabel.setFont(new Font("SimSun", Font.BOLD, 20));
        playerValueLabel.setForeground(Color.black);
        playerValueLabel.setBounds(790, 500, 300, 30); // Position on the GUI
        playerValueLabel.setVisible(false);
        layeredpane.add(playerValueLabel, Integer.valueOf(3));

        // Dealer Value Label
        dealerValueLabel = new JLabel("Dealer Total: 0");
        dealerValueLabel.setFont(new Font("SimSun", Font.BOLD, 20));
        dealerValueLabel.setForeground(Color.black);
        dealerValueLabel.setBounds(790, 450, 300, 30); // Position on the GUI
        dealerValueLabel.setVisible(false);
        layeredpane.add(dealerValueLabel, Integer.valueOf(3));

        //Player text 
        playerTitle = new JLabel("Player's Hand");
        playerTitle.setFont(new Font("SimSun", Font.BOLD, 20));
        playerTitle.setForeground(Color.WHITE);
        playerTitle.setBounds(330, 290, 300, 30); // Position on the GUI
        playerTitle.setVisible(false);
        layeredpane.add(playerTitle, Integer.valueOf(3));
        
        //dealer text 
        dealerTitle = new JLabel("Dealer's Hand");
        dealerTitle.setFont(new Font("SimSun", Font.BOLD, 20));
        dealerTitle.setForeground(Color.WHITE);
        dealerTitle.setBounds(330, 70, 300, 30); // Position on the GUI
        dealerTitle.setVisible(false);
        layeredpane.add(dealerTitle, Integer.valueOf(3));
        
        // Frame setup
        layeredpane.add(background_label, Integer.valueOf(0));
        layeredpane.add(hit_button, Integer.valueOf(2));
        layeredpane.add(stand_button, Integer.valueOf(2));
        layeredpane.add(hitstand_label, Integer.valueOf(1));
        layeredpane.add(playerCardPanel, Integer.valueOf(3)); // Add card panel to layered pane
        layeredpane.add(dealerCardPanel, Integer.valueOf(2)); // Add card panel to layered pane
        layeredpane.add(balanceLabel, Integer.valueOf(4));
        layeredpane.add(betLabel, Integer.valueOf(4));
        layeredpane.add(playerValueLabel, Integer.valueOf(4));

        
        this.setTitle("BLACKJACK 807");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1024, 640);
        this.add(layeredpane);
        this.setVisible(true);
    }
    
    private void startserver() {
        try {
            socket = new Socket("140.118.147.14", 1912);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            // Listen for server responses
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server: " + serverResponse);
                // Update GUI based on server response
                handleServerResponse(serverResponse);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleServerResponse(String response) {
        SwingUtilities.invokeLater(() -> {
        	
        	//HANDLE DEALER HAND RESPONSE
        	if (response.startsWith("DEALER HAND:")) {
        	    try {
        	        String[] cards = response.substring(12).split(",");
        	        dealerCardPanel.removeAll(); // Clear previous cards
        	        for (String card : cards) {
        	            displayDealerCards(card.trim()); // Display each card
        	        }
        	        dealerCardPanel.revalidate();
        	        dealerCardPanel.repaint(); // Ensure panel refreshes
        	    } catch (Exception e) {
        	        e.printStackTrace(); // Log any exceptions
        	    }
        	}

        	//HANDLE PLAYER HAND RESPONSE
        	else if (response.startsWith("HAND:")) {
        	    try {
        	        String[] cards = response.substring(5).split(",");
        	        playerCardPanel.removeAll(); // Clear previous cards
        	        for (String card : cards) {
        	            displayPlayerCards(card.trim()); // Display each card
        	        }
        	        playerCardPanel.revalidate();
        	        playerCardPanel.repaint(); // Ensure panel refreshes
        	    } catch (Exception e) {
        	        e.printStackTrace(); // Log any exceptions
        	    }
        	}



 
            //HANDLE HIT STAND
            else if (response.contains("Enter 1 to Hit or 2 to Stand")) {
                // Enable buttons for user action
                hit_button.setVisible(true);
                stand_button.setVisible(true);
            } 
            
            //PLAY ANOTHER GAME
            else if(response.contains("Do you want to play another game?")) {

            	int choice = JOptionPane.showConfirmDialog(
                        null, // Parent component (null means centered on screen)
                        "Do you want to play another game?", // Message
                        "Play Again?", // Title
                        JOptionPane.YES_NO_OPTION, // Options (Yes and No)
                        JOptionPane.QUESTION_MESSAGE // Icon type
                );
            	if (choice == JOptionPane.YES_OPTION) {
                    out.println(1); // Send "Hit" to server
                    resetGameGUI();
                } else if (choice == JOptionPane.NO_OPTION) {
                    out.println(2); // Send "No" to server
                    closeResources(); // Clean up resources
                    System.exit(0); // Terminate the program
                } else {
                    out.println(2); // Send "No" to server
                    closeResources(); // Clean up resources
                    System.exit(0); // Terminate the program
                }
            } 
            
        	//HANDLE BET AMOUNT RESPONSE
            else if(response.contains("Enter your bet amount:") || response.startsWith("Invalid bet: ")) { //INVALID BET IS USED AS AN CONDITION IF THE PLAYER DOESS NOT INTRODUCE A VALID BET
                backofcard_label.setVisible(false);
                balanceLabel.setVisible(false);
                betLabel.setVisible(false);
                playerValueLabel.setVisible(false);
                dealerValueLabel.setVisible(false);
                playerTitle.setVisible(false);
                dealerTitle.setVisible(false);
        		pfp.setVisible(false);
                gameinfotext.setVisible(false);
                hitstand_label.setVisible(false);
                hit_button.setVisible(false);
                stand_button.setVisible(false);
                pfp_background_label.setVisible(false);
                playerCardPanel.setVisible(false);
                dealerCardPanel.setVisible(false);
            	end_text.setVisible(false);
            	newbalancetext.setVisible(false);
            	
            	if(response.contains("Enter your bet amount:")) {
                	String balance = response.substring(22).trim();
                    String betAmount = JOptionPane.showInputDialog("Your balance: " + balance, "Enter BET amount:" );
                    if (betAmount == null) { // User clicked "Cancel" or closed the dialog
                        closeResources(); // Clean up resources
                        System.exit(0); // Terminate the program
                    }
                    out.println(betAmount);
            	} else { 
                	String balance = response.substring(13).trim();
                    String betAmount = JOptionPane.showInputDialog("Invalid bet. Your balance: " + balance, "Enter BET amount:" );
                    if (betAmount == null) { // User clicked "Cancel" or closed the dialog
                        closeResources(); // Clean up resources
                        System.exit(0); // Terminate the program
                    }
                    out.println(betAmount);
            	}

            	//SHOW ALL THE ELEMENTS AGAIN
                balanceLabel.setVisible(true);
                backofcard_label.setVisible(true);
                betLabel.setVisible(true);
                playerValueLabel.setVisible(true);
                dealerValueLabel.setVisible(true);
                playerTitle.setVisible(true);
                dealerTitle.setVisible(true);
        		pfp.setVisible(true);
                gameinfotext.setVisible(true);
                hitstand_label.setVisible(true);
                hit_button.setVisible(true);
                stand_button.setVisible(true);
                pfp_background_label.setVisible(true);
                playerCardPanel.setVisible(true);
                dealerCardPanel.setVisible(true);

                
            }
            
        	//HANDLE BALANCE
            if (response.startsWith("BALANCE:")) {
                try {
                    String balance = response.substring(8).trim();
                    balanceLabel.setText("Balance: $" + balance);
                } catch (Exception e) {
                    e.printStackTrace(); // Log any exceptions
                }
            }

            // Handle bet updates
            else if (response.startsWith("BET:")) {
                try {
                    String bet = response.substring(4).trim();
                    betLabel.setText("Bet: $" + bet);
                } catch (Exception e) {
                    e.printStackTrace(); // Log any exceptions
                }
            }

            // Handle player card value updates
            else if (response.startsWith("PLAYER VALUE:")) {
                try {
                    String playerValue = response.substring(13).trim();
                    playerValueLabel.setText("Player Total: " + playerValue);
                } catch (Exception e) {
                    e.printStackTrace(); // Log any exceptions
                }
            }

            // Handle dealer card value updates
            else if (response.startsWith("DEALER VALUE:")) {
                try {
                    dealerValueLabel.setText("Dealer Total: ?");
                } catch (Exception e) {
                    e.printStackTrace(); // Log any exceptions
                }
            }
            
            //END SCREEN HANDLING
            
            
            else if(response.startsWith("YOU WIN")) {
                backofcard_label.setVisible(false);
            	String balance = response.substring(7).trim();
            	end_screen(true,balance);
            }
           
            else if(response.startsWith("YOU LOOSE")) {
                backofcard_label.setVisible(false);
            	String balance = response.substring(9).trim();
            	end_screen(false,balance);
            }
            


        });
    }
    
    //display player cards
    private void displayPlayerCards(String cardName) {
    	ImageIcon cardIcon = new ImageIcon("Cards/" + cardName + ".png");
        Image cardIcon_img = cardIcon.getImage();
        Image cardIcon_temp = cardIcon_img.getScaledInstance(100, 145, Image.SCALE_SMOOTH);
        JLabel cardLabel = new JLabel();
        
        cardIcon = new ImageIcon(cardIcon_temp);
        cardLabel.setIcon(cardIcon);
              
        // Add the card to the card panel
        playerCardPanel.add(cardLabel);

        // Refresh the panel to display the new card
        playerCardPanel.revalidate();
        playerCardPanel.repaint();
    }
    
    //dealer cards
    private void displayDealerCards(String cardName) {
    	ImageIcon cardIcon = new ImageIcon("Cards/" + cardName + ".png");
        Image cardIcon_img = cardIcon.getImage();
        Image cardIcon_temp = cardIcon_img.getScaledInstance(100, 145, Image.SCALE_SMOOTH);
        JLabel cardLabel = new JLabel();
        
        cardIcon = new ImageIcon(cardIcon_temp);
        cardLabel.setIcon(cardIcon);
              
              
        dealerCardPanel.add(cardLabel); 
        
        dealerCardPanel.revalidate();
        dealerCardPanel.repaint();
    }


    
    private void resetGameGUI() {
        // Clear the card panel
        playerCardPanel.removeAll();
        playerCardPanel.revalidate();
        playerCardPanel.repaint();
        
        // Clear the card panel
        dealerCardPanel.removeAll();
        dealerCardPanel.revalidate();
        dealerCardPanel.repaint();

        // Reset buttons (hide them initially until a response from the server)
        hit_button.setVisible(false);
        stand_button.setVisible(false);
    }
    
    private void end_screen(boolean win, String balance) {
    	
    	
        balanceLabel.setVisible(false);
        betLabel.setVisible(false);
        playerValueLabel.setVisible(false);
        dealerValueLabel.setVisible(false);
		pfp.setVisible(false);
        gameinfotext.setVisible(false);
        hitstand_label.setVisible(false);
        hit_button.setVisible(false);
        stand_button.setVisible(false);
        pfp_background_label.setVisible(false);
        
        
    	
    	if(win) {
            end_text = new JLabel("YOU WIN!");
            
            end_text.setFont(new Font("SimSun", Font.BOLD, 100));
            end_text.setForeground(Color.GREEN);
            end_text.setBounds(300, 0, 1024, 640); // Position on the GUI
            end_text.setVisible(true);
            layeredpane.add(end_text,Integer.valueOf(5));
            
            newbalancetext = new JLabel("New balance: " + balance);
            newbalancetext.setFont(new Font("SimSun", Font.BOLD, 50));
            newbalancetext.setForeground(Color.WHITE);
            newbalancetext.setBounds(300, 200, 1024, 640); // Position on the GUI
            newbalancetext.setVisible(true);
            layeredpane.add(newbalancetext,Integer.valueOf(5));
    	} else {
            end_text = new JLabel("YOU LOOSE!");
            end_text.setFont(new Font("SimSun", Font.BOLD, 100));
            end_text.setForeground(Color.RED);
            end_text.setBounds(300, 0, 1024, 640); // Position on the GUI
            end_text.setVisible(true);
            layeredpane.add(end_text,Integer.valueOf(5));
            
            newbalancetext = new JLabel("New balance: " + balance);
            newbalancetext.setFont(new Font("SimSun", Font.BOLD, 50));
            newbalancetext.setForeground(Color.WHITE);
            newbalancetext.setBounds(300, 200, 1024, 640); // Position on the GUI
            newbalancetext.setVisible(true);
            layeredpane.add(newbalancetext,Integer.valueOf(5));
    	}

        
    }
    
    private void closeResources() {
        try {
            if (out != null) {
                out.println("DISCONNECT"); // Notify the server about disconnection (optional)
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("Resources closed and client disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hit_button) {
            out.println(1); // Send "Hit" to server
            hit_button.setVisible(false);
            stand_button.setVisible(false);
        } else if (e.getSource() == stand_button) {
            out.println(2); // Send "Stand" to server
            hit_button.setVisible(false);
            stand_button.setVisible(false);
        } 
        
    }
}




