package app.api.entity;

import app.utils.Coordonnees;
import app.utils.Direction;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class EntityLiving extends Entity{
	
	//La direction du regard
	protected IntegerProperty PV;
	protected IntegerProperty maxPv;	
	
	public EntityLiving(int id,Coordonnees position, Direction direction) {
		super(id,position,direction);
		
		PV=new SimpleIntegerProperty(12);
		maxPv=new SimpleIntegerProperty(12);
	}
	
	public EntityLiving(int id,Coordonnees position, Direction direction, int pv) {
		super(id,position,direction);
		this.direction=direction;
		PV.set(pv);
	}
	
	public void setDirection(Direction direction) {
		this.direction=direction;
	}
	
	public void perdrePV() {
		if(PV.getValue()>0)
			PV.set(PV.get()-1);
	}
	
	public void gagnerPV() {
		if(PV.getValue()<maxPv.getValue())
			PV.set(PV.get()+1);
	}
	
	public IntegerProperty getPV() {
		return PV;
	}
	
	public void addPermanentHeart() {
		this.maxPv.add(4);
	}
	
	public IntegerProperty getMaxPv() {
		return this.maxPv;
	}
	
	//deplacement
}