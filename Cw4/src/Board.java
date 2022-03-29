import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.lang.*;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points;
    private int size = 25;
    public int editType = 0;

    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    private void initialize(int length, int height) {
        points = new Point[length][height];

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point();
                if(y!=2 && y!=3){
                    points[x][y].type=5;
                }
            }
        }
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if(x!=points.length - 1){
                    points[x][y].next = points[x+1][y];
                }
                else{
                    points[x][y].next = points[0][y];
                }
            }
        }
    }

    public void iteration() {

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].moved=false;
            }
        }
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if(points[x][y].type==1 || points[x][y].type==2 || points[x][y].type==3){
                    points[x][y].accelerate();
                }
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if(points[x][y].type==1 || points[x][y].type==2 || points[x][y].type==3){
                    int distanceAheadLeft=-1;
                    int distanceAheadRight=-1;
                    int distanceBehindLeft=-1;
                    int distanceBehindRight=-1;
                    int maxV=7;
                    int carMaxV;
                    switch(points[x][y].type){
                        case 1:
                            carMaxV=3;
                            break;
                        case 2:
                            carMaxV=5;
                            break;
                        case 3:
                            carMaxV=7;
                            break;
                        default:
                            carMaxV=0;
                    }
                    for(int k=0;k<=maxV;k++){
                        if(points[Math.abs(x-k)%points.length][3].type!=0){
                            distanceBehindRight = k;
                        }
                    }
                    for(int k=0;k<=maxV;k++){
                        if(points[Math.abs(x-k)%points.length][2].type!=0){
                            distanceBehindLeft = k;
                        }
                    }
                    for(int k=0;k<=maxV;k++){
                        if(points[(x+k)%points.length][2].type!=0){
                            distanceAheadLeft = k;
                        }
                    }
                    for(int k=0;k<=maxV;k++){
                        if(points[(x+k)%points.length][3].type!=0){
                            distanceAheadRight = k;
                        }
                    }
                    
                    // manewr powrotu
                    if(distanceBehindRight==-1 && distanceBehindLeft<=0 && (distanceAheadRight==-1 || distanceAheadRight>=points[x][y].velocity)){
                        System.out.println("1");
                        System.out.println(""+distanceBehindRight+ " "+distanceAheadRight+" "+distanceBehindLeft+" "+distanceAheadLeft);
                        points[(x+points[x][y].velocity-1)%points.length][3].velocity = points[x][y].velocity;
                        points[(x+points[x][y].velocity-1)%points.length][3].moved = true;
                        points[(x+points[x][y].velocity-1)%points.length][3].type = points[x][y].type;
                        points[x][y].clear();
                    }
                    // manewr wyprzedzania
                    else if(points[x][y].velocity<carMaxV && distanceBehindRight<=0 && distanceBehindLeft==-1 && (distanceAheadLeft==-1 || distanceAheadLeft>=points[x][y].velocity)){
                        System.out.println("2");
                        System.out.println(""+distanceBehindRight+ " "+distanceAheadRight+" "+distanceBehindLeft+" "+distanceAheadLeft);
                        points[(x+points[x][y].velocity-1)%points.length][2].velocity = points[x][y].velocity;
                        points[(x+points[x][y].velocity-1)%points.length][2].moved = true;
                        points[(x+points[x][y].velocity-1)%points.length][2].type = points[x][y].type;
                        points[x][y].clear();
                    }
                    // manewr ruchu
                    else{
                        System.out.println("3");
                        boolean isCarAhead=false;
                        for(int k=1;k<=points[x][y].velocity;k++){
                            if(points[(x+k)%points.length][y].type!=0){
                                points[x][y].next = points[(x + k - 1)%points.length][y];
                                isCarAhead=true;
                                points[x][y].velocity=k;
                                break;
                            }
                        }
                        if(!isCarAhead){
                            points[x][y].next = points[(x+points[x][y].velocity)%points.length][y];
                        }

                        points[x][y].move();
                    }
                }
            }
        }
        this.repaint();
    }

    public void clear() {
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clear();
            }
        this.repaint();
    }


    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < points.length; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                if(points[x][y].type == 1){
                    g.setColor(new Color(1.0f, 1.0f, 0.0f, 0.7f));
                }
                else if(points[x][y].type == 2){
                    g.setColor(new Color(0.0f, 0.0f, 1.0f, 0.7f));
                }
                else if(points[x][y].type == 3){
                    g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.7f));
                }
                else if(points[x][y].type == 5){
                    g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.7f));
                }
                else{
                    g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.7f));
                }
                
                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
        if(editType==0){
        points[x][y].clicked();
        }
        else {
        points[x][y].type= editType;
        }
        this.repaint();
        }
        }

    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
        if(editType==0){
        points[x][y].clicked();
        }
        else {
        points[x][y].type= editType;
        }
        this.repaint();
        }
        }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}
