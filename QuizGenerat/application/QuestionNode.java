package application;

import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class QuestionNode extends Main{
	private ToggleGroup choices;
	private VBox node;



	private QuestionNode(Question question){
		
	}

	private VBox getNode(){
		return node;
	}

	private ToggleGroup getChoices(){
		return choices;
	}

}
