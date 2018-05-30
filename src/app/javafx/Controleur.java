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
import app.utils.Saver;
import app.utils.TextureLoader;
import app.utils.World;
import game.modele.entity.Entity;
import game.modele.entity.EntityFactory;
import game.modele.entity.EntityTP;
import game.modele.entity.living.EntityLiving;
import game.modele.entity.tileEntity.EntityLight;
import game.modele.entity.tileEntity.TileEntity;
import game.modele.utils.Coordonnees;
import game.modele.utils.Direction;
import game.modele.world.WorldData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
	private TextField tab7;
	@FXML
	private TextField tab8;

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
	@FXML
	private Label label7;
	@FXML
	private Label label8;

	ImageView selectedTool;
	ImageView selectedEntityType;
	ImageView selectTile;
	
	boolean selectTileInWorld1=false;

	Map<Integer,Image> dicoImageTileTextureMap = new HashMap<>();

	Entity selectedEntity;
	Entity selectedEntityLast;
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
			ImageView img = new ImageView(selectedEntityTexture.getImage());
			
			if(selectedEntityOnWorld1) {
				World.world1.getEntity().remove(selectedEntity);
				entitysWorld1.remove(selectedEntityTexture);
				entityWorld1.getChildren().remove(selectedEntityTexture);
				img.relocate(Double.parseDouble(tab2.getText())*32, Double.parseDouble(tab3.getText())*32);
				img.setId(""+idImageView);
				idImageView++;
				entitysWorld1.add(img);
				entityWorld1.getChildren().add(img);
				System.out.println(tab8.getText().length()+" "+tab8.getText());
				World.world1.getEntity().add( EntityFactory.create( 
						 tab1.getText(),
						(tab2.getText().length()>0?""+tab2.getText():"")+
						(tab3.getText().length()>0?","+tab3.getText():"")+
						(tab4.getText().length()>0?","+tab4.getText():"")+
						(tab5.getText().length()>0?","+tab5.getText():"")+
						(tab6.getText().length()>0?","+tab6.getText():"")+
						(tab7.getText().length()>0?","+tab7.getText():"")+
						(tab8.getText().length()>0?","+tab8.getText():"") ));
				
				
			}else {
				World.world2.getEntity().remove(selectedEntity);
				entitysWorld2.remove(selectedEntityTexture);
				entityWorld2.getChildren().remove(selectedEntityTexture);
				img.relocate(Double.parseDouble(tab2.getText())*32, Double.parseDouble(tab3.getText())*32);
				img.setId(""+idImageView);
				idImageView++;
				entitysWorld2.add(img);
				entityWorld2.getChildren().add(img);
				World.world2.getEntity().add( EntityFactory.create( 
						 tab1.getText(),
						(tab2.getText().length()>0?""+tab2.getText():"")+
						(tab3.getText().length()>0?","+tab3.getText():"")+
						(tab4.getText().length()>0?","+tab4.getText():"")+
						(tab5.getText().length()>0?","+tab5.getText():"")+
						(tab6.getText().length()>0?","+tab6.getText():"")+
						(tab7.getText().length()>0?","+tab7.getText():"")+
						(tab8.getText().length()>0?","+tab8.getText():"") ));
						
			}
			
			selectedEntityTexture=null;
			
		}catch(Exception e) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Un des champs n'est pas valide");
			alert.show();
			e.printStackTrace();
		}
	}

	@FXML
	void deleteEntity(ActionEvent event) {
		if(selectedEntity!=null) {
			if(selectedEntity instanceof EntityTP) {
				if(selectedEntityOnWorld1) {
					World.world1.getEntity().remove(selectedEntity);
					entitysWorld1.remove(selectedEntityTexture);
					entityWorld1.getChildren().remove(selectedEntityTexture);

				}else {
					World.world2.getEntity().remove(selectedEntity);
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
			tab7.clear();
			tab8.clear();
		}
	}

	@FXML
	void SaveEntityButton(ActionEvent event) {	
		try {
			if(world1Save.isSelected() && World.world1!=null) {
				Saver.saveEntity(World.world1);
				alert.setContentText("Succefully saving entitys");
			}

			if(world2Save.isSelected() && World.world2!=null) {
				Saver.saveEntity(World.world2);
				alert.setContentText("Succefully saving entitys");
			}

		}catch(Exception e) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Fail to save entitys : "+e);
			e.printStackTrace();
		}

		if(world1Save.isSelected() && World.world1!=null || world2Save.isSelected() && World.world2!=null ) { 	

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
				if(entityType==0 && World.world1!=null && World.world2!=null) {
					tpCoordonnees = new Coordonnees((int)me.getX()/32, (int)me.getY()/32);
					isEvent=true;
					if(pane.getId()==world1.getId())
						mapNameTp=World.world2.getName();
					else 
						mapNameTp=World.world1.getName();
				}else if(entityType==1) {
					try {
					newEntity = EntityFactory.create( 
							 tab1.getText(),
							(tab2.getText().length()>0?""+tab2.getText():"")+
							(tab3.getText().length()>0?","+tab3.getText():"")+
							(tab4.getText().length()>0?","+tab4.getText():"")+
							(tab5.getText().length()>0?","+tab5.getText():"")+
							(tab6.getText().length()>0?","+tab6.getText():"")+
							(tab7.getText().length()>0?","+tab7.getText():"")+
							(tab8.getText().length()>0?","+tab8.getText():"") );
				System.out.println("New entity : "+newEntity);		

				ImageView img;

				img = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityLiving.png").toURI().toURL()), null));
				img.relocate(newEntity.coordonnes.getX()*32, newEntity.coordonnes.getY()*32);
				img.setId(""+idImageView);
				idImageView++;

				if(pane.getId()==world2.getId()) {
					world1.getChildren().add(img);
					entitysWorld1.add(img);
					World.world1.getEntity().add(newEntity);
				}else if(pane.getId()==world1.getId()){
					world2.getChildren().add(img);
					entitysWorld2.add(img);
					World.world2.getEntity().add(newEntity);
				}
					}catch(Exception e) {
						e.printStackTrace();
						alert.setAlertType(AlertType.ERROR);
						alert.setContentText("Un des champs n'est pas valide");
						alert.show();
					}
				
			} else {
					alert.setAlertType(AlertType.WARNING);
					alert.setContentText("All world need to be loaded");
					alert.show();
				}

			}else if(tool==1 && isEvent) {
				try {
					if(entityType==0 && pane!=tempPane) {
						isEvent=false;

						newEntity = new EntityTP( tpCoordonnees, new Direction(Direction.North),true, mapNameTp, new Coordonnees((int)me.getX()/32, (int)me.getY()/32));
						System.out.println("New entity : "+newEntity);		

						ImageView img;

						img = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP.png").toURI().toURL()), null));
						img.relocate(newEntity.coordonnes.getX()*32, newEntity.coordonnes.getY()*32);
						img.setId(""+idImageView);
						idImageView++;

						if(pane.getId()==world2.getId()) {
							world1.getChildren().add(img);
							entitysWorld1.add(img);
							World.world1.getEntity().add(newEntity);
						}else if(pane.getId()==world1.getId()){
							world2.getChildren().add(img);
							entitysWorld2.add(img);
							World.world2.getEntity().add(newEntity);
						}
					}
				} catch (IOException | URISyntaxException e) {e.printStackTrace();}
			}else if(tool==2) {
				if(pane.getId()==world1.getId()) {
					for(ImageView block:entitysWorld1) {
						if((int)block.getLayoutX()/32==(int)me.getX()/32 && (int)block.getLayoutY()/32==(int)me.getY()/32 ) {
							selectedEntityOnWorld1=true;
							selectedEntityLast=selectedEntity;
							selectedEntity = World.world1.entityHere((int)(me.getX()/32),(int)(me.getY()/32)).length >= 1?World.world1.entityHere((int)(me.getX()/32),(int)(me.getY()/32))[0]:null;
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
							selectedEntityLast=selectedEntity;
							selectedEntity = World.world2.entityHere((int)(me.getX()/32),(int)(me.getY()/32)).length>=1?World.world2.entityHere((int)(me.getX()/32),(int)(me.getY()/32))[0]:null;
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
		
		label1.setLayoutX(31);
		label2.setLayoutX(51);
		label3.setLayoutX(51);
		label4.setLayoutX(7);
		label5.setLayoutX(31);
		
		label1.setText("Type :");
		label2.setText("x :");
		label3.setText("y :");
		label4.setText("Direction :");
		
		tab1.setText(selectedEntity.getId());
		tab2.setText(""+selectedEntity.coordonnes.getX());
		tab3.setText(""+selectedEntity.coordonnes.getY());
		tab4.setText(""+selectedEntity.direction.getDirection());
		
		
		if(selectedEntity instanceof EntityTP) {
			EntityTP entity = (EntityTP)selectedEntity;
			
			label6.setLayoutX(15);
			label7.setLayoutX(29);
			label8.setLayoutX(29);
			
			label5.setText("Etat :");
			label6.setText("Tp World :");
			label7.setText("Tp x :");
			label8.setText("Tp y :");
			
			tab5.setText(""+entity.getEtat());
			tab6.setText(entity.getTPmapName());
			tab7.setText(""+entity.getTPCoordonnees().getX());
			tab8.setText(""+entity.getTPCoordonnees().getY());
			
		}else if(selectedEntity instanceof EntityLight) {
			EntityLight entity = (EntityLight)selectedEntity;
			
			label6.setLayoutX(31);
				
			label5.setText("Etat :");
			label6.setText("Light :");
			label7.setText("");
			label8.setText("");
			
			tab5.setText(""+entity.getEtat());
			tab6.setText(""+entity.lightLvl);
			tab7.setText("");
			tab8.setText("");
			
		}else if(selectedEntity instanceof EntityLiving) {
			EntityLiving entity = (EntityLiving)selectedEntity;
			label5.setLayoutX(35);
			label6.setLayoutX(31);

			label5.setText("PV :");
			label6.setText("");
			label7.setText("");
			label8.setText("");

			tab5.setText(""+entity.getPV());
			tab6.setText("");
			tab7.setText("");
			tab8.setText("");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
		textureLoading();
		eventHandler();

	}

	private void loadWorld(String worldName, boolean isWorld1) {

		try {
			if(isWorld1) {
				entitysWorld1.clear();
				entityWorld1.getChildren().clear();
				backgroundWorld1.getChildren().clear();
				solidWorld1.getChildren().clear();
				topWorld1.getChildren().clear();
				world1.getChildren().clear();
				world1.getChildren().add(backgroundWorld1);
				world1.getChildren().add(solidWorld1);
				world1.getChildren().add(topWorld1);
				world1.getChildren().add(entityWorld1);
				World.loadWorld(worldName, true);	
				resize(world1, World.world1.getWidth()*32, World.world1.getHeight()*32);
				resize(backgroundWorld1, World.world1.getWidth()*32, World.world1.getHeight()*32);
				resize(solidWorld1, World.world1.getWidth()*32, World.world1.getHeight()*32);
				resize(topWorld1, World.world1.getWidth()*32, World.world1.getHeight()*32);
				resize(entityWorld1, World.world1.getWidth()*32, World.world1.getHeight()*32);
				printCalqueTile(backgroundWorld1,solidWorld1,topWorld1,World.world1);
				loadEntitys(entitysWorld1, World.world1.getEntity());
				printCalqueEntitys(entitysWorld1, entityWorld1);

			}else {
				entitysWorld2.clear();
				entityWorld2.getChildren().clear();
				backgroundWorld2.getChildren().clear();
				solidWorld2.getChildren().clear();
				topWorld2.getChildren().clear();
				world2.getChildren().clear();
				world2.getChildren().add(backgroundWorld2);
				world2.getChildren().add(solidWorld2);
				world2.getChildren().add(topWorld2);
				world2.getChildren().add(entityWorld2);
				World.loadWorld(worldName, false);	
				resize(world2, World.world2.getWidth()*32, World.world2.getHeight()*32);
				resize(backgroundWorld2, World.world2.getWidth()*32, World.world2.getHeight()*32);
				resize(solidWorld2, World.world2.getWidth()*32, World.world2.getHeight()*32);
				resize(topWorld2, World.world2.getWidth()*32, World.world2.getHeight()*32);
				resize(entityWorld2, World.world2.getWidth()*32, World.world2.getHeight()*32);
				printCalqueTile(backgroundWorld2,solidWorld2,topWorld2,World.world2);
				loadEntitys(entitysWorld2, World.world2.getEntity());
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

	Image entityTp;
	Image entityLiving;
	Image entityLight;
	public void loadEntitys(ArrayList<ImageView> entitysImage, ObservableList<Entity> observableList) {

		for(Entity entity:observableList) {
			ImageView entityImage;
			System.out.println(entity.getId());
			if(entity instanceof EntityTP) {

				entityImage = new ImageView(entityTp);
				entityImage.relocate(entity.coordonnes.getX()*32, entity.coordonnes.getY()*32);
				entitysImage.add(entityImage);


			}else if(entity instanceof EntityLiving) {
				entityImage = new ImageView(entityLiving);
				entityImage.relocate(entity.coordonnes.getX()*32, entity.coordonnes.getY()*32);
				entitysImage.add(entityImage);
				
			}else if(entity instanceof TileEntity) {
				entityImage = new ImageView(entityLight);
				entityImage.relocate(entity.coordonnes.getX()*32, entity.coordonnes.getY()*32);
				entitysImage.add(entityImage);
				
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
		try {
			entityTp = SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP.png").toURI().toURL()), null);
			entityLiving = SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityLiving.png").toURI().toURL()), null);
			entityLight = SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/entityLight.png").toURI().toURL()), null);
		}catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
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
					String start="";
					
						if(selectedEntityLast instanceof EntityTP) {
							start="EntityTP";
						}else if(selectedEntityLast != null){
							String path = selectedEntityLast.getClass().getSuperclass().getName();
							int currentChar = path.length()-1;
							String end="";

							while (!(""+path.charAt(currentChar)).equals(".") && currentChar>=0) {
								end+=path.charAt(currentChar);
								currentChar--;
							}
							currentChar=end.length()-1;
							while(currentChar>=0) {
								start+=end.charAt(currentChar);
								currentChar--;
							}
						}
					System.out.println(start);
					
					if(!entitysWorld1.isEmpty())
						for(ImageView img:entitysWorld1) {

							if(oldValue!=null && img.getId().equals(oldValue)) 
								img.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/"+start+".png").toURI().toURL()), null));

							else if(img.getId().equals(newValue)) 
								img.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/EntityTP2.png").toURI().toURL()), null));

						}
					if(!entitysWorld2.isEmpty())
						for(ImageView img:entitysWorld2) {

							if(oldValue!=null && img.getId().equals(oldValue)) 
								img.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/"+start+".png").toURI().toURL()), null));

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
			entityLiving.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent event) {
				entityType=1;
				selectedEntityType.relocate(51, 88);
				label5.setLayoutX(35);
				label6.setLayoutX(31);
				label5.setText("PV :");
				label6.setText("");
				label7.setText("");
				label8.setText("");}});
			toolBar.getChildren().add(entityLiving);

			ImageView tileEntity = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/tileEntity.png").toURI().toURL()), null));
			tileEntity.relocate(94, 89);
			tileEntity.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent event) {entityType=2;selectedEntityType.relocate(93, 88);}});
			toolBar.getChildren().add(tileEntity);

			ImageView entityLight = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/ressources/textures/entityLight.png").toURI().toURL()), null));
			entityLight.relocate(136, 89);
			entityLight.setOnMousePressed(new EventHandler<MouseEvent>() {public void handle(MouseEvent event) {entityType=3;selectedEntityType.relocate(136, 88);}});
			toolBar.getChildren().add(entityLight);

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
			world1.setMinWidth(World.world1.getWidth());
			world1.setMinHeight(World.world1.getHeight());

		}else {
			world2.setMinWidth(World.world2.getWidth());
			world2.setMinHeight(World.world2.getHeight());

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

	//Permet d'afficher dans dans chaque pane toute les textures de chaque couches de la map
	private void printCalqueTile(Pane pane,Pane paneTile,Pane paneTop, WorldData currentMap) {

		for(int y=0;y<currentMap.getHeight();y++) {
			for(int x=0;x<currentMap.getWidth();x++) {

				if(currentMap.getTileTerrain(y, x) != null) {
					ImageView tile = new ImageView(dicoImageTileTextureMap.get(currentMap.getTileTerrain(y, x).getId()));	
					tile.setX(x*32);
					tile.setY(y*32);
					pane.getChildren().add(tile);

					tile = new ImageView(dicoImageTileTextureMap.get(currentMap.getTile(y, x).getId()));
					tile.setX(x*32);
					tile.setY(y*32);
					paneTile.getChildren().add(tile);

					tile = new ImageView(dicoImageTileTextureMap.get(currentMap.getTileTop(y, x).getId()));
					tile.setX(x*32);
					tile.setY(y*32);
					paneTop.getChildren().add(tile);
				}

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
