import java.awt.Font;

import javax.swing.JTextField;

public class GameTime extends Thread {
	private static long time=0;
	JTextField txt;
	
	public GameTime(JTextField txt) {
		this.txt = txt;
		txt.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txt.setBounds(130, 500, 100, 30);
		txt.setText(getTimeString()+"");
		txt.setEnabled(false);
	}
	
	public void run() {
		long startTime = System.currentTimeMillis();
		
		while(!isInterrupted()) {
			time = (System.currentTimeMillis() - startTime)/1000;
			this.txt.setText(getTimeString());
		} 
	}
	
	public long getTime() {
		return time;
	}
	
	public String getTimeString() {
		
		long min = time/60;
		long sec = time%60;
		
		return "    "+min + " : " +sec;
	}
}
