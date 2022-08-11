import javax.swing.*;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Model {

    private Color penColor = Color.orange;
    private int penThickness = 2;
    private ArrayList<Point> points = new ArrayList<>();
    private boolean changed;
    private JFileChooser fc = new JFileChooser();
    private File file = null;
    private int sliderValue = 1000000;
    private Timer timer;
    private Hashtable<Integer, JLabel> table = new Hashtable<>();
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
    private JFrame frame;

    /**
     * Create a new model.
     */
    public Model(JFrame frame) {
        this.observers = new ArrayList();
        this.frame = frame;
        this.changed = false;
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Doodle File(*.ddat)", "ddat"));
        fc.setAcceptAllFileFilterUsed(false);
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    public void setPenColor(Color c) {
        this.penColor = c;
    }

    public void setPenThickness(int t) {
        this.penThickness = t;
    }


    public void exit() {
        if (points.size() > 0 && changed) {
            int option = JOptionPane.showConfirmDialog(frame, "Do you want to save before exit?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                if (save()) {
                    System.exit(0);
                }
            } else if (option == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public void create() {
        if (points.size() > 0 && changed) {
            int option = JOptionPane.showConfirmDialog(frame, "Do you want to save before creating new doodle?", "New", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                if (save()) {
                    points.clear();
                    table.clear();
                    sliderValue = 1000000;
                    notifyObservers();
                }
            } else if (option == JOptionPane.NO_OPTION) {
                points.clear();
                table.clear();
                sliderValue = 1000000;
                notifyObservers();
            }
        }
    }

    public boolean save() {
        int returnVal = fc.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            if (!file.getName().endsWith(".ddat")) {
                file = new File(file.getPath() + ".ddat");
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
                oos.writeObject(points);
                oos.close();
                fos.close();
                changed = false;
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void load() {
        boolean saved = true;
        if (points.size() > 0 && changed) {
            int option = JOptionPane.showConfirmDialog(frame, "Do you want to save before loading?", "Load", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                saved = save();
            } else if (option == JOptionPane.NO_OPTION) {
                saved = true;
            } else {
                saved = false;
            }
        }
        if (saved) {
            int returnVal = fc.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                if (!file.getName().endsWith(".ddat")) {
                    file = new File(file.getPath() + ".ddat");
                }
                if (file.isFile()) {
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ObjectInputStream ois = null;
                    try {
                        ois = new ObjectInputStream(fis);
                        changed = false;
                        points = (ArrayList<Point>) ois.readObject();
                        tick();
                        sliderValue = 1000000;
                        ois.close();
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    notifyObservers();
                }
            }
        }
    }

    public Color getPenColor() {
        return penColor;
    }

    public int getPenThickness() {
        return penThickness;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> p) {
        this.points = p;
    }

    public int getSliderValue() {
        return sliderValue;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public Hashtable<Integer, JLabel> getTable() {
        return table;
    }

    public void jump(int value) {
        sliderValue = value;
        notifyObservers();
    }

    public void tick() {
        table.clear();
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).end){
                table.put(i * 1000000 / points.size(), new JLabel("|"));
            }
        }
        notifyObservers();
    }

    public void play(int value) {
        sliderValue = value;
        ActionListener al = new ActionListener(){
            private int current = (sliderValue * points.size()) / 1000000;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current >= points.size()) {
                    timer.stop();
                } else {
                    current++;
                    sliderValue = current * 1000000 / points.size();
                    notifyObservers();
                }
            }
        };
        timer = new Timer(20, al);
        timer.start();
    }
}
