package app.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import app.api.Entity;
import app.api.EntityLiving;
import app.api.TileEntity;
import app.api.TileEntityTP;
import app.api.World;

public class Saver {
	
	public static void saveEntity(World world) {
		try {
			
			BufferedWriter entitysData = new BufferedWriter(new FileWriter(new File("ressources/map/"+world.getName()+".entity").getAbsolutePath()));
				
			for(Entity curEntity:world.getEntity()) {
				
				entitysData.write(",");entitysData.newLine();
				if(curEntity instanceof TileEntityTP) {
					TileEntityTP entity=(TileEntityTP) curEntity;
					entitysData.write("TileEntityTP");entitysData.newLine();
					entitysData.write(""+entity.getX());entitysData.newLine();
					entitysData.write(""+entity.getY());entitysData.newLine();
					entitysData.write(""+entity.getEtat());entitysData.newLine();
					entitysData.write(""+entity.getTPmapName());entitysData.newLine();
					entitysData.write(""+entity.getTPCoordonnees().getX());entitysData.newLine();
					entitysData.write(""+entity.getTPCoordonnees().getY());entitysData.newLine();
				
				}else if(curEntity instanceof EntityLiving) {
					EntityLiving entity=(EntityLiving) curEntity;
					entitysData.write("EntityLiving");entitysData.newLine();
					entitysData.write(""+entity.getX());entitysData.newLine();
					entitysData.write(""+entity.getY());entitysData.newLine();
					entitysData.write(""+entity.getPV());entitysData.newLine();
					entitysData.write(""+entity.getOrientation());entitysData.newLine();
					entitysData.write("0");entitysData.newLine();//TODO add id
					entitysData.write("");entitysData.newLine();
				
				}else if(curEntity instanceof TileEntity) {
						TileEntity entity=(TileEntity) curEntity;
						entitysData.write("TileEntity");entitysData.newLine();
						entitysData.write(""+entity.getX());entitysData.newLine();
						entitysData.write(""+entity.getY());entitysData.newLine();
						entitysData.write(""+entity.getEtat());entitysData.newLine();
						entitysData.write("North");entitysData.newLine();
						entitysData.write(""+entity.getId());entitysData.newLine();
						entitysData.write("");entitysData.newLine();
					}
			}
			
			entitysData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

	
}
