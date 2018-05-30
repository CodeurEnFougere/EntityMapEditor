package app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import app.EntityMapEditor;
import game.modele.entity.Entity;
import game.modele.entity.EntityFactory;
import game.modele.entity.Player.Player;
import game.modele.tile.Tile;
import game.modele.tile.TileFactory;
import game.modele.world.WorldData;

public class World {
	
	public static WorldData world1;
	public static WorldData world2;
	
	//Substitution
	public static Player player;
	public static WorldData currentMap;
	
	/*
	 * Chargement de la map : creation du tableau utilisee par l'affichage
	 * */
	public static void loadWorld(String file, boolean isWorld1) {

		TileFactory.load();
		try {
			//Chargement des tiles et entitys		
			BufferedReader tilesData = new BufferedReader(new FileReader(new File(EntityMapEditor.mainPath+"/ressources/map/"+file+".map").getAbsolutePath()));
			
			String name = tilesData.readLine();
			int width = Integer.parseInt(tilesData.readLine());
			int height = Integer.parseInt(tilesData.readLine());
			boolean outside = Boolean.parseBoolean(tilesData.readLine());

			Tile[][] tileGround = makeTileGrid(width, height, tilesData);

			Tile[][] tileSolid =  makeTileGrid(width, height, tilesData);

			Tile[][] tileTop=  makeTileGrid(width, height, tilesData);

			tilesData.close();
			
			if(isWorld1)
				world1=new WorldData(name, width, height, outside, tileGround, tileSolid, tileTop, loadEntity(file));
			else {
				world2=new WorldData(name, width, height, outside, tileGround, tileSolid, tileTop, loadEntity(file));
			}

		}catch(IOException e) {
			System.out.println("Impossible de charger la map ");
			e.printStackTrace();
		}

	}

	public static ArrayList<Entity> loadEntity(String world){
		ArrayList<Entity> entity= new ArrayList<Entity>();
		BufferedReader entityData;
		try {
			entityData = new BufferedReader(new FileReader(new File(EntityMapEditor.mainPath+"/ressources/map/"+world+".entity").getAbsolutePath()));
		

		String nextLine = entityData.readLine();
		while(nextLine != null && nextLine.length()> 1) {
			Entity newEntity = EntityFactory.create(nextLine, entityData.readLine());
			if(newEntity != null)
				entity.add(newEntity);
			nextLine = entityData.readLine();
		}
		entityData.close();
		} catch (IOException e) {
			System.out.println("No entity or invalide file : "+e);
		}
		return entity;
	}

	/*
	 * Chargement d'un Tableau de Tile utilise par la fonction loadWorld();
	 * */
	private static Tile[][] makeTileGrid(int width,int height,BufferedReader br){
		try {
			Tile[][] tile = new Tile[height][width];
			Pattern pat = Pattern.compile(",");
			for(int i = 0; i < height;i++) {
				String[] tabGround = pat.split(new StringBuilder(br.readLine()));
				for(int x = 0; x < tabGround.length ; x++)
					tile[i][x] = TileFactory.get(Integer.parseInt(tabGround[x]));
			}
			return tile;
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
