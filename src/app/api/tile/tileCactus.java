package app.api.tile;

import app.api.entity.Entity;
import app.api.entity.EntityLiving;

public class tileCactus extends tileGround{

	public tileCactus() {
		super(27);
	}
	
	@Override
	public void Action(Entity e) {
		if(e instanceof EntityLiving) {
			((EntityLiving) e).perdrePV();
			System.out.println(((EntityLiving) e).getPV());	
		}
	}
}
