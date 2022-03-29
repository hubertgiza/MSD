public class Point {

    public int type;
    public Point next;
    public boolean moved;
    public int velocity;
    public static Integer[] types = {0, 1, 2, 3, 5};
    public Point(){
        this.type=0;
        this.moved=false;
        this.velocity = 0;
    }

    public void move() {
        if(this.next.type==0 && !this.moved){
            this.next.type=this.type;
            this.next.velocity =this.velocity;
            this.type=0;

            this.moved = true;
            this.next.moved = true;
        }
    }

    public void accelerate(){
        int maxVelocity;
        switch(this.type){
            case 1:
                maxVelocity = 3;
                break;
            case 2:
                maxVelocity = 5;
                break;
            case 3:
                maxVelocity = 7;
                break;
            default:
                maxVelocity = 0;
        }
        if(this.velocity<maxVelocity){
            this.velocity =this.velocity + 1;
        }
    }
    public void setVelocity(int velocity){
        if(this.type!=0 && this.type!=5){
            this.velocity = velocity;
        }
    }
    public void clicked() {
        this.type+=1;
        switch(this.type){
            case 1:
                this.velocity=3;
                break;
            case 2:
                this.velocity=5;
                break;
            case 3:
                this.velocity=7;
                break;
        }
    }

    public void clear() {
        if(this.type!=5){
            this.type=0;
        }
        this.next = null;
        this.moved=true;
        this.velocity = 0;
    }
}