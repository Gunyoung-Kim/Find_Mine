
public class Score {
	private int score;
	private final static int DEFAULT_INCREASE = 10;
	
	public Score() {
		this.score = 0;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getStringScore() {
		return "    "+score;
	}
	
	public void increaseScore(long currentTime) {
		int increase;
		
		switch((int)currentTime/60) {
		case 0:
			increase = DEFAULT_INCREASE;
			break;
		case 1:
			increase = DEFAULT_INCREASE-1;
			break;
		case 2:
			increase = DEFAULT_INCREASE-2;
			break;
		case 3:
			increase = DEFAULT_INCREASE-3;
			break;
		case 4:
			increase = DEFAULT_INCREASE-4;
			break;
		default:
			increase = DEFAULT_INCREASE-5;
			break;
		}
		this.score += increase;
	}
}
