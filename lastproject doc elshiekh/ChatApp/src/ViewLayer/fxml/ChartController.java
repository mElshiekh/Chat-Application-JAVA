/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewLayer.fxml;

import DataAccessLayer.DAO.ClientImpl;
import DataAccessLayer.DataBaseManager.DataBaseManager;
import MainClasses.ChatServerRMI;
import MainClasses.Main;
import Property.ClientProp;
import business.BusinessClass;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author abdalla
 */
public class ChartController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView SandC;
    @FXML
    private PieChart Pie;
    @FXML
    private PieChart Pie2;
    @FXML
    private StackedAreaChart char3;
    @FXML
    private BubbleChart chart2;
    @FXML

    private BarChart<String, Number> bc;
    @FXML
    private BubbleChart<Number, Number> blc;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    public TableView<ClientProp> thirdTable;

    private TableView<ClientProp> OldTable;
    @FXML
    private TableColumn<ClientProp, String> Email;
    @FXML
    private TableColumn<ClientProp, String> Password;
    @FXML
    private TableColumn<ClientProp, String> Name;
    @FXML
    private TableColumn<ClientProp, String> Gender;
    @FXML
    private TableColumn<ClientProp, String> Status;
    @FXML
    private TableColumn<ClientProp, String> City;
    @FXML
    private TableColumn<ClientProp, String> Phone;
    @FXML
    private TextArea notifyClientsTA;
    @FXML
    private TextField notifyClientsTF;
             @FXML
    private Label online;
     @FXML
    private Label offline;  
 //   @FXML
 //   private Button btnLoad;
    //declare observable list for database data
    private ObservableList<ClientProp> data;
    private DataBaseManager dc;
    String c1;
    @FXML
    private VBox mainVbox;
    private double xOffset;
    private double yOffset;
    private boolean flag;
    private Image start;
    private Image Stop;
    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dc=new DataBaseManager ();
        start = new Image("images/Start3.png");
        Stop = new Image("images/pause.png");

        flag = true;
        /* Drag and Drop */
        borderPane.setOnMousePressed(event -> {
            xOffset = Main.getPrimaryStage().getX() - event.getScreenX();
            yOffset = Main.getPrimaryStage().getY() - event.getScreenY();
            borderPane.setCursor(Cursor.CLOSED_HAND);
        });

        borderPane.setOnMouseDragged(event -> {
            Main.getPrimaryStage().setX(event.getScreenX() + xOffset);
            Main.getPrimaryStage().setY(event.getScreenY() + yOffset);

        });

        borderPane.setOnMouseReleased(event -> {
            borderPane.setCursor(Cursor.DEFAULT);
        });
