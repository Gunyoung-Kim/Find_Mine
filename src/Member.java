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
			if(member[i][0].equals(id))
				return i;
		}
		return -1;
	}
	
	public static String[][] getMember() {
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
			return member;
		} catch(Exception e) {
			System.out.println(e);
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