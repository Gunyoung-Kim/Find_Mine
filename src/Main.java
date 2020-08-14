import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class ImagePanel extends JPanel{
	private Image img;
	
	public ImagePanel(Image img){
		this.img = img;
		setSize(new Dimension(img.getWidth(null),img.getHeight(null)));
		setPreferredSize(new Dimension(img.getWidth(null),img.getHeight(null)));
		setLayout(null);
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(img, 0, 0, null);
	}
}

public class Main extends Thread{
	static int isFirst=0;
	static final int NUM_OF_RED_FLAG = 99;
	static int red_flag;
	static JFrame frame = new JFrame("Find Mine");
	static GameData game;
	static JTextField timeZone;
	static GameTime time;
	static Score gameScore;
	static JTextField scoreZone;
	static JTextField flagZone;
	static ImagePanel loginPanel;
	static ImagePanel mainPanel;
	static JLabel lblNewLabel;
	static JLabel lblPassword;
	static JTextField textFieldID;
	static JPasswordField passwordField;
	static JButton[][] gameBoard;
	static JButton btnLogin;
	static JButton btnClock;
	static JButton btnScore;
	static JButton btnFlag;
	static JLabel mainLabel; 
	
	public static void main(String[] args) {
		Runnable task = () -> {
			game = new GameData();
			red_flag = NUM_OF_RED_FLAG;
			timeZone = new JTextField(20);
			time = new GameTime(timeZone);
			gameScore = new Score();
			scoreZone = new JTextField(20);
			scoreZone.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
			scoreZone.setBounds(700, 500, 100, 30);
			scoreZone.setText(gameScore.getStringScore());
			scoreZone.setEnabled(false);
			flagZone = new JTextField(20);
			flagZone.setBounds(415,500,100,30);
			flagZone.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
			flagZone.setText("     "+red_flag);
			flagZone.setEnabled(false);
			//String[][] member = Member.getMember();
			
			//frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.setJMenuBar(menuBar());
			
			/* frame components */
			loginPanel = new ImagePanel(new ImageIcon("./image/startWindow.JPG").getImage());
			frame.add(loginPanel);
			frame.setSize(640,525);
			frame.setResizable(false);
			
			mainPanel = new ImagePanel(new ImageIcon("./image/white_back.jpg").getImage());
			frame.add(mainPanel);
			mainPanel.setVisible(false);
			
			/* loginPanel components */
			lblNewLabel = new JLabel("ID");
			lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(200, 236, 61, 16);
			loginPanel.add(lblNewLabel); 
			
			lblPassword = new JLabel("Password");
			lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
			lblPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
			lblPassword.setBounds(154, 283, 90, 16);
			loginPanel.add(lblPassword);
			
			textFieldID = new JTextField();
			textFieldID.setBounds(264, 226, 181, 26);
			loginPanel.add(textFieldID);
			textFieldID.setColumns(10);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(264, 281, 181, 26);
			loginPanel.add(passwordField);
			
			gameBoard = new JButton[16][30];
			
			/* mainPanel components */
			mainLabel = new JLabel("FIND MINE");
			mainLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
			mainLabel.setBounds(380, 30, 200, 30);
			mainPanel.add(mainLabel);
			
			for(int i=0;i<16;i++) {
				for(int j=0;j<30;j++) {
					gameBoard[i][j] = new JButton();
					gameBoard[i][j].setBounds(100+25*(j-1), 100+25*(i-1) , 25, 25);
					gameBoard[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					gameBoard[i][j].setBackground(Color.black);
					int row =i;
					int col = j;
					gameBoard[row][col].setIcon(new ImageIcon("./image/default_button.jpg","default_button"));
					
					gameBoard[i][j].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(isFirst ==0) {
								game.setGameData(row, col);
								time.start();
								isFirst =1;
							}
							
							Queue<Point> que = new LinkedList<>();
							int[] drow = new int[] {-1,0,0,1};
							int[] dcol = new int[] {0,1,-1,0};
							
							que.offer(new Point(row,col));
							
							int result_ = game.Click(row,col);
							
							EnableBlock(result_,row,col);
							
							
							while(!que.isEmpty()) {
								Point p = que.poll();
								
								for(int z=0;z<4;z++) {
									int nrow = p.getRow() + drow[z];
									int ncol = p.getCol() + dcol[z];
									
									if(nrow>=0 && nrow<gameBoard.length && ncol>=0 &&ncol <gameBoard[0].length && !game.isClicked(nrow, ncol) && !game.isMine(nrow, ncol)) {
										int result = game.Click(nrow,ncol);
										if(result ==0)
											que.offer(new Point(nrow,ncol));
										
										EnableBlock(result,nrow,ncol);
									}
								}
							}
						}
					});
					
					gameBoard[i][j].addMouseListener(new MouseListener() {

						@Override
						public void mouseClicked(MouseEvent e) {}

						@Override
						public void mousePressed(MouseEvent e) {
							if(e.getButton() == MouseEvent.BUTTON3) {
								ImageIcon icon = (ImageIcon)gameBoard[row][col].getIcon();
								if(icon.getDescription().equals("default_button") && red_flag >0) {
									gameBoard[row][col].setIcon(new ImageIcon("./image/red_flag.jpg","red_flag"));
									red_flag--;
									flagZone.setText("     "+red_flag);
								} else if(icon.getDescription().equals("red_flag")) {
									gameBoard[row][col].setIcon(new ImageIcon("./image/yellow_flag.jpg","yellow_flag"));
									red_flag++;
									flagZone.setText("     "+red_flag);
								} else if(icon.getDescription().equals("yellow_flag")) {
									gameBoard[row][col].setIcon(new ImageIcon("./image/default_button.jpg","default_button"));
								} else {
									gameBoard[row][col].setIcon(new ImageIcon("./image/red_flag.jpg","red_flag"));
									red_flag--;
									flagZone.setText("     "+red_flag);
								}
							}
						}

						@Override
						public void mouseReleased(MouseEvent e) {}

						@Override
						public void mouseEntered(MouseEvent e) {}

						@Override
						public void mouseExited(MouseEvent e) {}
					});
					
					mainPanel.add(gameBoard[i][j]);
				}
			}
			
