public class ToDo {
    private String titel;
    private String beschreibung;
    private boolean erledigt;

    public ToDo(String titel, String beschreibung, boolean erledigt){
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.erledigt = erledigt;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "titel='" + titel + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", erledigt=" + erledigt +
                '}';
    }

    public String getTitel() {
        return titel;
    }
    public void setTitel(String titel) {
        this.titel = titel;
    }
    public String getBeschreibung() {
        return beschreibung;
    }
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    public boolean isErledigt(){
        return erledigt;
    }
    public void setErledigt(boolean erledigt) {
        this.erledigt = erledigt;
    }
}
