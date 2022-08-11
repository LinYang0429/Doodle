import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Doodle");
        Model model = new Model(frame);

        frame.setLayout(new BorderLayout());

        Palette palette = new Palette(model);
        frame.add(palette, BorderLayout.WEST);

        Menu menu = new Menu(model);
        frame.add(menu, BorderLayout.NORTH);

        Sketch sketch = new Sketch(model);
        frame.add(sketch, BorderLayout.CENTER);

        Playcontrol playcontrol = new Playcontrol(model);
        frame.add(playcontrol, BorderLayout.SOUTH);

        // Set up the window.
        frame.setTitle("Doodle");
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we)
            {
                model.exit();
            }
        });

        frame.setVisible(true);
    }
}
