package Controller;
import Model.Contracts;
import Model.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class MainUiController {
    @FXML
    private Button total_Check_btn;

    @FXML
    private Button creat_btn;

    @FXML
    private Button modify_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button check_btn;

    @FXML
    private TextField inputTextField;

    @FXML
    private TableView<Contracts> tableView;

    //@FXML
    //private TableColumn<Contracts, Integer> IdColumn;

    @FXML
    private TableColumn<Contracts, String> NameColumn;

    @FXML
    private TableColumn<Contracts, String> AddressColumn;

    @FXML
    private TableColumn<Contracts, String> PhoneColumn;

    @FXML
    private TableColumn<Contracts, Timestamp> CreateDateColumn;
    public static Service Service=new Service();

    @FXML
    private void handleTotalCheckBtn() {// 处理查询全部联系人按钮点击事件
        // 从数据库中获取全部联系人
        List<Contracts> contractsList = Service.getAllPeoples();
        // 将List转换为ObservableList以便于显示在表格中
        ObservableList<Contracts> observableList = FXCollections.observableArrayList(contractsList);
        // 将表格和ObservableList中的数据关联
        tableView.setItems(observableList);
    }

    @FXML
    private void handleCreateBtn(ActionEvent event) {// 处理新增联系人按钮点击事件
        try {
            // 创建一个新的Stage（窗口）
            Stage createPeopleStage = new Stage();
            createPeopleStage.setTitle("新增联系人");

            // 加载CreateUI.FXML文件用于输入联系人信息
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CreateUI.fxml"));
            Parent root = loader.load();
            // 设置Stage的Scene
            Scene scene = new Scene(root);
            createPeopleStage.setScene(scene);

            // 设置Modality，使新窗口在关闭前阻塞主窗口
            createPeopleStage.initModality(Modality.WINDOW_MODAL);
            createPeopleStage.initOwner(((Node) event.getSource()).getScene().getWindow());

            // 显示新的窗口，这里使用showAndWait()以等待用户输入完毕
            createPeopleStage.showAndWait();

            // 刷新TableView
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModifyBtn() {// 处理更改联系人按钮点击事件
        // 显示确认对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认修改");
        alert.setHeaderText("您确定要修改该联系人信息吗？");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // 用户点击了“确定”，继续保存更改

            // 从 TableView 中获取修改后的联系人
            List<Contracts> modifiedPeople = tableView.getItems();
            // 更新数据库中的每本修改后的联系人
            for (Contracts modifiedContracts : modifiedPeople) {
               Service.updatePeople(modifiedContracts);
            }
            // 刷新 TableView 以反映最新数据
            refreshTableView();
        }
    }


    @FXML
    private void handleDeleteBtn() {// 处理删除联系人按钮点击事件
        // 显示确认对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setHeaderText("您确定要删除该联系人吗？");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // 获取选中的行
           Contracts selectedContracts = tableView.getSelectionModel().getSelectedItem();
            if (selectedContracts != null) {
                // 从数据模型中移除选中的联系人
                Service.deletePeople(selectedContracts.getId());
                // 刷新 TableView
                refreshTableView();
            } else {
                System.out.println("请先选中要删除的联系人");
            }
        }


    }

    @FXML
    private void handleCheckBtn() {
        // 处理查询联系人按钮点击事件
        // 获取用户输入的查询关键字
        String keyword = inputTextField.getText();

        // 根据关键字从数据库中查询联系人
        List<Contracts> contractsList = Service.searchPeoplesByTitle(keyword);

        // 将List转换为ObservableList以便于显示在表格中
        ObservableList<Contracts> observableList = FXCollections.observableArrayList(contractsList);

        // 将表格和ObservableList中的数据关联
        tableView.setItems(observableList);
    }

    @FXML
    private void initialize() {
        // 启用 TableView 的编辑模式
        tableView.setEditable(true);

        // 设置每一列与Contracts类的相应属性绑定
        //隐藏id列
        //IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        CreateDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));


    // 设置每一列允许编辑
        //IdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        NameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        AddressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        PhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // 启用编辑提交事件

//        IdColumn.setOnEditCommit(event -> {
//            TablePosition<Contracts, Integer> position = event.getTablePosition();
//            Integer newId = event.getNewValue();
//            int row = position.getRow();
//            Contracts contracts = event.getTableView().getItems().get(row);
//            contracts.setId(newId);
//        });

        NameColumn.setOnEditCommit(event -> {
            TablePosition<Contracts, String> position = event.getTablePosition();
            String newName = event.getNewValue();
            int row = position.getRow();
            Contracts contracts = event.getTableView().getItems().get(row);
            contracts.setName(newName);
        });
        PhoneColumn.setOnEditCommit(event -> {
            TablePosition<Contracts, String> position = event.getTablePosition();
            String newPhone = event.getNewValue();
            int row = position.getRow();
            Contracts contracts = event.getTableView().getItems().get(row);
            contracts.setPhone(newPhone);
        });
        AddressColumn.setOnEditCommit(event -> {
            TablePosition<Contracts, String> position = event.getTablePosition();
            String newAddress = event.getNewValue();
            int row = position.getRow();
            Contracts contracts = event.getTableView().getItems().get(row);
            contracts.setAddress(newAddress);
        });
    }


    private void refreshTableView() {
        List<Contracts> allPeople = Service.getAllPeoples();
        populateTableView(allPeople);
    }
    //将链表的数据输出到TableView
    private void populateTableView(List<Contracts> contracts) {
        ObservableList<Contracts> observableList = FXCollections.observableArrayList(contracts);
        tableView.setItems(observableList);
    }
}