			mainPanel.add(timeZone);
			mainPanel.add(scoreZone);
			mainPanel.add(flagZone);
			
			btnClock = new JButton();
			btnClock.setIcon(new ImageIcon("./image/clock.jpg"));
			btnClock.setBounds(90, 500, 30, 30);
			mainPanel.add(btnClock);
			
			btnScore = new JButton();
			btnScore.setIcon(new ImageIcon("./image/score.jpg"));
			btnScore.setBounds(660, 500, 30, 30);
			mainPanel.add(btnScore);
			
			btnFlag = new JButton();
			btnFlag.setIcon(new ImageIcon("./image/red_flag_whiteback.jpg"));
			btnFlag.setBounds(375,500,30,30);
			mainPanel.add(btnFlag);
			
			/* login button */
			btnLogin = new JButton("Login");
			btnLogin.setBackground(Color.WHITE);
			btnLogin.setIcon(new ImageIcon("./image/login.jpg"));
			btnLogin.setPressedIcon(new ImageIcon("./image/login_blue.JPG"));
			btnLogin.setBounds(264, 338, 181, 29);
			btnLogin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String enterId = textFieldID.getText();
					char[] pw = passwordField.getPassword();
					String enterPw = new String(pw,0,pw.length);
					int index;
					
					/*
					if((index =Member.isThereMember(enterId)) != -1) {
						if(Member.isPwCorrect(enterPw,index)) {
							loginPanel.setVisible(false);
							frame.setSize(900,700);
							frame.setLocationRelativeTo(null);
							mainPanel.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "Incorrect PW");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Unregistered ID");
					}
					*/
					
