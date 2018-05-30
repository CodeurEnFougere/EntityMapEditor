package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EntityMapEditor extends Application {

	public static Stage stage;
	
	public static Class<? extends EntityMapEditor> ressource;
	
	public static String mainPath="";
	
	public static void main(String[] args) {
		String[] path = EntityMapEditor.class.getClassLoader().getResource("").getPath().split("/");
		boolean isEclipseLaunch=path[path.length-1].equals("bin")?true:false;
		for(int azerty=0;azerty<path.length-(isEclipseLaunch?1:0);azerty++) {
			if(azerty!=0)
				mainPath+="/"+path[azerty];
			else
				mainPath+=path[azerty];
		}
		System.out.println(mainPath);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage){
		try {
			stage=primaryStage;
			ressource=getClass();
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/app/javafx/EntityMapEditorVue.fxml"))));
			stage.setTitle("Entity Map Editor");
			stage.setMinWidth(916);
			stage.setMinHeight(638);
			stage.show();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
