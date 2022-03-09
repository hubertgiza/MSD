import java.util.ArrayList;
import java.util.Random;

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
		if(this.getState()>0){
			this.nextState = this.getState()-1;
		}
		if(this.getState()==0){
				if(this.neighbors.size()>0){
					if (this.neighbors.get(0).getState()>0){
						this.nextState = 6;
					}
				}
		}
	}

	public void drop(){
		Random rand = new Random();
		float sample = rand.nextFloat();
		if(sample<0.05){
			this.setState(6);
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
