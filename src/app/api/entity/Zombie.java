package app.api.entity;

import app.utils.Coordonnees;
import app.utils.Direction;

public class Zombie extends EntityMonster{

	public Zombie(Coordonnees c,Direction d) {
		super(0,c,d);
	}

	@Override
	public void update() {
		addX(0.2);
	}

	@Override
	public void active(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incAnim() {
		// TODO Auto-generated method stub
		
	}

}
