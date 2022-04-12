import java.util.ArrayList;

public class Point {

	public ArrayList<Point> neighbors;
	public static Integer []types ={0,1,2,3};
	public int type;
	public int staticField;
	public boolean moved;
	public Point() {
		this.type=0;
		this.staticField = 100000;
		this.neighbors= new ArrayList<Point>();
		this.moved = false;
	}
	
	public void clear() {
		staticField = 100000;
		
	}

	public boolean calcStaticField() {
		int minStaticField = this.staticField;
		for(Point p : this.neighbors){
			minStaticField = Math.min(minStaticField,p.staticField+1);
		}
		if(this.staticField!=minStaticField){
			this.staticField = minStaticField;
			return true;
		}
		return false;
	}
	
	public void move(){
		if(!this.moved && (this.type==3)){
			Point minNeighbour = this;
			for(Point p : this.neighbors){
				if((minNeighbour.staticField>p.staticField) && (p.type==0 || p.type==2)){
					minNeighbour = p;
				}
			}

			if(minNeighbour != this){
				if(minNeighbour.type!=2){
					minNeighbour.type = this.type;
					minNeighbour.moved = true;
					this.moved = false;
					this.type = 0;
				}
				else{
					this.moved=false;
					this.type=0;
				}
			}
		}
	}
	public void addNeighbor(Point nei) {
			neighbors.add(nei);
		}
}