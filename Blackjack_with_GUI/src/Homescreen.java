import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;

public class Homescreen implements ActionListener {
	
	JFrame frame = new JFrame();
	JButton play_button = new JButton();
	
    JLabel homescreen_label = new JLabel();
    JLabel background_label=new JLabel();
    
    //pfp labels
    JLabel facu_label = new JLabel();
    JLabel jossu_label = new JLabel();
    JLabel stockel_label = new JLabel();
    JLabel toma_label = new JLabel();
    
    //pfp buttons
	JButton facu_button = new JButton();
	JButton stockel_button = new JButton();
	JButton jossu_button = new JButton();
	JButton toma_button = new JButton();

	
	

	Homescreen() {
		//-----------FONTS-----------------

		
		//------------IMAGES---------------
		//background
	    ImageIcon background=new ImageIcon("background.jpg");
	    Image background_img=background.getImage();
	    Image temp=background_img.getScaledInstance(500,600,Image.SCALE_SMOOTH);
	    background=new ImageIcon(temp);   
	    
	    //LOGO
	    ImageIcon logo = new ImageIcon("homescreen.png");
	    
	    //homescreen
	    ImageIcon homescreen = new ImageIcon("blackjack807.png");
		Image resizedImage2 = homescreen.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
		ImageIcon resizedhomescreen = new ImageIcon(resizedImage2);	    
	    
	    //start button
	    ImageIcon start_button_img = new ImageIcon("start_button.png");
		Image resizedImage = start_button_img.getImage().getScaledInstance(150, 250, Image.SCALE_SMOOTH);
		ImageIcon resizedstartbutton = new ImageIcon(resizedImage);
		
		//pfp
		ImageIcon facu_icon = new ImageIcon("facu.jpeg");
		Image resizedfacu_icon = facu_icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
		ImageIcon resizedfacu = new ImageIcon(resizedfacu_icon);
		
		ImageIcon toma_icon = new ImageIcon("tomac.jpeg");
		Image resizedtoma_icon = toma_icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
		ImageIcon resizedtoma = new ImageIcon(resizedtoma_icon);
		
		ImageIcon stockel_icon = new ImageIcon("stockel.jpeg");
		Image resizedstockel_icon = stockel_icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		ImageIcon resizedstockel = new ImageIcon(resizedstockel_icon);
		
		ImageIcon jossu_icon = new ImageIcon("jossu.jpeg");
		Image resizedjossu_icon = jossu_icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
		ImageIcon resizedjossu = new ImageIcon(resizedjossu_icon);
		
		//borders
		Border black_border = BorderFactory.createLineBorder(Color.black,3);
	    
		

		
		//-----------------BUTTONS------------------------
		play_button.setBounds(150, 450,200,50);
		play_button.setFocusable(false);
		play_button.setVisible(true);
		play_button.addActionListener(this);
		play_button.setIcon(resizedstartbutton);
		play_button.setText("START GAME");	
		play_button.setFont(new Font("SimSun",Font.BOLD,20));
		play_button.setHorizontalTextPosition(JLabel.CENTER);
		play_button.setVerticalTextPosition(JLabel.CENTER); //con respecto a la imagen
		play_button.setForeground(Color.WHITE);
		play_button.setBorderPainted(false); // Disable border painting
		play_button.setFocusPainted(false);  // Disable focus outline
		play_button.setContentAreaFilled(false); // Remove background
		
		facu_button.setBounds(50, 260, 150, 50);
		facu_button.setFocusable(false);
		facu_button.setVisible(false);
		facu_button.addActionListener(this);
		facu_button.setIcon(resizedstartbutton);
		facu_button.setText("Select facu");	
		facu_button.setFont(new Font("SimSun",Font.BOLD,15));
		facu_button.setHorizontalTextPosition(JLabel.CENTER);
		facu_button.setVerticalTextPosition(JLabel.CENTER); //con respecto a la imagen
		facu_button.setForeground(Color.WHITE);
		facu_button.setBorderPainted(false); // Disable border painting
		facu_button.setFocusPainted(false);  // Disable focus outline
		facu_button.setContentAreaFilled(false); // Remove background
		
		jossu_button.setBounds(300, 260, 150, 50);
		jossu_button.setFocusable(false);
		jossu_button.setVisible(false);
		jossu_button.addActionListener(this);
		jossu_button.setIcon(resizedstartbutton);
		jossu_button.setText("Select jossu");	
		jossu_button.setFont(new Font("SimSun",Font.BOLD,15));
		jossu_button.setHorizontalTextPosition(JLabel.CENTER);
		jossu_button.setVerticalTextPosition(JLabel.CENTER); //con respecto a la imagen
		jossu_button.setForeground(Color.WHITE);
		jossu_button.setBorderPainted(false); // Disable border painting
		jossu_button.setFocusPainted(false);  // Disable focus outline
		jossu_button.setContentAreaFilled(false); // Remove background
		
		toma_button.setBounds(50, 480, 150, 50);
		toma_button.setFocusable(false);
		toma_button.setVisible(false);
		toma_button.addActionListener(this);
		toma_button.setIcon(resizedstartbutton);
		toma_button.setText("Select tomac");	
		toma_button.setFont(new Font("SimSun",Font.BOLD,15));
		toma_button.setHorizontalTextPosition(JLabel.CENTER);
		toma_button.setVerticalTextPosition(JLabel.CENTER); //con respecto a la imagen
		toma_button.setForeground(Color.WHITE);
		toma_button.setBorderPainted(false); // Disable border painting
		toma_button.setFocusPainted(false);  // Disable focus outline
		toma_button.setContentAreaFilled(false); // Remove background
		
		stockel_button.setBounds(300, 480, 150, 50);
		stockel_button.setFocusable(false);
		stockel_button.setVisible(false);
		stockel_button.addActionListener(this);
		stockel_button.setIcon(resizedstartbutton);
		stockel_button.setText("Select alex");	
		stockel_button.setFont(new Font("SimSun",Font.BOLD,15));
		stockel_button.setHorizontalTextPosition(JLabel.CENTER);
		stockel_button.setVerticalTextPosition(JLabel.CENTER); //con respecto a la imagen
		stockel_button.setForeground(Color.WHITE);
		stockel_button.setBorderPainted(false); // Disable border painting
		stockel_button.setFocusPainted(false);  // Disable focus outline
		stockel_button.setContentAreaFilled(false); // Remove background
		


		//---------------LAYEREDPANE---------------------
		 JLayeredPane layeredpane = new JLayeredPane();
		 layeredpane.setBounds(0,0,500,600);
		
		
		//---------------LABELS--------------------------
		 //background
		 
		background_label.setIcon(background);
	    background_label.setLayout(null);
	    background_label.setBounds(0,0,500,600);
	    background_label.setVisible(false);
	    
	    //home screen label
	    homescreen_label.setIcon(resizedhomescreen);
	    homescreen_label.setOpaque(true);
	    homescreen_label.setBounds(0, 0, 500, 500);
	    
	    //pfp labels
	    facu_label.setIcon(resizedfacu);
	    facu_label.setBounds(50, 100, 150, 150);
	    facu_label.setVisible(false);
	    facu_label.setBorder(black_border);
	    jossu_label.setIcon(resizedjossu);
	    jossu_label.setBounds(300, 100, 150, 150);
	    jossu_label.setVisible(false);
	    jossu_label.setBorder(black_border);
	    toma_label.setIcon(resizedtoma);
	    toma_label.setBounds(50, 320, 150, 150); 
	    toma_label.setVisible(false);
	    toma_label.setBorder(black_border);
	    stockel_label.setIcon(resizedstockel);
	    stockel_label.setBounds(300, 320, 150, 150);
	    stockel_label.setVisible(false);
	    stockel_label.setBorder(black_border);
	    
	    
		//------------FRAME----------------
		frame.setTitle("BLACKJACK 807");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(500,600);
		frame.setVisible(true);
		frame.setIconImage(logo.getImage()); 
		
		frame.add(layeredpane);
		layeredpane.add(play_button,Integer.valueOf(2));
		layeredpane.add(facu_label,Integer.valueOf(3));
		layeredpane.add(toma_label,Integer.valueOf(3));
		layeredpane.add(jossu_label,Integer.valueOf(3));
		layeredpane.add(stockel_label,Integer.valueOf(3));
		layeredpane.add(background_label,Integer.valueOf(0));
		layeredpane.add(homescreen_label, Integer.valueOf(2));
		layeredpane.add(facu_button,Integer.valueOf(3));
		layeredpane.add(jossu_button,Integer.valueOf(3));
		layeredpane.add(toma_button,Integer.valueOf(3));
		layeredpane.add(stockel_button,Integer.valueOf(3));
		

		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==play_button) { //e is an action event object
			homescreen_label.setVisible(false);  
		    background_label.setVisible(true);
		    play_button.setVisible(false);
		    facu_label.setVisible(true);
			facu_button.setVisible(true);
		    toma_label.setVisible(true);
			toma_button.setVisible(true);
		    stockel_label.setVisible(true);
			stockel_button.setVisible(true);
		    jossu_label.setVisible(true);
			jossu_button.setVisible(true);

		}
		if(e.getSource()==facu_button) {
			frame.dispose();
			new client_blackjack("facu.jpeg");
			frame.dispose();
		}
		if(e.getSource()==stockel_button) {
			frame.dispose();
			new client_blackjack("stockel.jpeg");
		}
		if(e.getSource()==toma_button) {
			frame.dispose();
			new client_blackjack("tomac.jpeg");
			frame.dispose();
		}
		if(e.getSource()==jossu_button) {
			frame.dispose();
			new client_blackjack("jossu.jpeg");
			frame.dispose();
		}
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		new Homescreen();
	}

}
