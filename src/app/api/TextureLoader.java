package app.api;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import app.EntityMapEditor;

public class TextureLoader {
	
	public static Texture getTextureMapImage(String textureMapName, int textureWidth, int textureHeight, int textureMapWidth, int textureMapHeight, int id) {
		BufferedImage tileMap;
		Texture tileTexture=null;
		try {
			tileMap = ImageIO.read(EntityMapEditor.ressource.getResource("/ressources/textures/"+textureMapName+".png").toURI().toURL());
			
			tileTexture= new Texture(tileMap, textureWidth, textureHeight, id%textureMapWidth, id/textureMapHeight);
		}catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			
		}
		
		return tileTexture;
	}
}
