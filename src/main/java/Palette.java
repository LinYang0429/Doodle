import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Palette extends JPanel implements Observer {
    private JLabel palette = new JLabel("Select Color and stroke thickness:");
    private ArrayList<Color> colors = new ArrayList<>();
    private JPanel selected = new JPanel();
    private int size = 2;
    private Model m;


    public Palette(Model model) {
        this.m = model;
        model.addObserver(this);
        m.setPenColor(Color.orange);
        this.setBackground(Color.WHITE);
        this.setMinimumSize(new Dimension(30, 80));
        this.setSize(new Dimension(20, 100));
        this.setLayout(new GridLayout(5,2));

        colors.add(Color.orange);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.BLUE);
        colors.add(Color.GRAY);
        colors.add(Color.CYAN);

        selected.setBackground(Color.orange);
        JLabel label = new JLabel("Stroke Color");
        selected.add(label);

        for(Color color: colors) {
            JPanel current = new JPanel();
            current.setBackground(color);
            current.setPreferredSize(new Dimension(20,20));
            current.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    selected.setBackground(color);
                    m.setPenColor(color);
                }
            });
            this.add(current);
        }

        JButton colorPicker = new JButton("Select Color");
        colorPicker.setFocusable(false);

        colorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Select Color", selected.getBackground());
                if (c != null) {
                    selected.setBackground(c);
                    m.setPenColor(c);
                }
            }
        });
        this.add(colorPicker);

        selected.setPreferredSize(new Dimension(20,20));
        this.add(selected);

        JComponent stroke1 = new JComponent() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(2));
                g2.draw(new Line2D.Float(0, 0, getWidth(), getHeight()));
            }
        };
        JComponent stroke2 = new JComponent() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(4));
                g2.draw(new Line2D.Float(0, 0, getWidth(), getHeight()));
            }
        };
        stroke1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                size = 2;
                m.setPenThickness(size);
                stroke1.setBorder(BorderFactory.createLineBorder(Color.black));
                stroke2.setBorder(BorderFactory.createLineBorder(Color.white));
            }
        });
        stroke1.setBorder(BorderFactory.createLineBorder(Color.black));

        stroke2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                size = 4;
                m.setPenThickness(size);
                m.setPenThickness(size);
                stroke1.setBorder(BorderFactory.createLineBorder(Color.white));
                stroke2.setBorder(BorderFactory.createLineBorder(Color.black));
            }
        });
        stroke2.setBorder(BorderFactory.createLineBorder(Color.white));
        this.add(stroke1);
        this.add(stroke2);
    }

    @Override
    public void update(Object observable) {

    }
}
