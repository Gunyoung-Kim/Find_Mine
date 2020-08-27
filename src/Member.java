import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Member {
	
	static String[][] member;
	
	public static boolean isPwCorrect(String pw,int index) {
		if(member[index][1].equals(pw))
			return true;
		else 
			return false;
	}
	
	public static int isThereMember(String id) {
		for(int i=0;i<member.length;i++) {
			if(member[i][0].equals(id)) {
				return i;
			}
		}
		return -1;
	}
	
	public static User loginUser(String id) {
		try {
			Connection con = getConnection();
			String id_ = "";
			String password = "";
			String name = "";
			String phone = "";
			String score = "";
			PreparedStatement loginUser = con.prepareStatement("Select id, password, name , phone, highest_score "
					+ "FROM member "
					+ "WHERE id = "+"\""+id+"\"");
			ResultSet results = loginUser.executeQuery();
			if(results.next()) {
				id_ = results.getString("id");
				password = results.getString("password");
				name = results.getString("name");
				phone = results.getString("phone");
				score = results.getString("highest_score");
			}
			User user = User.getUser(id_,password,name,phone,score);
			return user;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void updateScore(String id, String score) {
		try {
			Connection con = getConnection();
			PreparedStatement updateScore = con.prepareStatement("Update member "
					+ "SET highest_score = " +"\""+score+"\""
					+ "WHERE id = "+"\""+id+"\"");
			updateScore.executeUpdate();
			System.out.println("Renew Score");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadMember() {
		try {
			Connection con = getConnection();
			PreparedStatement getMember = con.prepareStatement("Select id, password FROM member");
			ResultSet results = getMember.executeQuery();
			ArrayList<String[]> list = new ArrayList<>();
			while(results.next()) {
				list.add(new String[] {
						results.getString("id"),
						results.getString("password")
				});
			}
			
			String[][] arr = new String[list.size()][2];
			member = list.toArray(arr);
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static String[][] getMember() {
		if(member != null) {
			return member;
		} else {
			return null;
		}
	}
	
	public static void joinMember(String id,String password,String name,String phone) {
		try {
			Connection con = getConnection();
			PreparedStatement joinMember = con.prepareStatement(
					"INSERT INTO member" +
					"(id,password,name,phone,highest_score)" +
					"VALUE" +
					"('"+id+"','"+password+"','"+name+"','"+phone+"','"+"0"+"')");
			joinMember.executeUpdate();
			System.out.println("Welcome !" + id);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void createTable() {
		try {
			Connection con = getConnection();
			PreparedStatement createTable = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS " +
					"member(id varChar(255)," +
					"password varChar(255)," +
					"name varChar(255)," +
					"phone varChar(255)," +
					"highest_score varChar(255)," +
					"PRIMARY KEY(id))");
			createTable.execute();
			System.out.println("Done!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			
		}
	}
	
	public static Connection getConnection() {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/findmine?characterEncoding=UTF-8&serverTimezone=UTC";
			String user = "root";
			String pass = "tel16027!";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,user,pass);
			System.out.println("The connection is successful");
			return con;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}