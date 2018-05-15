package app.api;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TileEntityTP extends TileEntity{
	private Coordonnees tpCoordonnees;
	private StringProperty mapNameTp;

	public TileEntityTP(int id, Coordonnees coordonerPosition, boolean etat, String mapNameTp, Coordonnees tpCoordonnees) {
		super(id, coordonerPosition, etat);
		this.tpCoordonnees=tpCoordonnees;
		this.mapNameTp=new SimpleStringProperty(mapNameTp);
	}
	
	public String getTPmapName() {
		return this.mapNameTp.getValue();
	}
	
	public StringProperty getTPmapNameProperty() {
		return this.mapNameTp;
	}
	
	public Coordonnees getTPCoordonnees() {
		return this.tpCoordonnees;
	}
	
	public String toString() {
		return ("coordonnes : "+super.getCoordoner()+" Tp coord : "+this.tpCoordonnees+" world : "+this.mapNameTp.getValue());
	}

}
