package app.api;

public class Tile {
	
	private int id;
	
	public Tile(int id) {
		this.id=id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String toString() {
		String name="id: "+this.id;
		if(id==1)
			name+=" name: grass";
		return name;
	}
	
}
