import java.util.ArrayList;

public class Point {
	private ArrayList<Point> neighbors;
	private int currentState;
	private int nextState;
	private int numStates = 6;
	
	public Point() {
		currentState = 0;
		nextState = 0;
		neighbors = new ArrayList<Point>();
	}

	public void clicked() {
		currentState=(++currentState)%numStates;	
	}
	
	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	public void calculateNewState() {
		//TODO: insert logic which updates according to currentState and 
		//number of active neighbors
		if(this.currentState==0&& (this.countAliveNeighbor()==2 || this.countAliveNeighbor()==3 || this.countAliveNeighbor()==4 ||  this.countAliveNeighbor()==5)){
			this.nextState=1;
		}
		else if(this.currentState==1 && (this.countAliveNeighbor()==4 || this.countAliveNeighbor()==5 || this.countAliveNeighbor()==6 ||  this.countAliveNeighbor()==7 ||  this.countAliveNeighbor()==8)){
			this.nextState=1;
		}
		else{
			this.nextState=0;
		}
	}

	public void changeState() {
		currentState = nextState;
	}
	
	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}

	//TODO: write method counting all active neighbors of THIS point
	public int countAliveNeighbor(){
		int count=0;
		for(Point element: this.neighbors){
			if (element.getState()==1){
				count++;
			}
		}
		return count;
	}
}
