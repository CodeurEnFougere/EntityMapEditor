package app.api.tile;

public class tileBed extends tileInteract {

	public enum Bed{
		Bed_Bottom(122);
		
		private int id; 
		Bed(int id) {
			this.id = id;
		}
		public int get() {
			return id;
		}
	}
	
	public tileBed() {
		super(106);
	}
	
	public tileBed(Bed b) {
		super(b.id);
	}

	@Override
	protected void Show() {
		// TODO Auto-generated method stub
		
	}

}
