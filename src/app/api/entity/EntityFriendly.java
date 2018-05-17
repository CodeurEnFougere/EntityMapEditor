package app.api.entity;

import app.utils.Coordonnees;
import app.utils.Direction;

public abstract class EntityFriendly extends EntityLiving{

	public EntityFriendly(int id,Coordonnees position, Direction direction) {
		super(id,position, direction);
	}

}
