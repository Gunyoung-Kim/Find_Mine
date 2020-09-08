
public class User {
	private static User user;
	private String id;
	private String password;
	private String name;
	private String phonenum;
	private String highestscore;
	
	private User(String id, String password,String name,String phonenum,String highestscore) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.phonenum = phonenum;
		this.highestscore = highestscore;
	}
	
	public static User getUser(String id, String password,String name,String phonenum,String highestscore) {
		if(user == null)
			user = new User(id,password,name,phonenum,highestscore);
		
		return user;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getPhonenum() {
		return this.phonenum;
	}
	
	public String getScore() {
		return this.highestscore;
	}
	
	public boolean renewScore(int score) {
		if(score > Integer.parseInt(this.highestscore)) {
			this.highestscore = new String(score+"");
			Member.updateScore(this.id, this.highestscore);
			return true;
		} else {
			return false;
		}
	}
	
}
