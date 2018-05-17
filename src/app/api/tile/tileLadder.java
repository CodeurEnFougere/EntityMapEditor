package app.api.tile;


public class tileLadder extends tileGround{

	public enum Ladder{
		Ladder_Top(107),
		Ladder_Bottom(139);
		
		private int id; 
		Ladder(int id) {
			this.id = id;
		}
		public int get() {
			return id;
		}
		
	}
	
	public tileLadder() {
		super(123);
	}
	
	public tileLadder(Ladder l) {
		super(l.id);
	}
}
