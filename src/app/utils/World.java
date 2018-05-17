package app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import app.api.WorldData;
import app.api.entity.Entity;
import app.api.entity.EntityTP;
import app.api.tile.Tile;
import app.api.tile.TileFactory;

public class World {
	
	public static WorldData world1;
	public static WorldData world2;
	
	public static void loadWorld(String file, boolean isWorld1) {
		try {
			TileFactory.load();
			//Chargement des tiles
			BufferedReader tilesData = new BufferedReader(new FileReader(new File("ressources/map/"+file+".map").getAbsolutePath()));

			String name = tilesData.readLine();
			int width = Integer.parseInt(tilesData.readLine());
			int height = Integer.parseInt(tilesData.readLine());
			boolean isOutside = Boolean.parseBoolean(tilesData.readLine());


			Tile[][] tileGround = makeTileGrid(width, height, tilesData);

			Tile[][] tileSolid =  makeTileGrid(width, height, tilesData);

			Tile[][] tileTop=  makeTileGrid(width, height, tilesData);

			tilesData.close();
			if(isWorld1)
				world1=new WorldData(name, width, height, isOutside, tileGround, tileSolid, tileTop, loadEntity(file));
			else
				world2=new WorldData(name, width, height, isOutside, tileGround, tileSolid, tileTop, loadEntity(file));

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Entity> loadEntity(String file) throws IOException{
		ArrayList<Entity> entity= new ArrayList<Entity>();
		BufferedReader entityData;
		try {
			entityData = new BufferedReader(new FileReader(new File("ressources/map/"+file+".entity").getAbsolutePath()));
		
		
		String nextLine = entityData.readLine();
		while(nextLine!= null && nextLine.equals(",")) {
			String entityType = entityData.readLine();
			double x=Double.parseDouble(entityData.readLine());
			double y=Double.parseDouble(entityData.readLine());
			
			switch (entityType) {
			
			case "TileEntity":
				boolean etatTileEntity= Boolean.parseBoolean(entityData.readLine());
				entityData.readLine();
				int idTileEntity=Integer.parseInt(entityData.readLine());
				entityData.readLine();
				
				break;
				
			case "EntityLiving":
				int etatEntityLiving= Integer.parseInt(entityData.readLine());
				Direction directionEntityLiving=new Direction(Integer.parseInt(entityData.readLine()));
				entityData.readLine();

				break;
				
			case "TileEntityTP":
				boolean etatTileEntityTP= Boolean.parseBoolean(entityData.readLine());
				String mapTP = entityData.readLine();
				double xTP=Double.parseDouble(entityData.readLine());
				double yTP=Double.parseDouble(entityData.readLine());
				entity.add(new EntityTP(0, new Coordonnees(x, y), new Direction(Direction.North), etatTileEntityTP, mapTP, new Coordonnees(xTP, yTP)));
				break;
			}
			
			nextLine = entityData.readLine();
		}
		entityData.close();
		}catch(Exception e) {
			entityData=null;
		}
		return entity;
	}
	
	
	

	/*
	 * Chargement d'un Tableau de Tile utilis� par la fonction loadWorld();
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
