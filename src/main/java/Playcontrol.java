import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Playcontrol extends JPanel implements Observer{
    private Model model;
    private JSlider slider;

    public Playcontrol(Model model) {
        this.model = model;
        model.addObserver(this);
        this.setBackground(Color.WHITE);
        this.setMinimumSize(new Dimension(400, 40));
        this.setLayout(new GridLayout());

        slider = new JSlider(JSlider.HORIZONTAL, 0, 1000000, 1000000);
        slider.setPreferredSize(new Dimension(400, 40));
        slider.setMinimumSize(new Dimension(100, 40));
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.jump(slider.getValue());
            }
        });
        slider.setPaintLabels(true);

        JButton play = new JButton();
        ImageIcon imageIcon = new ImageIcon("play.png");
        Image playicon = imageIcon.getImage().getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
        play.setIcon(new ImageIcon(playicon));;
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.play(slider.getValue());
            }
        });
        this.add(play);
        this.add(slider);

        JButton start = new JButton();
        ImageIcon startimage = new ImageIcon("start.png");
        Image starticon = startimage.getImage().getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
        start.setIcon(new ImageIcon(starticon));
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                slider.setValue(0);
            }
        });
        this.add(start);

        JButton end = new JButton();
        ImageIcon endimage = new ImageIcon("end.png");
        Image endicon = endimage.getImage().getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
        end.setIcon(new ImageIcon(endicon));
        end.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                slider.setValue(slider.getMaximum());
            }
        });
        this.add(end);
    }

    @Override
    public void update(Object observable) {
        if (!model.getTable().isEmpty()) {
            slider.setLabelTable(model.getTable());
        }
        slider.setValue(model.getSliderValue());
        slider.repaint();
    }
}
