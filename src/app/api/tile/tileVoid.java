package app.api.tile;

import app.api.entity.Entity;

public class tileVoid  extends tileGround{

	public tileVoid() {
		super(0);
	}

	@Override
	public void Action(Entity e) {

	}
	
	@Override
	public boolean solid() {
		return false;
	}
}
