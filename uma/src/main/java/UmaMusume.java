import javafx.beans.property.*;

public class UmaMusume {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty baseStar;

    public UmaMusume(int id, String name, String baseStar) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.baseStar = new SimpleStringProperty(baseStar);
    }

    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getBaseStar() { return baseStar.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty baseStarProperty() { return baseStar; }
}