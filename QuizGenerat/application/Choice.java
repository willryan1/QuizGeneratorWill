package application;

public class Choice {
	private boolean isCorrect;
	private String choice;

	public Choice(String choice, boolean isCorrect){
		this.choice = choice;
		this.isCorrect = isCorrect;
	}

	public boolean isCorrect(){
		return isCorrect;
	}

	public String getChoice(){
		return choice;
	}

}
