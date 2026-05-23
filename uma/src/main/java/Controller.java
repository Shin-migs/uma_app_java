import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class Controller {

    @FXML private TextField txtName;
    @FXML private ChoiceBox<BaseStar> cbStar;

    @FXML private TableView<UmaMusume> table;
    @FXML private TableColumn<UmaMusume, Integer> colId;
    @FXML private TableColumn<UmaMusume, String> colName;
    @FXML private TableColumn<UmaMusume, String> colStar;

    private ObservableList<UmaMusume> list = FXCollections.observableArrayList();
    private Connection conn;
    private int selectedId = -1;

    @FXML
    public void initialize() {
        conn = DBConnection.connect();

        if (conn == null) {
            System.out.println("[ERROR] Database connection is null. Check your .env file.");
            return;
        }

        cbStar.getItems().setAll(BaseStar.values());

        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colName.setCellValueFactory(data -> data.getValue().nameProperty());
        colStar.setCellValueFactory(data -> data.getValue().baseStarProperty());

        loadData();

        table.setOnMouseClicked(e -> {
            UmaMusume u = table.getSelectionModel().getSelectedItem();
            if (u != null) {
                selectedId = u.getId();
                txtName.setText(u.getName());
                for (BaseStar star : BaseStar.values()) {
                    if (star.toString().equals(u.getBaseStar())) {
                        cbStar.setValue(star);
                    }
                }
            }
        });
    }

    private void loadData() {
        list.clear();
        try {
            String query = "SELECT * FROM umamusume ORDER BY id ASC";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                list.add(new UmaMusume(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("base_star")
                ));
            }
            table.setItems(list);
            System.out.println("[OK] Loaded " + list.size() + " records.");
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to load data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void addUma() {
        if (conn == null) return;
        try {
            String query = "INSERT INTO umamusume(name, base_star) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, txtName.getText());
            pst.setString(2, cbStar.getValue() != null ? cbStar.getValue().toString() : "");
            pst.executeUpdate();
            loadData();
            clearFields();
        } catch (Exception e) {
            System.out.println("[ERROR] Add failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void updateUma() {
        if (conn == null || selectedId == -1) return;
        try {
            String query = "UPDATE umamusume SET name=?, base_star=? WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, txtName.getText());
            pst.setString(2, cbStar.getValue() != null ? cbStar.getValue().toString() : "");
            pst.setInt(3, selectedId);
            pst.executeUpdate();
            loadData();
            clearFields();
        } catch (Exception e) {
            System.out.println("[ERROR] Update failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteUma() {
        if (conn == null || selectedId == -1) return;
        try {
            String query = "DELETE FROM umamusume WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, selectedId);
            pst.executeUpdate();
            loadData();
            clearFields();
        } catch (Exception e) {
            System.out.println("[ERROR] Delete failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields() {
        txtName.clear();
        cbStar.setValue(null);
        selectedId = -1;
        table.getSelectionModel().clearSelection();
    }
}
