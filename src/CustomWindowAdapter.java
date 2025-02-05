import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CustomWindowAdapter extends WindowAdapter {
    private ToDoManger toDoManger;

    public CustomWindowAdapter(ToDoManger toDoManger){
        this.toDoManger = toDoManger;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        toDoManger.saveToDos();
    }
}
