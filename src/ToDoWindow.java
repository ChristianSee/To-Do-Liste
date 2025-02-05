import javax.imageio.IIOException;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ToDoWindow {
    JFrame jFrame = new JFrame();

    private ToDoManger buttons;
    private JPanel speicherToDoArea;

    public ToDoWindow(ToDoManger buttons) {     //Konstruktor
        this.buttons = buttons;

        jFrame.setTitle("To Do App");
        jFrame.setSize(720, 500);

        jFrame.add((speicherToDoArea = createToDoArea()), BorderLayout.CENTER);
        jFrame.add(createButtons(), BorderLayout.SOUTH);

        jFrame.pack(); //Fenster aktualisieren, z.B. nach entfernen eines to dos

        jFrame.addWindowListener(new CustomWindowAdapter(buttons));     //to dos speichern beim schliessen des Fensters

        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    private JPanel createToDoBlock(ToDo toDo) {

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        jPanel.add(new JLabel(toDo.getTitel()), BorderLayout.NORTH);
        jPanel.add(new JLabel(toDo.getBeschreibung()), BorderLayout.CENTER);

        JCheckBox jCheckBox = new JCheckBox();
        jCheckBox.setSelected(toDo.isErledigt());
        jCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDo.setErledigt(jCheckBox.isSelected()); // Variable wird geaendert, wenn checkbox sich aendert
            }
        });

        jPanel.add(jCheckBox, BorderLayout.EAST);

        if (toDo instanceof TimedToDo) {    //ist es zeitlich begrenztes to do ?
            jPanel.add(new JLabel(((TimedToDo) toDo).getEndet().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))), BorderLayout.SOUTH);
        }
        return jPanel;
    }

    private JPanel createButtons() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        JButton add = new JButton("Hinzufügen");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                String title = getInput("Gib einen Titel ein");
                if (title.isEmpty()) { return; }

                String description = getInput("Gib eine Beschreibung ein");
                if (description.isEmpty()) { return; }

                String hours = getInput("In wievielen Stunden soll das ToDo ablaufen?");
                if (hours.isEmpty()) { return; }

                try {
                    int hoursInteger = Integer.parseInt(hours);

                    if (hoursInteger == 0) {
                        buttons.add(new ToDo(title, description, false));
                    } else {
                        buttons.add(new TimedToDo(title, description, false, LocalDateTime.now().plusHours(hoursInteger)));
                    }
                    } catch(Exception exception){   //Fehlerhafte eingabe dann normales To Do einfuegen
                        buttons.add(new ToDo(title, description, false));
                }
                jFrame.remove(speicherToDoArea);    //aktuelle to do area aus Fenster entfernen
                jFrame.add((speicherToDoArea = createToDoArea()), BorderLayout.CENTER);  //to do area hinzufuegen
                jFrame.pack();  //Fenster aktualisieren
            }
        });

        JButton removeALL = new JButton("Alle entfernen");
        removeALL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.getListe().clear();         // Liste leeren
                jFrame.remove(speicherToDoArea);    //aktuelle to do area aus Fenster entfernen
                jFrame.add((speicherToDoArea = createToDoArea()), BorderLayout.CENTER); //to do area hinzufuegen
                jFrame.pack();  //Fenster aktualisieren
            }
        });

        JButton removeCompleted = new JButton("Erledigte entfernen");
        removeCompleted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = buttons.getListe().size() - 1 ; i >= 0; i--){
                    if (buttons.getListe().get(i).isErledigt()){         // soll das To Do entfernt werden?
                        buttons.getListe().remove(i);
                    }
                }
                jFrame.remove(speicherToDoArea);    //aktuelle to do area aus Fenster entfernen
                jFrame.add((speicherToDoArea = createToDoArea()), BorderLayout.CENTER); //to do area hinzufuegen
                jFrame.pack();  //Fenster aktualisieren
            }
        });

        jPanel.add(add);
        jPanel.add(removeALL);
        jPanel.add(removeCompleted);
        return jPanel;
    }

    private JPanel createToDoArea(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        ArrayList<ToDo> toDos = buttons.getListe(); //Alle to dos

        for (int i = 0; i < toDos.size(); i++){
            // fuer jedes To Do eine Kachel erstellen
            jPanel.add(createToDoBlock(toDos.get(i)));
        }

        return jPanel;
    }

    private String getInput(String eingabe){
        boolean eingabeGueltig = false;
        while (!eingabeGueltig) {
            String input = JOptionPane.showInputDialog(eingabe);

            if (input == null) {
                return "";
            }
            if (!input.isEmpty()) {
                return input;
            }
        }
        return "";
    }
}
