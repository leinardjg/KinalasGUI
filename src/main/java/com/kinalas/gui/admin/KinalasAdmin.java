package com.kinalas.gui.admin;

import com.kinalas.gui.admin.components.EmployeeTab;
import com.kinalas.gui.admin.components.ItemsTab;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class KinalasAdmin {

    @FXML private TabPane adminTabPane;

    @FXML
    private void initialize() {
        this.adminTabPane.getTabs().add(new EmployeeTab());
        this.adminTabPane.getTabs().add(new ItemsTab());
    }

}
