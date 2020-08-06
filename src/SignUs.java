import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUs extends JDialog {
	static final String FONT_STYLE = "Lucida Grande";
	static final int FONT_SIZE =20;
	static final int LINE_SPACING = 55;
	static final int INPUT_SPACE_WIDTH =150;
	static final int INPUT_SPACE_HEIGHT = 30;
	static boolean isDuplicated = true;
	
	public SignUs(Window parent) {
		super(parent,"Sign Up",ModalityType.APPLICATION_MODAL);
		setSize(400,600);
		setLayout(null);
		
		JLabel registerId = new JLabel("ID : ");
		registerId.setFont(new Font(FONT_STYLE, Font.PLAIN, FONT_SIZE));
		registerId.setBounds(60+10, 105, 61, 16);
		
		JTextField txtRegId = new JTextField(20);
		txtRegId.setBounds(100+10, 100, INPUT_SPACE_WIDTH, INPUT_SPACE_HEIGHT);
		
		JLabel registerPw = new JLabel("PW : ");
		registerPw.setFont(new Font(FONT_STYLE,Font.PLAIN,FONT_SIZE));
		registerPw.setBounds(53+10, 105+LINE_SPACING, 61, 16);
		
		JPasswordField RegPw = new JPasswordField(20);
		RegPw.setBounds(100+10, 100+LINE_SPACING, INPUT_SPACE_WIDTH, INPUT_SPACE_HEIGHT);
		
		JLabel registerPw_2 = new JLabel("Re PW : ");
		registerPw_2.setFont(new Font(FONT_STYLE,Font.PLAIN,FONT_SIZE));
		registerPw_2.setBounds(22+10, 105+LINE_SPACING*2, 80, 16);
		
		JPasswordField RegPw_2 = new JPasswordField(20);
		RegPw_2.setBounds(100+10, 100+LINE_SPACING*2, INPUT_SPACE_WIDTH, INPUT_SPACE_HEIGHT);
		
		JLabel name = new JLabel("Name : ");
		name.setFont(new Font(FONT_STYLE,Font.PLAIN,FONT_SIZE));
		name.setBounds(22+10, 105+LINE_SPACING*3, 80, 16);
		
		JTextField txtName = new JTextField(20);
		txtName.setBounds(100+10, 100+LINE_SPACING*3, INPUT_SPACE_WIDTH, INPUT_SPACE_HEIGHT);
		
		JLabel phone = new JLabel("Phone : ");
		phone.setFont(new Font(FONT_STYLE,Font.PLAIN,FONT_SIZE));
		phone.setBounds(22+10, 105+LINE_SPACING*4, 80, 16);
		
		JTextField txtPhone = new JTextField(20);
		txtPhone.setBounds(100+10, 100+LINE_SPACING*4, INPUT_SPACE_WIDTH, INPUT_SPACE_HEIGHT);
		
		JButton btnCheckDup = new JButton("Check");
		btnCheckDup.setBounds(270,100, 100, 30);
		btnCheckDup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[][] member = Member.member;
				String getId = txtRegId.getText();
				
				if(getId.equals("")) {
					JOptionPane.showMessageDialog(null,"Fill Blank");
					return;
				}
				
				int proper = properId(getId);
				if(proper ==-1) {
					JOptionPane.showMessageDialog(null, "It should start with english");
					return;
				} else if (proper ==0) {
					JOptionPane.showMessageDialog(null, "ID cannot contain special character");
					return;
				}
				
				for(int i=0;i<member.length;i++) {
					if(getId.equals(member[i][0])) {
						JOptionPane.showMessageDialog(null, "This ID already exists");
						return;
					}
				}
				isDuplicated = false;
				JOptionPane.showMessageDialog(null, "Available ID");
			}
		});
		
		JButton btnRegister = new JButton("JOIN");
		
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isDuplicated == true) {
					JOptionPane.showMessageDialog(null, "Please Check ID Duplication");
					return;
				}
				String id = txtRegId.getText();
				char[] pw1 = RegPw.getPassword();
				char[] pw2 = RegPw_2.getPassword();
				String pw_1 = new String(pw1,0,pw1.length);
				String pw_2 = new String(pw2,0,pw2.length);
				
				if(!pw_1.equals(pw_2)) {
					JOptionPane.showMessageDialog(null, "The Passwords you entered don't match");
					return;
				}
				
				String name = txtName.getText();
				String phone = txtPhone.getText();
				
				if(id.equals("")) {
					JOptionPane.showMessageDialog(null, "Fill ID");
					return;
				}
				if(pw_1.equals("")) {
					JOptionPane.showMessageDialog(null, "Fill PW");
					return;
				}
				if(name.equals("")) {
					JOptionPane.showMessageDialog(null, "Fill Name");
					return;
				}
				if(phone.equals("")) {
					JOptionPane.showMessageDialog(null, "Fill Phone");
					return;
				}
				//Member.joinMember(id, pw_1, name, phone);
				JOptionPane.showMessageDialog(null,"Welcome! " +name);
			}
			
		});
		btnRegister.setBounds(150, 450, 100, 20);
		btnRegister.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		add(registerId);
		add(txtRegId);
		add(btnCheckDup);
		
		add(registerPw);
		add(RegPw);
		
		add(registerPw_2);
		add(RegPw_2);
		
		add(name);
		add(txtName);
		
		add(phone);
		add(txtPhone);
		
		add(btnRegister);
	}
	
	/*첫글자 영어 아니면 -1, 특수문자 껴있으면 0, 정상이면 1 */
	public static int properId(String id) {
		char[] charId = id.toCharArray();
		
		if(!(charId[0]>='a' && charId[0] <='z') && !(charId[0] >='A' && charId[0] <= 'Z'))
			return -1;
		
		for(int i=0;i<charId.length;i++) {
			char c = charId[i];
			if(  !(c<='9'&&c>='0') && !(c<='Z' && c>='A') && !(c<='z' && c>='a'))
				return 0;
		}
		return 1;
	}
}
