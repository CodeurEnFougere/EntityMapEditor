package game.modele.entity.living.friendly.sheeps;

import game.modele.entity.Entity;
import game.modele.entity.living.friendly.EntityFriendly;
import game.modele.utils.Coordonnees;
import game.modele.utils.Direction;

public class Sheep extends EntityFriendly{
	
	
	public Sheep(String id, Coordonnees position, Direction direction) {
		super(id, position, direction);
		this.speed = 0.04;
		this.baseSpeed = 0.04;
		this.acce = 0.0;


	}

	@Override
	public void active(Entity e) {

	}
	@Override
	public void incAnim() {
		this.etatDeplacement.set(
				this.etatDeplacement.get()
				+ (this.etatDeplacement.get() < 83 ? 1 : -83));
	}
	
	

}
