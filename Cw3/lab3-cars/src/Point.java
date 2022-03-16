import java.util.Random;

public class Point {

    public int type;
    public Point next;
    public boolean moved;
    public int velocity;

    public Point(){
        this.type=0;
        this.moved=false;
        this.velocity = 0;
    }

    public void move() {
        if(this.next.type==0 && !this.moved){
            this.type=0;
            this.next.type=1;
            this.next.velocity =this.velocity;

            this.moved = true;
            this.next.moved = true;
        }
    }

    public void accelerate(){
        if(this.velocity<5){
            this.velocity =this.velocity + 1;
        }
    }
    public void setVelocity(int velocity){
        if(this.type==1){
            this.velocity = velocity;
        }
    }
    public void randomSlow(){
        if(this.type==1 && this.velocity>=1){
            Random rand = new Random();
            float f = rand.nextFloat();
            if(f<0.05){
                this.velocity -= 1;
            }
        }
    }
    public void clicked() {
        this.type=1;
    }

    public void clear() {
        this.type=0;
    }
}