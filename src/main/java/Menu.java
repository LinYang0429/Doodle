import javax.swing.*;
import java.awt.event.*;

public class Menu extends JMenuBar implements Observer {
    private Model model;

    public Menu(Model model) {
        this.model = model;
        model.addObserver(this);
        JMenu file = new JMenu("File");
        JMenuItem create = new JMenuItem("New");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(create);
        file.add(load);
        file.add(save);
        file.add(exit);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.create();
            }
        });
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.load();
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.save();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.exit();
            }
        });
        this.add(file);
    }

    @Override
    public void update(Object observable) {

    }
}
