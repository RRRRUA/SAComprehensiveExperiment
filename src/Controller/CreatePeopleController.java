package Controller;
import Model.Contracts;
import Model.Service;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreatePeopleController {

    @FXML
    private TextField NameField;

    @FXML
    private TextField PhoneField;

    @FXML
    private TextField AddressField;

    @FXML
    private void handleConfirmButton(){// 处理确认按钮点击事件，可以将输入的联系人信息保存到数据库等操作
        String name=NameField.getText();
        String phone=PhoneField.getText();
        String address=AddressField.getText();
        Contracts people=new Contracts(name,phone,address);
        new Service().addPeople(people);
        Stage stage = (Stage) NameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancelButton() {// 处理取消按钮点击事件
        Stage stage = (Stage) NameField.getScene().getWindow();
        stage.close();
    }
}
