import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ToDoManger {
    //methode mit liste mit der wir todos speichern
    ArrayList<ToDo> liste = new ArrayList<>();

    private String pfad;

    public ToDoManger(String pfad){
        this.pfad = pfad;
    }

    // to do zur liste hinzufügen
    public void add(ToDo todo) {
        liste.add(todo);
    }
    // to do aus liste entfernen
    public void remove(ToDo todo) {
        liste.remove(todo);
    }
    // zeitlich begrenzte to dos anzeigen
    public ArrayList<TimedToDo> getTimedToDos() {
        ArrayList<TimedToDo> timedToDos = new ArrayList<>();

        for (int i = 0 ; i < liste.size(); i++) {
            ToDo toDo = liste.get(i);
            if (toDo instanceof TimedToDo){
                timedToDos.add((TimedToDo) toDo);
            }
        }
        return timedToDos;
    }
    // normale nicht zeitlich begrenzte to dos anzeigen
    public ArrayList<ToDo> getNormalToDos() {
        ArrayList<ToDo> timedToDos = new ArrayList<>();

        for (int i = 0 ; i < liste.size(); i++) {
            ToDo toDo = liste.get(i);
            if (!(toDo instanceof TimedToDo)){
                timedToDos.add((ToDo)toDo);
            }
        }
        return timedToDos;
    }
    // alle To Dos zurueckgeben
    public ArrayList<ToDo> getListe() {
        return liste;
    }

    // alle abgelaufenen ,zeitlich begrenzten to dos aus der liste entfernen
    public void removeExpiredToDos() {
        //Alle zeitlich begrenzten to dos abfragen
        ArrayList<TimedToDo> timedToDos = getTimedToDos();

        for (int i = 0 ; i < timedToDos.size(); i++) {
            TimedToDo timedToDo = timedToDos.get(i);    //aktuelles to do aus der liste
            if (timedToDo.getEndet().isBefore(LocalDateTime.now())) {
                remove(timedToDo);
            }
        }
    }
    public void saveToDos(){
        File file = new File(pfad);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            for (int i = 0; i < liste.size(); i++) {        //alle to dos durchgehen und in datei speichern
                ToDo toDo = liste.get(i);
                //To Dos ohne Datum
                String saveString = toDo.getTitel() + "#" + toDo.getBeschreibung() + "#" + toDo.isErledigt();
                //To Dos mit Datum
                if (toDo instanceof TimedToDo) {
                    saveString = saveString + "#" + ((TimedToDo) toDo).getEndet().toString(); //toString() nicht unbedingt nötig
                }
                fileWriter.write(saveString + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadToDos(){
        File file = new File(pfad);

            if (!file.exists()) {
                return;
            }

        try {
            FileReader fileReader = new FileReader(file);           // liest aus Textdatei aus

            String ganzerInhalt = "";                               //gesamter Dateiinhalt
            int aktuellerChar = 0;
            while ((aktuellerChar = fileReader.read()) != -1) {     //Abbruchbedingung, keine Zeichen mehr
                ganzerInhalt = ganzerInhalt + (char) aktuellerChar;
            }
            fileReader.close();

            //String aus Textdatei in Zeilen aufteilen
            String[] zeilen = ganzerInhalt.split("\n");
            for (int i = 0; i < zeilen.length; i++) {
                String zeile = zeilen[i];                         //aktuelle Zeile aus Zeilenarray in variable speichern
                String[] komponenten = zeile.split("#");    //Zeile anhand # teilen

                if (komponenten.length == 3) {        //zeitlich nicht begrenzte to dos
                    liste.add(new ToDo(komponenten[0],komponenten[1], Boolean.valueOf(komponenten[2])));
                }else if (komponenten.length == 4) {  //zeitlich begrenzte to dos
                    liste.add(new TimedToDo(komponenten[0], komponenten[1], Boolean.valueOf(komponenten[2]), LocalDateTime.parse(komponenten[3])));
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
