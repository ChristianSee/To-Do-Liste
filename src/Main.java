//*****************************************************
//**                                                 **
//** By: Christian Seepold,                          **
//** Thema: To-Do-Liste                              **
//** Version 1.0                                     **
//** Datum: 20.01.2025                               **
//** Status: ready                                   **
//**                                                 **
//*****************************************************




import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ToDoManger buttons = new ToDoManger("todoapp_savefile.txt");
        buttons.loadToDos();        //bei start alle to dos laden
        buttons.removeExpiredToDos();

        new ToDoWindow(buttons);
    }



}