package app.api.entity;

import app.utils.Coordonnees;
import app.utils.Direction;

public class EntityTP extends TileEntity{
	private Coordonnees tpCoordonnees;
	private String mapNameTp;

	public EntityTP(int id, Coordonnees coordonerPosition,Direction direction, boolean etat, String mapNameTp, Coordonnees tpCoordonnees) {
		super(id, coordonerPosition,direction, etat);
		this.tpCoordonnees=tpCoordonnees;
		this.mapNameTp=mapNameTp;
	}

	public String getTPmapName() {
		return this.mapNameTp;
	}

	public Coordonnees getTPCoordonnees() {
		return this.tpCoordonnees;
	}

	@Override
	public boolean setCoordoner(Coordonnees coordonnees) {
		return false;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void active(Entity e) {

	}

	@Override
	public void incAnim() {
		// NONE
	}
	
	
	
}
