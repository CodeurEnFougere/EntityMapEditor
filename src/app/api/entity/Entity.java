package app.api.entity;

import app.api.tile.Tile;
import app.api.tile.tileVoid;
import app.utils.Coordonnees;
import app.utils.Direction;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;

public abstract class Entity {


	//repr�sente l'�tat d'une direction
	public class infoDeplacement{
		//la touche est enfonc�
		public boolean active = false;
		//la touche est enfonc� mais une autre prend le dessus
		public boolean attente = false;
	}
	public infoDeplacement moveUP;
	public infoDeplacement moveDown;
	public infoDeplacement moveRight;
	public infoDeplacement moveLeft;
	
	protected int id;
	public Direction direction;
	
	protected Tile currentTile = new tileVoid();
	protected Tile currentTerrain = new tileVoid();
	protected Tile currentTop = new tileVoid();
	
	public Coordonnees coordonnes;
	public IntegerProperty etatDeplacement = new SimpleIntegerProperty(0);

	protected ImageView imageView;

	protected double speed;
	public double slow;


	public Entity(int id,Coordonnees coordonnees,Direction direction) {
		this.direction=direction;
		this.id = id;
		this.coordonnes=coordonnees;
		this.imageView = new ImageView();
		//d�placement
		moveUP = new infoDeplacement();
		moveDown = new infoDeplacement();
		moveLeft = new infoDeplacement();
		moveRight = new infoDeplacement();		
	}
	public void addX(double x)
	{
		if(this.setCoordoner(// TODO Auto-generated method stub
				new Coordonnees(
						this.coordonnes.getX() + x,
						this.coordonnes.getY()
						))) {
			this.coordonnes.setX(this.coordonnes.getX() + x);
		}
	}
	public void addY(double y) {
		if(this.setCoordoner(
				new Coordonnees(
						this.coordonnes.getX(),
						this.coordonnes.getY() + y))) {
			this.coordonnes.setY(this.coordonnes.getY() + y);
		}
	}
	public void forceTp(Coordonnees coordonnees) {
		this.coordonnes.setX(coordonnees.getX());
		this.coordonnes.setY(coordonnees.getY());
	}
	public boolean isOnTileCoord(Coordonnees coordonnees) {
		return((int)this.coordonnes.getX()==(int)coordonnees.getX() && (int)this.coordonnes.getY()==(int)coordonnees.getY());
	}
	public boolean setCoordoner(Coordonnees coordonnees) {
		return true;
	}
	
	//ID

	public int getId() {
		return this.id;
	}

	//UpdateIA

	public abstract void update();

	//Active (Marche dessus)

	public abstract void active(Entity e);

	//deplacement
	public abstract void incAnim();
	public void resetAnim() {
		this.etatDeplacement.set(0);
	}

}