import java.time.LocalDateTime;

public class TimedToDo extends ToDo {
    private LocalDateTime endet;

    public TimedToDo (String titel, String beschreibung, boolean erledigt, LocalDateTime endet) {
        super(titel, beschreibung, erledigt);
        this.endet = endet;
    }
    @Override
    public String toString() {
        return "ToDo{" +
                "titel='" + getTitel() + '\'' +
                ", beschreibung='" + getBeschreibung() + '\'' +
                ", erledigt=" + isErledigt() +
                ", endet=" + endet +
                '}';
    }

    public LocalDateTime getEndet() {
        return endet;
    }
    public void setEndet(LocalDateTime endet) {
        this.endet = endet;
    }
}
