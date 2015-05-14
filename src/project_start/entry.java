package project_start;

import java.net.UnknownHostException;





import java.security.CryptoPrimitive;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;






import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class entry extends Application{

	public void init() throws Exception{
		
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Mongo mongo=new MongoClient("localhost", 27017);
		DB userDB= mongo.getDB("fyp1");

		
		
		GridPane myGrid = new GridPane();
		TextField passTF = new TextField("Enter Password");
		Label passLbl = new Label("Password : ");
		TextField userTF = new TextField("Enter Username");
		Label userLbl = new Label("Username : ");
		TextField sizeLimitTF = new TextField("Enter The Size Max :");
		Label sizeLimitLbl = new Label("Size (in %) :");
		Button accessBTN=new Button("Access File System");
		Button addUserBTN=new Button("Add User");
		
		
		myGrid.setConstraints(userLbl,1,1);
		myGrid.getChildren().add(userLbl);
		myGrid.setConstraints(userTF,2,1);
		myGrid.getChildren().add(userTF);
		myGrid.setConstraints(passLbl,1,2);
		myGrid.getChildren().add(passLbl);
		myGrid.setConstraints(passTF,2,2);
		myGrid.getChildren().add(passTF);
		myGrid.setConstraints(sizeLimitLbl,1,3);
		myGrid.getChildren().add(sizeLimitLbl);
		myGrid.setConstraints(sizeLimitTF,2,3);
		myGrid.getChildren().add(sizeLimitTF);
		myGrid.setConstraints(accessBTN,2,4);
		myGrid.getChildren().add(accessBTN);
		myGrid.setConstraints(addUserBTN,3,4);
		myGrid.getChildren().add(addUserBTN);

		
		
		// Adding Click Event So That When User Clicks The TextField, It Get Empty
		userTF.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			userTF.setText("");
		});
		passTF.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			passTF.setText("");
		});
		sizeLimitTF.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			sizeLimitTF.setText("");
		});
		
		
		// Adding Click Event On Add User Button
		addUserBTN.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
		
			String passwordEntered=passTF.getText().trim();
			String userEntered=userTF.getText().trim();
			float sizeLimitValue=Integer.parseInt(sizeLimitTF.getText());
			
			
			System.out.println(passwordEntered);
			System.out.println(userEntered);
			System.out.println(sizeLimitValue);
			
			DBCollection userColl=userDB.getCollection("userCollection");

			BasicDBObject userData=new BasicDBObject();
			userData.put("username", userEntered);
			userData.put("password", passwordEntered.toString());
			userData.put("size Limit", sizeLimitValue);
			
			
			
			BasicDBObject retrievedData=new BasicDBObject();
			retrievedData.put("username", userEntered);
			
			DBCursor cursor= userColl.find(retrievedData);
			Boolean count=false;
			while(cursor.hasNext()){
				count=true;
				System.out.println(cursor.getCollection().toString());
				if(count){
					System.out.println("user Exist");
					break;
				}
			}
			if(!count){
				userColl.insert(userData);
				System.out.println("data inserted");
			}
		
			cursor.close();
			
		});
		
		//Adding Drop Shadow Effect on Buttons
		DropShadow dropShadow=new DropShadow();
		addUserBTN.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
			addUserBTN.setEffect(dropShadow);
		});
		addUserBTN.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addUserBTN.setEffect(null);
		});

		accessBTN.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
			accessBTN.setEffect(dropShadow);
		});
		accessBTN.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			accessBTN.setEffect(null);
		});
		accessBTN.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			String passwordEntered=passTF.getText().trim();
			String userEntered=userTF.getText().trim();
			
			System.out.println(passwordEntered);
			System.out.println(userEntered);
			
			DBCollection userColl=userDB.getCollection("userCollection");
			
			BasicDBObject checkData=new BasicDBObject();
			checkData.put("username",userEntered);
			checkData.put("password",passwordEntered);
			
			DBCursor myCursor= userColl.find(checkData);
			Boolean userExist=false;
			
			while(myCursor.hasNext()){
				userExist=true;
				if(userExist){
				System.out.println(myCursor.getCollection().toString());
				System.out.println("user entered");
				break;
				}
			}
			
			
			
		});
		
		
		
		
		//Setting Scene
		Scene firstScene=new Scene(myGrid, 400,400);
		primaryStage.setScene(firstScene);
		primaryStage.setTitle("File Hierarchy");
		primaryStage.show();
		
	}

}

