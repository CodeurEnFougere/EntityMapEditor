package game.modele.entity.tileEntity.chest;

import game.modele.entity.Entity;
import game.modele.entity.tileEntity.TileEntity;
import game.modele.item.Item;
import game.modele.utils.Coordonnees;
import game.modele.utils.Direction;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Chest extends TileEntity{
	
	public Item itemInside;
	public IntegerProperty etatAnim;
	
	public Chest(String id, Coordonnees coordoner, Direction direction, Item insideItem) {
		super(id, coordoner, direction, insideItem != null);
		this.itemInside = insideItem;
		super.isSolidEntity = true;
		etatAnim = new SimpleIntegerProperty(etat.get()?0:30);
	}

	@Override
	public void onHit(Entity entity) {}

	@Override
	public void active(Entity e) {}

	@Override
	public void incAnim() {}
	
	public void interact() {
		if(etat.get()) {
		}
	}
	
}
