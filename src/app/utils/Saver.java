package app.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import app.EntityMapEditor;
import game.modele.entity.Entity;
import game.modele.entity.EntityItemOnGround;
import game.modele.entity.EntityTP;
import game.modele.entity.tileEntity.TikiTorchSmall;
import game.modele.entity.tileEntity.chest.Chest;
import game.modele.entity.tileEntity.chest.GoldChest;
import game.modele.entity.tileEntity.chest.IronChest;
import game.modele.entity.tileEntity.chest.WoodChest;
import game.modele.world.WorldData;

public class Saver {
	
	public static void saveEntity(WorldData world) {
		try {
			
			BufferedWriter entitysData = new BufferedWriter(new FileWriter(new File(EntityMapEditor.mainPath+"/ressources/map/"+world.getName()+".entity").getAbsolutePath()));
				
			for(Entity entity:world.getEntity()) {
				if(!entity.getId().equals("Player")) {
					entitysData.write(entity.getId());entitysData.newLine();
					entitysData.write(""+entity.coordonnes.getX());
					entitysData.write(","+entity.coordonnes.getY());
					entitysData.write(","+entity.direction.getDirection());

					switch(entity.getId()) {

					case "EntityTP":
						EntityTP entityTP = (EntityTP)entity;
						entitysData.write(","+entityTP.getEtat());
						entitysData.write(","+entityTP.getTPmapName());
						entitysData.write(","+entityTP.getTPCoordonnees().getX());
						entitysData.write(","+entityTP.getTPCoordonnees().getY());
						break;

					case "Gold Chest":
						GoldChest goldChest = (GoldChest)entity;
						entitysData.write(","+itemInsideChest(goldChest));
						break;

					case "Iron Chest":
						IronChest ironChest = (IronChest)entity;
						entitysData.write(","+itemInsideChest(ironChest));
						break;

					case "Wood Chest":
						WoodChest woodChest = (WoodChest)entity;
						entitysData.write(","+itemInsideChest(woodChest));
						break;

					case "ItemOnGround":
						EntityItemOnGround entityItemOnGround = (EntityItemOnGround)entity;
						entitysData.write(","+entityItemOnGround.item.name);
						break;
						
					case "TikiTorchSmall":
						TikiTorchSmall tikiTorchSmall = (TikiTorchSmall)entity;
						entitysData.write(","+tikiTorchSmall.getEtat()+
								","+tikiTorchSmall.lightLvl);
						break;
					}
					entitysData.newLine();
				}
			}
			
			entitysData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
		private static String itemInsideChest(Chest chest) {
			if(chest.itemInside != null)
				return chest.itemInside.name;
			else
				return null;
		}
	
}
