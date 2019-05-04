package application;

import java.util.List;

public class Question extends QuestionDatabase{
	private String metadata;
	private String question;
	private String topic;
	private String image;
	private String[] choices;
	private String answer;
	private int answerPosition;

	public Question(String metadata, String question, String topic, String image, String[] choices, String answer, int answerPosition){
		this.metadata = metadata;
		this.question = question;
		this.topic = topic;
		this.image = image;
		this.choices = choices;
		this.answer = answer;
		this.answerPosition = answerPosition;
	}
	
	public Question(String metadata, String question, String topic, String[] choices, int answerPosition, String answer){
		this.metadata = metadata;
		this.question = question;
		this.topic = topic;
		this.choices = choices;
		this.answer = answer;
		this.answerPosition = answerPosition;
		this.image = "none";
	}

	public int getAnswerPosition() {
		return answerPosition;
	}

	public void setAnswerPosition(int answerPosition) {
		this.answerPosition = answerPosition;
	}

	public String getMetadata(){
		return metadata;
	}

	public String getQuestion(){
		return question;
	}

	public String getTopic(){
		return topic;
	}
	public String getImage(){
		return image;
	}

	public String[] getChoices(){
		return choices;
	}

	public String getAnswer(){
		return answer;
	}

	public boolean isCorrect(String ans){
		if(answer.equals(ans)){
			return true;
		}
		return false;
	}

}
