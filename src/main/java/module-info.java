module com.kinalas.kinalasgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    exports com.kinalas.gui;
    opens com.kinalas.gui to javafx.fxml;

    opens com.kinalas.gui.login to javafx.fxml;
    exports com.kinalas.gui.login;

    exports com.kinalas.core.model.employee;

}