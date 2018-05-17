package app.api.tile;

import app.api.entity.Entity;

public class tileIce extends tileGround{

	public tileIce() {
		super(16);
	}

	@Override
	public void onEntityOver(Entity e) {
		e.slow = 2;
	}

	@Override
	public void leaveEntity(Entity e) {
		e.slow = 1;		
	}

}
