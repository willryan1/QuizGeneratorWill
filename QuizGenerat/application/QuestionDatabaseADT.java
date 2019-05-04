package application;

import java.io.File;
import java.util.List;

import javafx.collections.ObservableList;

public interface QuestionDatabaseADT {

	public  void addQuestion(String string, Question question);

	public void saveQuestionstoJSON(File file);

	public List<Question> getQuestions(String string);

	public void loadQuestion(File file);

	public ObservableList<String> getTopics();

}
