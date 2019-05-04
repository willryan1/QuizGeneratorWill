package application;
  
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
  
  public List<Scene> questions = new ArrayList<Scene>();
  int numRight;
  int totalQuestions;
  int totalQuestionDataBase = 0;
  int optionsMade;
  
  QuestionDatabase data = new QuestionDatabase();

  @Override
  public void start(Stage primaryStage) {
      try {
        
          
          GridPane root = new GridPane();
          root.setHgap(10);
          root.setVgap(12);
          Scene scene = new Scene(root,400,300);
          scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

          Label header = new Label("Quiz Generator");
          header.setScaleX(1.5);
          header.setScaleY(1.5);

          totalQuestionDataBase = data.numberOfQuestions();
          Label Topic = new Label("Choose a topic:");
          Label num = new Label("Choose number of Questions: " + totalQuestionDataBase);

          ObservableList<String> topics = data.getTopics();
          ComboBox topicsComboBox = new ComboBox(topics);

          ArrayList<String> numOfq = new ArrayList<String>();
          for(int i = 0; i < totalQuestionDataBase; i++){
              numOfq.add("" + i);
          }
          ObservableList<String> options =
          FXCollections.observableArrayList(numOfq);
          ComboBox numberOfQuestions = new ComboBox(options);

          Button generate = new Button("Generate Quiz");
          Button addQuestion = new Button("Add Question");
          Button loadData = new Button("Load Data");

          generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

              if(numberOfQuestions.getValue() != null && topicsComboBox.getValue() != null) {
                primaryStage.close();

                totalQuestions = Integer.parseInt((String)numberOfQuestions.getValue());

                //Json data right here
                
                List<Question> quesitons = data.getQuestions(topicsComboBox.getValue().toString());
                
                for(int i = 0; i < totalQuestions; i++) {
                  GridPane root = new GridPane();
                  root.setHgap(10);
                  root.setVgap(12);
                  Scene scene = new Scene(root,400,300);
                  scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                  
                  //this is where the database will be used in order to retrive the data into create a question screen
                  Question currentNode = quesitons.get(i);
                         
                  String q = currentNode.getQuestion();//json question
                  String[] o = currentNode.getChoices();//json options
                  int c = currentNode.getAnswerPosition();//right answer
                  Image t = null;
                  if(!currentNode.getImage().equals("none")) {
                    t = new Image(currentNode.getImage(), 100, 150, false, false);; //, 100, 150, false, false); use this as the size of the images
                  }
                  questions.add(createQuestion(q,o,root,scene,c,primaryStage,t));
                }

                primaryStage.setScene(questions.get(0));
                primaryStage.show();
                questions.remove(0);
              }
            }
        });

          addQuestion.setOnAction(new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent event) {
                  // TODO Auto-generated method stub
                   primaryStage.close();

                   GridPane root = new GridPane();
                      root.setHgap(10);
                      root.setVgap(12);  
                      Scene scene = new Scene(root,350,350);
                      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

                      //add design here
                      Label title = new Label("Add Questions");
                      title.setScaleX(1.5);
                      title.setScaleY(1.5);
                      Label q = new Label("Questions: ");
                      Label t = new Label("Topic: ");
                      Label o = new Label("Options: ");
                      
                      TextField question = new TextField();
                      TextField topic = new TextField();
                      
                      HBox qu = new HBox();
                      HBox to = new HBox();
                      
                      qu.getChildren().add(q);
                      qu.getChildren().add(question);
                      
                      to.getChildren().add(t);
                      to.getChildren().add(topic);
                      
                      ToggleGroup tg = new ToggleGroup();
                      
                      RadioButton rb = new RadioButton("");
                      rb.setToggleGroup(tg);
                      TextField option = new TextField();
                      optionsMade = 1;
                      
                      HBox optionTab = new HBox();
                      
                      optionTab.getChildren().add(rb);
                      optionTab.getChildren().add(option);
                      
                      VBox radioButtons = new VBox();
                      
                      radioButtons.getChildren().add(o);
                      radioButtons.getChildren().add(optionTab);
                      
                      Button newOption = new Button("Add");
                      
                      newOption.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                          // TODO Auto-generated method stub

                          if(optionsMade <= 4) {
                              RadioButton rb = new RadioButton("");
                              TextField option = new TextField();
                              rb.setToggleGroup(tg);
                            
                              HBox optionTab = new HBox();
                              
                              optionTab.getChildren().add(rb);
                              optionTab.getChildren().add(option);
                              
                              radioButtons.getChildren().add(optionTab);
                              optionsMade++;
                          }
                          
                        }
                        
                      });
                      
                      Button b = new Button("Submit");
                      
                      b.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                          // TODO Auto-generated method stub
                          
                         
                          
                          String questionText = question.getText();
                          String topicText = topic.getText();
                          
                          ArrayList<String> options = new ArrayList<String>();
                          //option that is correct needs to be dded image
                          int position = 0;
                          String rightAnswer = "";
                          
                          for(int i = 0; i < radioButtons.getChildren().size(); i++) {
                            
                            HBox current = (HBox) radioButtons.getChildren().get(i);
                            
                            TextField textField = (TextField)current.getChildren().get(1);
                            options.add(textField.getText());
                            
                            RadioButton currentRadio = (RadioButton)radioButtons.getChildren().get(i);
                            
                            if(currentRadio.isSelected() && tg.getSelectedToggle() != null) {
                              
                              position = i;
                              rightAnswer = textField.getText();
                              
                            }
                            
                          }
                                                    
                          String[] options2 = (String[]) options.toArray();
                           // this is where the backend will go in order to save the text fields and options
                          
                          if(!questionText.equals("") && !topicText.equals("") && options2 != null && !rightAnswer.equals("")) {
                            Question q = new Question("unused", questionText, topicText, options2, position, rightAnswer);
                          
                            data.addQuestion(topicText, q);
                            
                            primaryStage.close();
                            
                          }
                          
                          
                        }
                        
                      });

                      root.add(title, 0, 0);
                      root.add(qu, 0, 1);
                      root.add(to, 0, 2);
                      root.add(radioButtons, 0, 3);
                      root.add(newOption, 0, 4);
                      root.add(b, 0, 5);
                      root.setAlignment(Pos.CENTER);
                      primaryStage.setScene(scene);
                      primaryStage.show();
              }

          });

