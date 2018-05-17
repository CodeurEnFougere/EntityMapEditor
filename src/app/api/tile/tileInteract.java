package app.api.tile;

import app.api.entity.Entity;

public abstract class tileInteract extends Tile{

	public tileInteract(int id) {
		super(id);
	}

	public void Action(Entity e) {
		System.out.println("appuyer sur enter");
		Show();
	}
	protected abstract void Show();
	
}
