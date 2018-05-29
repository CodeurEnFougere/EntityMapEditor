package app.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import game.modele.entity.Entity;
import game.modele.entity.EntityTP;
import game.modele.entity.living.EntityLiving;
import game.modele.entity.tileEntity.TileEntity;
import game.modele.world.WorldData;

public class Saver {
	
	public static void saveEntity(WorldData world) {
		try {
			
			BufferedWriter entitysData = new BufferedWriter(new FileWriter(new File("ressources/map/"+world.getName()+".entity").getAbsolutePath()));
				
			for(Entity curEntity:world.getEntity()) {

				if(curEntity instanceof EntityTP) {
					EntityTP entity=(EntityTP) curEntity;
					entitysData.write("TileEntityTP");entitysData.newLine();
					entitysData.write(""+entity.coordonnes.getX());
					entitysData.write(","+entity.coordonnes.getY());
					entitysData.write(","+entity.getEtat());
					entitysData.write(","+entity.getTPmapName());
					entitysData.write(","+entity.getTPCoordonnees().getX());
					entitysData.write(","+entity.getTPCoordonnees().getY());entitysData.newLine();
				
				}else if(curEntity instanceof EntityLiving) {
					EntityLiving entity=(EntityLiving) curEntity;
					entitysData.write("EntityLiving");entitysData.newLine();
					entitysData.write(","+entity.coordonnes.getX());
					entitysData.write(","+entity.coordonnes.getY());
					entitysData.write(","+entity.getPV());
					entitysData.write(","+entity.direction);
					entitysData.write(",");entitysData.newLine();
				
				}else if(curEntity instanceof TileEntity) {
						TileEntity entity=(TileEntity) curEntity;
						entitysData.write("TileEntity");entitysData.newLine();
						entitysData.write(","+entity.coordonnes.getX());
						entitysData.write(","+entity.coordonnes.getY());
						entitysData.write(","+entity.getEtat());
						entitysData.write(",North");entitysData.newLine();
					}
			}
			
			entitysData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

	
}
