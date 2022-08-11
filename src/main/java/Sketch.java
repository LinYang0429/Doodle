import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


public class Sketch extends JPanel implements MouseListener, MouseMotionListener, Observer {
    private Model model;
    private int x, y;
    private int startx, starty, endx, endy;
    private ArrayList<Point> points;

    public Sketch(Model model) {
        this.model = model;
        model.addObserver(this);
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        repaint();
        addMouseListener(this);
        addMouseMotionListener(this);
        this.points = new ArrayList<>(model.getPoints());
    }

    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        Point p = new Point(startx, starty, x, y, model.getPenColor(), model.getPenThickness(), false);
        points.add(p);
        model.setPoints(points);
        model.tick();
        model.setChanged(true);
        model.jump(1000000);
        repaint();
        startx = x;
        starty = y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        startx = e.getX();
        starty = e.getY();
    }
    public void mouseReleased(MouseEvent e) {
        endx = e.getX();
        endy = e.getY();
        Point p = new Point(startx, starty, endx, endy, model.getPenColor(), model.getPenThickness(), true);
        points.add(p);
        model.setPoints(points);
        model.tick();
        model.setChanged(true);
        model.jump(1000000);
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for(Point p : points) {
            g2d.setColor(p.c);
            g2d.setStroke(new BasicStroke(p.Stroke));
            g2d.drawLine(p.x1, p.y1, p.x2, p.y2);
        }
    }

    @Override
    public void update(Object observable) {
        this.points = new ArrayList<>(model.getPoints());
        int sliderValue = model.getSliderValue();
        int current = (sliderValue * points.size()) / 1000000;
        points.subList(current, points.size()).clear();
        repaint();
    }
}