					loginPanel.setVisible(false);
					frame.setSize(900,700);
					frame.setLocationRelativeTo(null);
					mainPanel.setVisible(true);
				}
				
			});
			
			loginPanel.add(btnLogin);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			

		};
		
		Thread t1 = new Thread(task);
		t1.start();
	}
	

	public static void EnableBlock(int result_, int row,int col) {
		if(result_ ==-1) {
			gameBoard[row][col].setDisabledIcon(new ImageIcon("./image/mine.jpg","mine"));
			gameBoard[row][col].setEnabled(false);
			JOptionPane.showMessageDialog(null, "Boom!");
			int[] arr = game.getMineArray();
			
			for(int i=0;i<arr.length;i++) {
				int r = arr[i]/30;
				int c = arr[i]%30;
				
				gameBoard[r][c].setDisabledIcon(new ImageIcon("./image/mine.jpg","mine"));
				gameBoard[r][c].setEnabled(false);
			}
			//time.interrupt();
			//System.exit(0);
		} else {
			switch(result_) {
			case 0:
				gameBoard[row][col].setDisabledIcon(new ImageIcon("./image/zero.jpg"));
				gameBoard[row][col].setEnabled(false);
				break;
			case 1:
				gameBoard[row][col].setDisabledIcon(new ImageIcon("./image/one.jpg"));
				gameBoard[row][col].setEnabled(false);
				break;
			case 2:
				gameBoard[row][col].setDisabledIcon(new ImageIcon("./image/two.jpg"));
				gameBoard[row][col].setEnabled(false);
				break;
			case 3:
				gameBoard[row][col].setDisabledIcon(new ImageIcon("./image/three.jpg"));
				gameBoard[row][col].setEnabled(false);
				break;
			case 4:
				gameBoard[row][col].setDisabledIcon(new ImageIcon("./image/four.jpg"));
				gameBoard[row][col].setEnabled(false);
				break;
			case 5:
				gameBoard[row][col].setDisabledIcon(new ImageIcon("./image/five.jpg"));
				gameBoard[row][col].setEnabled(false);
				break;
			case 6:
				gameBoard[row][col].setDisabledIcon(new ImageIcon("./image/six.jpg"));
				gameBoard[row][col].setEnabled(false);
				break;
			case 7:
				gameBoard[row][col].setDisabledIcon(new ImageIcon("./image/seven.jpg"));
				gameBoard[row][col].setEnabled(false);
				break;
			case 8:
				gameBoard[row][col].setDisabledIcon(new ImageIcon("./image/eight.jpg"));
				gameBoard[row][col].setEnabled(false);
				break;
			}
			gameScore.increaseScore(time.getTime());
			scoreZone.setText(gameScore.getStringScore());
		}
	}
	
	public static JMenuBar menuBar() {
		JMenuBar bar = new JMenuBar();
		bar.setForeground(Color.WHITE);
		JMenu game = new JMenu("Game");
		JMenu member = new JMenu("Member");
		JMenu about = new JMenu("About");
		
		bar.add(game);
		bar.add(member);
		bar.add(about);
		
		JMenuItem newGame = new JMenuItem("New Game");
		
		JMenuItem option = new JMenuItem("Option");
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		game.add(newGame);
		game.addSeparator();
		game.add(option);
		game.addSeparator();
		game.add(exit);
		
		JMenuItem joinUs = new JMenuItem("Join Us");
		
		joinUs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SignUs signUs = new SignUs(frame);
				signUs.setVisible(true);
			}
			
		});
		
		member.add(joinUs);
		
		JMenuItem thisGame = new JMenuItem("This Game");
		
		thisGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "This game is made by Kim\nCopyright © 2020 김건영. All rights reserved.",
						"Find Mine",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JMenuItem howPlay = new JMenuItem("How to Play");
		howPlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"The game Screen consists of minefield with several square buttons. \n"
						+ "Each button can be pressed open\n"
						+ "If you press a button with a hidden mine and open it, the game is over. \n"
						+ "If it's a button without a mine, there are two cases.\n"
						+ "	1. The number of landmines hidden in other buttons around the perimeter that touch an open button may be written \n"
						+ " 2. If there are no hidden mines around it, the button automatically opens with it. \n" 
						+ "If you open all the buttons that are not hidden, you win the game.\n\n" 
						+ "Players can right-click a button that they think is hidden by a landmine and put up a flag.\n",
						"How To Play",JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		about.add(thisGame);
		about.add(howPlay);
		
		return bar;
	}
}

class Point {
	private int row;
	private int col;
	
	public Point(int row,int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
