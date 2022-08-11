import java.awt.*;
import java.io.Serializable;

public class Point implements Serializable {
    public int x1, x2, y1, y2;
    public Color c;
    public int Stroke;
    public boolean end;

    public Point(int x1, int y1, int x2, int y2, Color c, int stroke, boolean end) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.c = c;
        this.Stroke = stroke;
        this.end = end;
    }

}