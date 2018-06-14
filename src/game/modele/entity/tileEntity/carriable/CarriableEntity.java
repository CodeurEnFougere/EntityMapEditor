package game.modele.entity.tileEntity.carriable;

import game.modele.entity.Entity;
import game.modele.entity.tileEntity.TileEntity;
import game.modele.utils.Coordonnees;
import game.modele.utils.Direction;

public class CarriableEntity extends TileEntity{


	public CarriableEntity(String id, Coordonnees coordoner, Direction direction) {
		super(id, coordoner, direction, false);
	}

	@Override
	public void active(Entity e) {
	}

	@Override
	public void incAnim() {}

	@Override
	public void onHit(Entity entity) {
	}

	public boolean pickupEntity(Entity entity) {
		super.etat.set(true);

		return true;
	}

	public boolean placeEntity(Entity entity) {
		super.etat.set(false);
		int dir = entity.direction.getDirection();
		int y = (dir==Direction.West?(int)entity.coordonnes.getX()-1:dir==Direction.East?(int)entity.coordonnes.getX()+1:(int)entity.coordonnes.getX());
		int x = (dir==Direction.South?(int)entity.coordonnes.getY()+1:dir==Direction.North?(int)entity.coordonnes.getY()-1:(int)entity.coordonnes.getY());
		super.coordonnes.setCoordoner(y,x);
		this.primaryKey=key++;

		return true;
	}

	public void throwEntity(Entity entity) {
		super.etat.set(false);

	}

}
