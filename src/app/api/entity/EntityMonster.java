package app.api.entity;


import app.utils.Coordonnees;
import app.utils.Direction;

public abstract class EntityMonster extends EntityLiving {

	public EntityMonster(int id,Coordonnees position, Direction direction) {
		super(id,position, direction);
	}


	
}
