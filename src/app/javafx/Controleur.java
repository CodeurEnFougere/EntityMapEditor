package app.javafx;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import app.EntityMapEditor;
import app.api.Coordonnees;
import app.api.Entity;
import app.api.EntityLiving;
import app.api.TextureLoader;
import app.api.TileEntity;
import app.api.TileEntityTP;
import app.api.World;
import app.utils.Loader;
import app.utils.Saver;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class Controleur implements Initializable{

	Stage stage = EntityMapEditor.stage;

	@FXML
	private TabPane tabPane;

	@FXML
	private SplitPane worldSPane;

	@FXML
	private Pane world1;
	@FXML
	private TilePane backgroundWorld1;
	@FXML
	private TilePane solidWorld1;
	@FXML
	private TilePane topWorld1;
	@FXML
	private Pane entityWorld1;

	@FXML
	private Pane world2;
	@FXML
	private TilePane backgroundWorld2;
	@FXML
	private TilePane solidWorld2;
	@FXML
	private TilePane topWorld2;
	@FXML
	private Pane entityWorld2;

	@FXML
	private Pane toolBar;

	@FXML
	private TextArea world1Text;
	@FXML
	private TextArea world2Text;

	@FXML
	private CheckBox world1Save;
	@FXML
	private CheckBox world2Save;

	@FXML
	private ImageView grabImg;
	@FXML
	private ImageView selectImg;

	@FXML
	private TextField tab1;

	@FXML
	private TextField tab2;

	@FXML
	private TextField tab3;

	@FXML
	private TextField tab4;

	@FXML
	private TextField tab5;

	@FXML
	private TextField tab6;

	@FXML
	private Label label1;

	@FXML
	private Label label2;

	@FXML
	private Label label3;

	@FXML
	private Label label4;

	@FXML
	private Label label5;

	@FXML
	private Label label6;

	ImageView selectedTool;
	ImageView selectedEntityType;
	ImageView selectTile;
	boolean selectTileInWorld1=false;

	Map<Integer,Image> dicoImageTileTextureMap = new HashMap<>();

	Entity selectedEntity;
	boolean selectedEntityOnWorld1;
	StringProperty selectedEntityTextureId;
	ImageView selectedEntityTexture;

	boolean mousePress=false;
	double paneX=0;
	double paneY=0;
	double mousePressX=0;
	double mousePressY=0;

	int tool=0;
	int entityType=0;
	int idImageView=0;
	Pane tempPane;

	boolean isEvent=false;

	String mapNameTp;
	Entity newEntity;
	Coordonnees tpCoordonnees;

	ArrayList<ImageView> entitysWorld1 ;
	ArrayList<ImageView> entitysWorld2 ;

	Alert alert = new Alert(AlertType.INFORMATION);

	@FXML
	void saveEntity(ActionEvent event) {
		if(selectedEntityTexture!=null)
		try {
			if(selectedEntityOnWorld1) {
				Loader.world1.getEntity().remove(selectedEntity);
				entitysWorld1.remove(selectedEntityTexture);
				entityWorld1.getChildren().remove(selectedEntityTexture);
				ImageView img = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP.png").toURI().toURL()), null));
				img.relocate(Double.parseDouble(tab1.getText())*32, Double.parseDouble(tab2.getText())*32);
				img.setId(""+idImageView);
				idImageView++;
				entitysWorld1.add(img);
				entityWorld1.getChildren().add(img);
				if(selectedEntity instanceof TileEntityTP) {
					Loader.world1.getEntity().add(new TileEntityTP(1, new Coordonnees(Double.parseDouble(tab1.getText()), Double.parseDouble(tab2.getText())), true, tab3.getText(), new Coordonnees(Double.parseDouble(tab4.getText()), Double.parseDouble(tab5.getText()))));	
				}
				
			}else {
				Loader.world2.getEntity().remove(selectedEntity);
				entitysWorld2.remove(selectedEntityTexture);
				entityWorld2.getChildren().remove(selectedEntityTexture);
				ImageView img = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP.png").toURI().toURL()), null));
				img.relocate(Double.parseDouble(tab1.getText())*32, Double.parseDouble(tab2.getText())*32);
				img.setId(""+idImageView);
				idImageView++;
				entitysWorld2.add(img);
				entityWorld2.getChildren().add(img);
				if(selectedEntity instanceof TileEntityTP) {
					Loader.world2.getEntity().add(new TileEntityTP(1, new Coordonnees(Double.parseDouble(tab1.getText()), Double.parseDouble(tab2.getText())), true, tab3.getText(), new Coordonnees(Double.parseDouble(tab4.getText()), Double.parseDouble(tab5.getText()))));	
				}
			}
			selectedEntityTexture=null;
		}catch(Exception e) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Un des champs");
			alert.show();
			e.printStackTrace();
		}
	}

	@FXML
	void deleteEntity(ActionEvent event) {
		if(selectedEntity!=null) {
			if(selectedEntity instanceof TileEntityTP) {
				if(selectedEntityOnWorld1) {
					Loader.world1.getEntity().remove(selectedEntity);
					entitysWorld1.remove(selectedEntityTexture);
					entityWorld1.getChildren().remove(selectedEntityTexture);

				}else {
					Loader.world2.getEntity().remove(selectedEntity);
					entitysWorld2.remove(selectedEntityTexture);
					entityWorld2.getChildren().remove(selectedEntityTexture);
				}
			}
			tab1.clear();
			tab2.clear();
			tab3.clear();
			tab4.clear();
			tab5.clear();
			tab6.clear();
		}
	}

	@FXML
	void SaveEntityButton(ActionEvent event) {	
		try {
			if(world1Save.isSelected() && Loader.world1!=null) {
				Saver.saveEntity(Loader.world1);
				alert.setContentText("Succefully saving entitys");
			}

			if(world2Save.isSelected() && Loader.world2!=null) {
				Saver.saveEntity(Loader.world2);
				alert.setContentText("Succefully saving entitys");
			}

		}catch(Exception e) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Fail to save entitys : "+e);
			e.printStackTrace();
		}

		if(world1Save.isSelected() && Loader.world1!=null || world2Save.isSelected() && Loader.world2!=null ) { 	

		}else {
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("A world need to be selected or loaded to save it's entitys");
		}
		alert.show();
	}


	@FXML
	void loadWorld1Button(ActionEvent event) {
		if(!world1Text.getText().equals("")) {
			loadWorld(world1Text.getText(), true);
		}else {
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("A name is needed to load");
			alert.show();
		}
	}

	@FXML
	void loadWorld2Button(ActionEvent event) {
		if(!world2Text.getText().equals("")) {
			loadWorld(world2Text.getText(), false);
		}else {
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("A name is needed to load");
			alert.show();
		}
	}

	private void mouseEventMove(MouseEvent me,Pane pane) {
		if(me.getEventType()==MouseEvent.MOUSE_MOVED && tool>0) {
			if(pane.getId()==world1.getId()){
				if(!selectTileInWorld1) {
					world1.getChildren().add(selectTile);
					selectTileInWorld1=true;
					world2.getChildren().remove(selectTile);
				}

			}else {
				if(selectTileInWorld1) {
					world2.getChildren().add(selectTile);
					world1.getChildren().remove(selectTile);
					selectTileInWorld1=false;
				}
			}
			selectTile.relocate(((int)(me.getX()/32))*32-1, ((int)(me.getY()/32))*32-1);
		}
	}

	private void mouseEvent(MouseEvent me,Pane pane) {


		if(tool==0) {
			if(me.getEventType()==MouseEvent.MOUSE_PRESSED) {
				if(!mousePress) {
					mousePress=true;
					mousePressX=me.getX();
					mousePressY=me.getY();
				}
			}

			if(me.getEventType()==MouseEvent.MOUSE_RELEASED) {
				mousePress=false;
			}

			if(me.getEventType()==MouseEvent.MOUSE_DRAGGED) {
				if(mousePress) {
					pane.relocate(me.getX()-mousePressX+pane.getLayoutX(), me.getY()-mousePressY+pane.getLayoutY());
				}
			}
		}
		if(me.getEventType()==MouseEvent.MOUSE_PRESSED) {
			if(tool==1 && !isEvent) {
				tempPane=pane;
				if(entityType==0 && Loader.world1!=null && Loader.world2!=null) {
					tpCoordonnees = new Coordonnees((int)me.getX()/32, (int)me.getY()/32);
					isEvent=true;
					System.out.println("Event true");
					if(pane.getId()==world1.getId())
						mapNameTp=Loader.world2.getName();
					else 
						mapNameTp=Loader.world1.getName();
				}else {
					alert.setAlertType(AlertType.WARNING);
					alert.setContentText("All world need to be loaded");
					alert.show();
				}

			}else if(tool==1 && isEvent && pane!=tempPane) {
				isEvent=false;
				System.out.println("Event false");
				try {
					newEntity = new TileEntityTP(1, tpCoordonnees, true, mapNameTp, new Coordonnees((int)me.getX()/32, (int)me.getY()/32));
					System.out.println("new entity :"+newEntity);		

					ImageView img;

					img = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP.png").toURI().toURL()), null));
					img.relocate(newEntity.getX()*32, newEntity.getY()*32);
					img.setId(""+idImageView);
					idImageView++;

					if(pane.getId()==world2.getId()) {
						world1.getChildren().add(img);
						entitysWorld1.add(img);
						Loader.world1.getEntity().add(newEntity);
					}else if(pane.getId()==world1.getId()){
						world2.getChildren().add(img);
						entitysWorld2.add(img);
						Loader.world2.getEntity().add(newEntity);
					}
				} catch (IOException | URISyntaxException e) {e.printStackTrace();}


			}else if(tool==2) {
				if(pane.getId()==world1.getId()) {
					for(ImageView block:entitysWorld1) {
						if((int)block.getLayoutX()/32==(int)me.getX()/32 && (int)block.getLayoutY()/32==(int)me.getY()/32 ) {
							selectedEntityOnWorld1=true;
							selectedEntity = Loader.world1.getEntity((int)(me.getX()/32),(int)(me.getY()/32));
							selectedEntityTextureId.setValue(""+block.getId());
							selectedEntityTexture=block;
							System.out.println(selectedEntity);
							System.out.println(block.getId());

						}
					}

				} else {
					for(ImageView block:entitysWorld2) {
						if((int)block.getLayoutX()/32==(int)me.getX()/32 && (int)block.getLayoutY()/32==(int)me.getY()/32 ) {
							selectedEntityOnWorld1=false;
							selectedEntity = Loader.world2.getEntity((int)(me.getX()/32),(int)(me.getY()/32));
							selectedEntityTextureId.setValue(""+block.getId());
							selectedEntityTexture=block;
							System.out.println(selectedEntity);
							System.out.println(block.getId());



						}			
					}		
				}	
			}		
		}


	}

	private void selectEntity() {
		if(selectedEntity instanceof TileEntityTP) {
			TileEntityTP entity = (TileEntityTP)selectedEntity;
			tab1.setText(""+entity.getX());
			tab2.setText(""+entity.getY());
			tab3.setText(entity.getTPmapName());
			tab4.setText(""+entity.getTPCoordonnees().getX());
			tab5.setText(""+entity.getTPCoordonnees().getY());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
		textureLoading();
		eventHandler();


		//TODO

	}

	private void loadWorld(String worldName, boolean isWorld1) {

		try {
			if(isWorld1) {
				entityWorld1.getChildren().clear();
				Loader.loadWorld(worldName, true);	
				resize(world1, Loader.world1.getWidth()*32, Loader.world1.getHeight()*32);
				resize(backgroundWorld1, Loader.world1.getWidth()*32, Loader.world1.getHeight()*32);
				resize(solidWorld1, Loader.world1.getWidth()*32, Loader.world1.getHeight()*32);
				resize(topWorld1, Loader.world1.getWidth()*32, Loader.world1.getHeight()*32);
				resize(entityWorld1, Loader.world1.getWidth()*32, Loader.world1.getHeight()*32);
				printCalqueTile(backgroundWorld1,solidWorld1,topWorld1,Loader.world1);
				loadEntitys(entitysWorld1, Loader.world1.getEntity());
				printCalqueEntitys(entitysWorld1, entityWorld1);

			}else {
				entityWorld2.getChildren().clear();
				Loader.loadWorld(worldName, false);	
				resize(world2, Loader.world2.getWidth()*32, Loader.world2.getHeight()*32);
				resize(backgroundWorld2, Loader.world2.getWidth()*32, Loader.world2.getHeight()*32);
				resize(solidWorld2, Loader.world2.getWidth()*32, Loader.world2.getHeight()*32);
				resize(topWorld2, Loader.world2.getWidth()*32, Loader.world2.getHeight()*32);
				resize(entityWorld2, Loader.world2.getWidth()*32, Loader.world2.getHeight()*32);
				printCalqueTile(backgroundWorld2,solidWorld2,topWorld2,Loader.world2);
				loadEntitys(entitysWorld2, Loader.world2.getEntity());
				printCalqueEntitys(entitysWorld2, entityWorld2);

			}
			alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Succefuly loading \""+worldName+"\"");

		}catch(Exception e) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Failed to load \""+worldName+"\" : "+e);
			e.printStackTrace();
		}
		alert.show();
	}

	public void loadEntitys(ArrayList<ImageView> entitysImage, ArrayList<Entity> entitys) {

		for(Entity entity:entitys) {
			ImageView entityImage;

			if(entity instanceof TileEntityTP) {
				try {
					entityImage = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP.png").toURI().toURL()), null));
					entityImage.relocate(entity.getX()*32, entity.getY()*32);
					entitysImage.add(entityImage);
				}catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}

			}else if(entity instanceof EntityLiving) {

			}else if(entity instanceof TileEntity) {

			}
		}		
	}

	private void init() {
		world1.setStyle("-fx-background-color: #FF0000;");
		world2.setStyle("-fx-background-color: #0000FF;");
		toolBar.setStyle("-fx-background-color: #C4C4C4;");

		resize(toolBar, 200,200);
		resize(world1, 32,32);
		resize(world2, 32,32);

		selectedEntityTextureId=new SimpleStringProperty();
		entitysWorld1=new ArrayList<ImageView>();
		entitysWorld2=new ArrayList<ImageView>();
	}

	private void eventHandler() {

		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
			try {
				tabPane.setMinWidth(stage.getWidth());
				tabPane.setMinHeight(stage.getHeight());
				worldSPane.setMinWidth(stage.getWidth()-200);
				worldSPane.setMinHeight(stage.getHeight());
				toolBar.relocate(stage.getWidth()-200, 0);
				toolBar.setMinHeight(stage.getHeight());

			}catch(Exception e) {
				e.printStackTrace();
			}

		};

		stage.widthProperty().addListener(stageSizeListener);
		stage.heightProperty().addListener(stageSizeListener); 

		world1.setOnMouseMoved(new EventHandler<MouseEvent>() {public void handle(MouseEvent me) {mouseEventMove(me,world1);}});
		world2.setOnMouseMoved(new EventHandler<MouseEvent>() {public void handle(MouseEvent me) {mouseEventMove(me,world2);}});

		world1.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent me) {mouseEvent(me,world1);}});
		world2.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent me) {mouseEvent(me,world2);}});
		world1.setOnMouseReleased(new EventHandler<MouseEvent>() {public void handle(MouseEvent me) {mouseEvent(me,world1);}});
		world2.setOnMouseReleased(new EventHandler<MouseEvent>() {public void handle(MouseEvent me) {mouseEvent(me,world2);}});
		world1.setOnMouseDragged(new EventHandler<MouseEvent>() {public void handle(MouseEvent me) {mouseEvent(me,world1);}});
		world2.setOnMouseDragged(new EventHandler<MouseEvent>() {public void handle(MouseEvent me) {mouseEvent(me,world2);}});

		selectedEntityTextureId.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if(!entitysWorld1.isEmpty())
						for(ImageView img:entitysWorld1) {

							if(oldValue!=null && img.getId().equals(oldValue)) 
								img.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP.png").toURI().toURL()), null));

							else if(img.getId().equals(newValue)) 
								img.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP2.png").toURI().toURL()), null));

						}
					if(!entitysWorld2.isEmpty())
						for(ImageView img:entitysWorld2) {

							if(oldValue!=null && img.getId().equals(oldValue)) 
								img.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP.png").toURI().toURL()), null));

							else if(img.getId().equals(newValue)) 
								img.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP2.png").toURI().toURL()), null));


						}
					selectEntity();
				}catch (IOException | URISyntaxException e) {e.printStackTrace();}
			}

		});

		try {
			//Tools
			ImageView grabImg = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/scroll.png").toURI().toURL()), null));
			grabImg.relocate(10, 30);
			grabImg.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent event) {tool=0;selectedTool.relocate(9, 29);}});
			toolBar.getChildren().add(grabImg);

			ImageView placeImg = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/place.png").toURI().toURL()), null));
			placeImg.relocate(52, 30);
			placeImg.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent event) {tool=1;selectedTool.relocate(51, 29);}});
			toolBar.getChildren().add(placeImg);

			ImageView selectImg = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/modify.png").toURI().toURL()), null));
			selectImg.relocate(94, 30);
			selectImg.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent event) {tool=2;selectedTool.relocate(93, 29);}});
			toolBar.getChildren().add(selectImg);

			//Entity Placer
			ImageView entityTP = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP.png").toURI().toURL()), null));
			entityTP.relocate(10, 89);
			entityTP.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent event) {entityType=0;selectedEntityType.relocate(9, 88);}});
			toolBar.getChildren().add(entityTP);

			ImageView entityLiving = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityLiving.png").toURI().toURL()), null));
			entityLiving.relocate(52, 89);
			entityLiving.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent event) {entityType=1;selectedEntityType.relocate(51, 88);}});
			toolBar.getChildren().add(entityLiving);

			ImageView tileEntity = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/tileEntity.png").toURI().toURL()), null));
			tileEntity.relocate(94, 89);
			tileEntity.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent event) {entityType=2;selectedEntityType.relocate(93, 88);}});
			toolBar.getChildren().add(tileEntity);


			selectedTool = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/select.png").toURI().toURL()), null));
			selectedTool.relocate(9, 29);
			toolBar.getChildren().add(selectedTool);

			selectedEntityType = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/select.png").toURI().toURL()), null));
			toolBar.getChildren().add(selectedEntityType);
			selectedEntityType.relocate(9, 88);		


		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

	}

	public void loadMap(boolean isWorld1) {
		if(isWorld1) {
			world1.setMinWidth(Loader.world1.getWidth());
			world1.setMinHeight(Loader.world1.getHeight());

		}else {
			world2.setMinWidth(Loader.world2.getWidth());
			world2.setMinHeight(Loader.world2.getHeight());

		}	
	}

	private void textureLoading() {
		LoadDicoMap(dicoImageTileTextureMap,32,32,16,16,"TileTextureMap");
		try {
			selectTile= new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/select.png").toURI().toURL()), null));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void LoadDicoMap(Map<Integer,Image> dico,int imageWidthPixels, int imageHeightPixels, int imageWidth, int imageHeight, String textureMapName) {
		for(int x = 0; x < imageWidth*imageHeight; x++) {
			dico.put(x + 1,SwingFXUtils.toFXImage(TextureLoader.getTextureMapImage(textureMapName,imageWidthPixels,imageHeightPixels,imageWidth,imageHeight,x).getTexture(), null));
		}
	}

	private void printCalqueEntitys(ArrayList<ImageView> entitysImage, Pane pane) {
		pane.getChildren().clear();
		for(ImageView entityImage:entitysImage) {
			entityImage.setId(""+idImageView);
			pane.getChildren().add(entityImage);
			idImageView++;
		}
	}

	private void printCalqueTile(TilePane pane, TilePane paneTile, TilePane paneTop, World world) {

		for(int y=0;y<world.getHeight();y++) {
			for(int x=0;x<world.getWidth();x++) {

				ImageView tile = new ImageView(dicoImageTileTextureMap.get(world.getTileTerrain(y, x).getId()));	
				tile.setX(x*32);
				tile.setY(y*32);
				pane.getChildren().add(tile);

				tile = new ImageView(dicoImageTileTextureMap.get(world.getTile(y, x).getId()));
				tile.setX(x*32);
				tile.setY(y*32);
				paneTile.getChildren().add(tile);

				tile = new ImageView(dicoImageTileTextureMap.get(world.getTileTop(y, x).getId()));
				tile.setX(x*32);
				tile.setY(y*32);
				paneTop.getChildren().add(tile);

			}
		}
	}

	private void resize(Pane pane, double x, double y) {
		pane.setMinWidth(x);
		pane.setMaxWidth(x);
		pane.setMinHeight(y);
		pane.setMaxHeight(y);
	}
}