//          loadData
          loadData.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
              // TODO Auto-generated method stub
                
              FileChooser fileChooser = new FileChooser();
              File selectedFile = fileChooser.showOpenDialog(null);
               
              if (selectedFile != null) {
               //file is selected
                data.loadQuestion(selectedFile);
              }
              
            }
            
          });

          root.add(header, 0, 0);
          root.add(Topic,0 , 1);
          root.add(topicsComboBox, 0, 2);
          root.add(num, 0, 3);
          root.add(numberOfQuestions, 0, 4);
          root.add(addQuestion, 0, 5);
          root.add(loadData, 0, 6);
          root.add(generate, 0 , 7);

          root.setAlignment(Pos.CENTER);
          primaryStage.setTitle("Quiz Generator");
          primaryStage.setScene(scene);
          primaryStage.show();
      } catch(Exception e) {
          e.printStackTrace();
      }
  }

  public static void main(String[] args) {
      launch(args);
  }

  public Scene createQuestion(String question, String[] options, GridPane gp, Scene sc, int correct, Stage primaryStage, Image input) {

      VBox hbButtons = new VBox();
      hbButtons.setSpacing(10.0);
      hbButtons.setAlignment(Pos.CENTER);

      ToggleGroup group = new ToggleGroup();
      RadioButton[] rb = new RadioButton[options.length];

      for(int i = 0; i < options.length; i++) {

          RadioButton rb1 = new RadioButton(options[i]);
          rb1.setToggleGroup(group);
          rb[i] = rb1;
          hbButtons.getChildren().add(rb1);

      }

      Label TopicQuestion = new Label(question);
      TopicQuestion.setScaleX(1.5);
      TopicQuestion.setScaleY(1.5);
      TopicQuestion.setWrapText(true);
      TopicQuestion.setTextAlignment(TextAlignment.JUSTIFY);
      TopicQuestion.setPadding(new Insets(5,0,0,0));

      Button check = new Button("Check");

      check.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {

          if(group.getSelectedToggle() == null) {

            //throws a message since a radio group is not selected

          }else if(check.getText().equals("Check")) {
            for(int i = 0; i <  hbButtons.getChildren().size(); i++) {

                if( rb[i].getText().equals(options[correct]) && rb[i].isSelected()) {

                    rb[i].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    numRight++;

                }else if(!rb[i].getText().equals(options[correct]) && rb[i].isSelected()) {

                  rb[i].setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

                }

            }

            check.setText("Next");
            for (Toggle t : group.getToggles()) {
              if (t instanceof RadioButton) {
                  ((RadioButton) t).setDisable(true);
              }
          }
        }else if(check.getText().equals("Next")) {

          if(questions.isEmpty()) {

              //display score scene
            GridPane root = new GridPane();
            root.setHgap(10);
            root.setVgap(12);
            root.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root,250,250);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Label score = new Label("Your Score:");
            score.setScaleX(1.5);
            score.setScaleY(1.5);
            Label score2 = new Label(numRight + " / " + totalQuestions);
            score2.setScaleX(1.5);
            score2.setScaleY(1.5);
            
            Button saveToFile = new Button("Save to File");
            Button exitWihoutSaving = new Button("Exit");
            
            exitWihoutSaving.setOnAction(new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                primaryStage.close();
              }
              
            });

            saveToFile.setOnAction(new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                
                //save to json File
                /* example
                JSONArray employeeList = new JSONArray();
                employeeList.add(employeeObject);
                employeeList.add(employeeObject2);
                 
                //Write JSON file
                try (FileWriter file = new FileWriter("qyestion.json")) {
         
                    file.write(employeeList.toJSONString());
                    file.flush();
         
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                File n = new File("");
                data.saveQuestionstoJSON(n);
                FileWriter fw;
                try {
                  fw = new FileWriter(n);
                  fw.flush();
                } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                
                
              }
              
            });
           
            
            
            root.add(score, 0, 0);
            root.add(score2, 0, 1);
            root.add(saveToFile, 0, 2);
            root.add(exitWihoutSaving, 0, 3);
            primaryStage.setScene(scene);
            primaryStage.show();


          }else {

            primaryStage.setScene(questions.get(0));
            primaryStage.show();
            questions.remove(0);
          }

        }
        }
    });

      if(input != null) {
        Image image = input;
        ImageView imageView = new ImageView(image);
        gp.add(imageView, 10, 3);
      }
      
      
      gp.add(TopicQuestion, 5, 0);
      gp.add(hbButtons, 2, 3);
      gp.add(check, 5, 5);
      
      return sc;

  }
  
}

