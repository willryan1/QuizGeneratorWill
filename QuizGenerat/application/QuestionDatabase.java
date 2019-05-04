package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QuestionDatabase implements QuestionDatabaseADT {
	private Map<String, List<Question>> topics;

	public QuestionDatabase() {
		this.topics = new HashMap<String, List<Question>>();
	}

	@Override
	public void addQuestion(String topic, Question question) {
		if(topics.get(topic) == null) {
			List<Question> qlist = new ArrayList<Question>();
			qlist.add(question);
			topics.put(topic, qlist);
		}else {
			List<Question> qlist = topics.get(topic);
			qlist.add(question);
			topics.put(topic, qlist);
		}
	}

	@Override
	public void saveQuestionstoJSON(File file) {
		ObservableList<String> topic = getTopics();
		ArrayList<String> alltopics = new ArrayList<String>(topic);
		JSONObject obj = new JSONObject();
		JSONArray jarry = new JSONArray();
		for(int i = 0; i < alltopics.size(); i++) {
			List<Question> qlist = topics.get(alltopics.get(i));
			for(int j = 0; j < qlist.size(); j++) {
				Question q = qlist.get(j);
				JSONObject jobj = new JSONObject();
				jobj.put("meta-data", q.getMetadata());
				jobj.put("questionText", q.getQuestion());
				jobj.put("topic", q.getTopic());
				jobj.put("image", q.getImage());
				JSONArray rchoices = new JSONArray();
				for(int a = 0; a < q.getChoices().length; a++) {
					JSONObject choice = new JSONObject();
					if(q.getChoices()[a].equals(q.getAnswer())) {
						choice.put("isCorrect", "T");
					}else {
						choice.put("isCorrect", "F");
					}choice.put("choice", q.getChoices()[a]);
					rchoices.add(choice);
				}jobj.put("choiceArray", rchoices);
				jarry.add(jobj);
			}
		}obj.put("questionArray", jarry);
		try(FileWriter f = new FileWriter(file)){
			f.write(obj.toJSONString());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Question> getQuestions(String topic) {
		List<Question> q = topics.get(topic);
		return q;
	}

	@Override
	public void loadQuestion(File file) {
		JSONParser parser = new JSONParser();
		try {
			FileReader fr = new FileReader(file);
			Object obj = parser.parse(fr);
			JSONObject jobj = (JSONObject) obj;
			JSONArray questionArray = (JSONArray)jobj.get("questionArray");
			for(Object question: questionArray) {
				JSONObject nobj = (JSONObject) question;
				String metadata = (String)nobj.get("meta-data");
				String qtext = (String)nobj.get("questionText");
				String topic = (String)nobj.get("topic");
				String image = (String)nobj.get("image");
				JSONArray choiceArray = (JSONArray) nobj.get("choiceArray");
				ArrayList<String> carry = new ArrayList<String>();
				ArrayList<String> carrya = new ArrayList<String>();
				String[] choicesArray = new String[choiceArray.size()];
				for(Object choice: choiceArray) {
					JSONObject cobj = (JSONObject) choice;
					carry.add((String)cobj.get("isCorrect"));
					carrya.add((String)cobj.get("choice"));
				}//choicesArray = (String[]) carry.toArray();
				String answer = "";
				int answerPosition = 0;
				for(int i = 0; i < carry.size(); i++) {
					choicesArray[i] = carrya.get(i);
					if(carry.get(i).equals("T")) {
						answer = carrya.get(i);
						answerPosition = i;
					}
				}
				Question q = new Question(metadata,qtext,topic,image,choicesArray,answer,answerPosition);
				if(topics.get(topic) == null) {
					List<Question> qlist = new ArrayList<Question>();
					qlist.add(q);
					topics.put(topic, qlist);
				}else {
					List<Question> qlist = topics.get(topic);
					qlist.add(q);
					topics.put(topic, qlist);
				}
			}
		}catch(FileNotFoundException oe) {
			oe.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public ObservableList<String> getTopics() {
		List<String> list = new ArrayList<String>();
		for(String key: topics.keySet()) {
			list.add(key);
		}ObservableList<String> oblist = FXCollections.observableList(list);
		return oblist;
	}
	
	public int numberOfQuestions() {
		ObservableList<String> topics = getTopics();
		ArrayList<String> t = new ArrayList<String>(topics);
		int amount = 0;
		for(int i = 0; i < t.size(); i++) {
			List<Question> q = getQuestions(t.get(i));
			amount += q.size();
		}
		return amount;
	}

}