loadDataFromDatabase() ;
    }

    public void ChangeTable() {
        ClientImpl impl = new ClientImpl();
        TableView<ClientProp> ClientTable = new TableView<>();
        TableColumn<ClientProp, String> EmailCol = new TableColumn<>("Email");
        EmailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        TableColumn<ClientProp, String> PasswordCol = new TableColumn<>("Password");
        PasswordCol.setCellValueFactory(new PropertyValueFactory<>("Password "));
        TableColumn<ClientProp, String> NameCol = new TableColumn<>("Name");
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TableColumn<ClientProp, String> GenderCol = new TableColumn<>("Gender");
        GenderCol.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        TableColumn<ClientProp, String> StatusCol = new TableColumn<>("Status");
        StatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        TableColumn<ClientProp, String> CItyCol = new TableColumn<>("CIty");
        CItyCol.setCellValueFactory(new PropertyValueFactory<>("CIty"));
        TableColumn<ClientProp, String> PhoneCol = new TableColumn<>("Phone");
        PhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        ClientTable.getColumns().addAll(EmailCol, PasswordCol, NameCol, GenderCol, StatusCol, CItyCol, PhoneCol);

        try {
            ClientTable.getItems().addAll(impl.getPersonList());
        } catch (SQLException ex) {

            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (OldTable != null) {
            mainVbox.getChildren().remove(OldTable);
        }
        mainVbox.getChildren().add(ClientTable);
        OldTable = ClientTable;

    }

      @FXML
    public void loadDataFromDatabase() {
        try {
            Connection conn = dc.getCon();
            data = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Client");
            while (rs.next()) {
                //get strings
                data.add(new ClientProp(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(6),
                        rs.getString(7), rs.getString(8)));
            }

        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        //Set cell values to tableview.
        thirdTable.setEditable(true);
        thirdTable.getSelectionModel().setCellSelectionEnabled(true);

        Password.setCellFactory(TextFieldTableCell.forTableColumn());
        Name.setCellFactory(TextFieldTableCell.forTableColumn());
        Gender.setCellFactory(TextFieldTableCell.forTableColumn());
        Status.setCellFactory(TextFieldTableCell.forTableColumn());
        City.setCellFactory(TextFieldTableCell.forTableColumn());
        Phone.setCellFactory(TextFieldTableCell.forTableColumn());
//makes columns editable
        Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        Password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        City.setCellValueFactory(new PropertyValueFactory<>("City"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));


    Name.setOnEditCommit(event -> {
    ClientProp user = event.getRowValue();
    user.setName(event.getNewValue());
   updataData("Name", event.getNewValue(), user.getEmail());
});
        Gender.setOnEditCommit(event -> {
    ClientProp user = event.getRowValue();
    user.setGender(event.getNewValue());
   updataData("Gender", event.getNewValue(), user.getEmail());
});
            Status.setOnEditCommit(event -> {
    ClientProp user = event.getRowValue();
    user.setStatus(event.getNewValue());
   updataData("Status", event.getNewValue(), user.getEmail());
});
                City.setOnEditCommit(event -> {
    ClientProp user = event.getRowValue();
    user.setCIty(event.getNewValue());
   updataData("City", event.getNewValue(), user.getEmail());
});
        Phone.setOnEditCommit(event -> {
    ClientProp user = event.getRowValue();
    user.setPhone(event.getNewValue());
   updataData("Phone", event.getNewValue(), user.getEmail());
});


        thirdTable.setItems(null);
        thirdTable.setItems(data);

    }


    public void updataData(String column, String newValue, String id) {
        Connection connection = null;
         System.out.println("abdalla"+column+newValue+id);
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","hr","hr");
          //  Statement con = connection.createStatement();
            //connection
            TablePosition pos = thirdTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            TableColumn col = pos.getTableColumn();
           //  String data1 = (String) col.getCellObservableValue(row).getValue();
            //cell
            ClientProp row1 = thirdTable.getSelectionModel().getSelectedItem();
            c1 = row1.getEmail();
            //row
            //tableview variables
            System.out.println(column+newValue+id);
              PreparedStatement stmt = connection.prepareStatement("UPDATE Client SET "+column+" = ? WHERE Email = ? ");
            //Query   
             stmt.setString(1, newValue);
        stmt.setString(2, id);
        stmt.execute();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
    }
//get connection, get celldata, get id data from first row, update cell with selected id
    @FXML
    public void getRow() {

        TablePosition pos = thirdTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        TableColumn col = pos.getTableColumn();
// this gives the value in the selected cell:
        String data1 = (String) col.getCellObservableValue(row).getValue();
        System.out.println(data1);
//CURRENTLY UNUSED METHOD
    }
    
    @FXML
    private void ChangeServerState() {
        /* to Stop server */
        if (flag) {
            flag = false;
            ChatServerRMI.Stop();
            // Image start = new Image("images/Start2.png");
            //SandC = new ImageView(start);
            if (Platform.isFxApplicationThread()) {

                SandC.setImage(start);
            } else {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {

                        SandC.setImage(start);

                    }
                });
            }

        } else/* to Continue server */ {
            flag = true;
            ChatServerRMI.conti();
            if (Platform.isFxApplicationThread()) {

                SandC.setImage(Stop);
            } else {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {

                        SandC.setImage(Stop);
                    }
                });
            }
        }
    }

    @FXML
    private void closeSystem(ActionEvent event) {
        System.out.println("Hii aLL");
        if (Platform.isFxApplicationThread()) {
            Stage stage;
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setTitle("Register");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("error in show register fxml from login fxml");
                Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Platform.runLater(() -> {
                Stage stage;
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setTitle("Register");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in show register fxml from login fxml");
                    Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            });

        }
    }

    @FXML
    public void Tables(ActionEvent event) {

        if (Platform.isFxApplicationThread()) {
            Stage stage;
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Tables.fxml"));
                Parent root = loader.load();
                //    ChartController controller = loader.getController();
                //     controller.setsServerRef(serverRef);
                Scene scene = new Scene(root);
                stage.setTitle("Register");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            } catch (IOException ex) {
                System.out.println("error in show register fxml from login fxml");
                Logger.getLogger(AdminloginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    Stage stage;
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tables.fxml"));
                        Parent root = loader.load();
                        //    ChartController controller = loader.getController();
                        //     controller.setsServerRef(serverRef);
                        Scene scene = new Scene(root);
                        stage.setTitle("Register");
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println("error in show register fxml from login fxml");
                        Logger.getLogger(AdminloginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });

        }
    }

    @FXML
    public void pie1(int A, int B, int C, int D, int E) {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("from Age 5 to 10", A),
                        new PieChart.Data("from Age 10 to 20", B),
                        new PieChart.Data("from Age 20 to 30", C),
                        new PieChart.Data("from Age 30 to 40", D),
                        new PieChart.Data("from Age 40 to 60+", E));
        Pie.setData(pieChartData);
    }

    @FXML
    public void pie2(int A, int B) {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Males", A),
                        new PieChart.Data("Females", B));
        Pie2.setData(pieChartData);

    }

    @FXML
    public void Table1(int A, int B) {
        thirdTable.setEditable(true);

    }

    @FXML
    public void char1() {

        bc.setTitle("females and males ");
        xAxis.setLabel("Females");
        yAxis.setLabel("Males");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data(austria, 25601.34));
        series1.getData().add(new XYChart.Data(brazil, 20148.82));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data(austria, 57401.85));
        series2.getData().add(new XYChart.Data(brazil, 41941.19));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data(austria, 45000.65));
        series3.getData().add(new XYChart.Data(brazil, 44835.76));
        bc.getData().addAll(series1, series2, series3);
    }

    @FXML
    public void char2() {
        xAxis.setLabel("Week");
        yAxis.setLabel("Product Budget");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Product 1");
        series1.getData().add(new XYChart.Data(3, 35));
        series1.getData().add(new XYChart.Data(12, 60));
        series1.getData().add(new XYChart.Data(15, 15));
        series1.getData().add(new XYChart.Data(22, 30));
        series1.getData().add(new XYChart.Data(28, 20));
        series1.getData().add(new XYChart.Data(35, 41));
        series1.getData().add(new XYChart.Data(42, 17));
        series1.getData().add(new XYChart.Data(49, 30));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Product 2");
        series2.getData().add(new XYChart.Data(8, 15));
        series2.getData().add(new XYChart.Data(13, 23));
        series2.getData().add(new XYChart.Data(15, 45));
        series2.getData().add(new XYChart.Data(24, 30));
        series2.getData().add(new XYChart.Data(38, 78));
        series2.getData().add(new XYChart.Data(40, 41));
        series2.getData().add(new XYChart.Data(45, 57));
        series2.getData().add(new XYChart.Data(47, 23));
        blc.getData().addAll(series1, series2);

    }

    @FXML
    public void char3() {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
        Pie.setData(pieChartData);
    }
    
    @FXML
    public void sendAnnouncement(){
        try {
            
           BusinessClass bI =new BusinessClass() ;
           java.util.Date d = new java.util.Date();
           notifyClientsTA.appendText("\n"+d+"  "+notifyClientsTF.getText());
           bI.tellOthersAdmin(notifyClientsTF.getText());
           notifyClientsTF.setText("");
            
        } catch (RemoteException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    }
        public void setOnlineLabel(String number){
        online.setText(number);
    }
    public void setOfflineLabel(String number){
        offline.setText(number);
    }  
}
