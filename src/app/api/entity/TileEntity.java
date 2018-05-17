package app.api.entity;

import app.utils.Coordonnees;
import app.utils.Direction;

public abstract class TileEntity extends Entity {
	private boolean etat;
	
	public TileEntity(int id, Coordonnees coordoner,Direction direction, boolean etat) {
		super(id,coordoner,direction);
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean getEtat() {
		return this.etat;
	}
	
	public void setEtat(boolean etat) {
		this.etat=etat;
	}
}
