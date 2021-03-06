package game.modele.world;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import app.utils.World;
import game.modele.entity.Entity;
import game.modele.tile.Tile;
import game.modele.utils.graph.Graph;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WorldData {

	private StringProperty zoneName=new SimpleStringProperty("null");
	private Tile[][] tileGround;
	private Tile[][] tiles;
	private Tile[][] tilesTop;
	private IntegerProperty[][] luminosity;

	public ObservableList<Entity> entity ;
	private int width,height;
	public Graph g;
	private boolean outSide;

	public WorldData(String zoneName, int width, int height, boolean outside, Tile ground[][], Tile tiles[][], Tile tilesTop[][], ArrayList<Entity> entitys) {
		this.entity = FXCollections.observableArrayList();
		this.luminosity = new SimpleIntegerProperty[width][height];
		Entity.key=0;
		this.width=width;
		this.height=height;
		this.outSide=outside;
		this.tileGround=ground;
		this.tiles=tiles;
		this.tilesTop=tilesTop;
		
		for(Entity entity:entitys)
			this.entity.add(entity);
		this.luminosity = new SimpleIntegerProperty[width][height];
		for(int x = 0 ; x < width ; x++)
			for(int y = 0; y < height ; y++) {
				luminosity[x][y] = new SimpleIntegerProperty(0);
			}

		this.zoneName.setValue(zoneName);
		g = new Graph(width, height);
	}

	public String getName() {
		return this.zoneName.get();
	}

	public boolean isOutside() {
		return this.outSide;
	}

	public StringProperty getNameProperty() {
		return this.zoneName;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Tile getTileTerrain(int x, int y) {
		return tileGround[x][y];
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public Tile getTileTop(int x, int y) {
		return tilesTop[x][y];
	}

	public IntegerProperty getShadow(int x,int y) {
		return luminosity[x][y];
	}

	public ObservableList<Entity> getEntity(){
		return this.entity;
	}
	
	public void deleteEntity(int primaryKey) {
		for(int i = 0; i < entity.size(); i++) {
			if(entity.get(i).primaryKey == primaryKey) {
				entity.remove(i);
			}
		}
	}

	public Entity[] entityHere(double x,double y) {
		return this.entity.stream().filter(a -> a.coordonnes.isSameTile(x, y)).toArray(Entity[]::new);
	}

	public void AddTorch(int x,int y,int i,int pas) {
		ArrayList<SimpleEntry<Integer,Integer>> tmp = new ArrayList<>();
		tmp.add(new SimpleEntry<Integer, Integer>(x,y));
		int current = 0;
		addLight(tmp, current, i, pas);

	}

	public void addLight(ArrayList<SimpleEntry<Integer,Integer>> tmp, int current , int i , int pas) {
		if(i == 0) return;

		int lenght = tmp.size();
		for(int p = current; p < lenght;p++) {			
			SimpleEntry<Integer,Integer> si = tmp.get(p);
			if(!tmp.contains(new SimpleEntry<Integer, Integer>(si.getKey() + 1,si.getValue())))
				tmp.add(new SimpleEntry<Integer, Integer>(si.getKey() + 1,si.getValue()));

			if(!tmp.contains(new SimpleEntry<Integer, Integer>(si.getKey(),si.getValue() + 1)))
				tmp.add(new SimpleEntry<Integer, Integer>(si.getKey(),si.getValue() + 1));

			if(!tmp.contains(new SimpleEntry<Integer, Integer>(si.getKey() - 1,si.getValue())))
				tmp.add(new SimpleEntry<Integer, Integer>(si.getKey() - 1,si.getValue()));

			if(!tmp.contains(new SimpleEntry<Integer, Integer>(si.getKey(),si.getValue() - 1)))
				tmp.add(new SimpleEntry<Integer, Integer>(si.getKey(),si.getValue() - 1));

			if(si.getKey().intValue() < this.getHeight() 
					&& si.getKey().intValue() >= 0 
					&& si.getValue().intValue() < this.getWidth() 
					&& si.getValue().intValue() >= 0) {
				World.currentMap.luminosity[si.getKey()][si.getValue()]
						.set(World.currentMap.luminosity[si.getKey()][si.getValue()].get() + i);
			}

			current++;
		}

		addLight(tmp, current, i - pas, pas);
	}
}
