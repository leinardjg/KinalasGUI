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

    opens com.kinalas.gui.setup to javafx.fxml;
    exports com.kinalas.gui.setup;

    opens com.kinalas.gui.start to javafx.fxml;
    exports com.kinalas.gui.start;

    opens com.kinalas.gui.login to javafx.fxml;
    exports com.kinalas.gui.login;

    opens com.kinalas.gui.admin to javafx.fxml;
    exports com.kinalas.gui.admin;

    opens com.kinalas.gui.kinalas to javafx.fxml;
    exports com.kinalas.gui.kinalas;

    opens com.kinalas.core.model to java.base;
    exports com.kinalas.core.model;

    opens com.kinalas.core.model.orderable.item to java.base;
    exports com.kinalas.core.model.orderable.item;

    opens com.kinalas.core.model.orderable to java.base;
    exports com.kinalas.core.model.orderable;

    opens com.kinalas.gui.admin.create.employee to javafx.fxml;
    exports com.kinalas.gui.admin.create.employee;

    opens com.kinalas.gui.admin.create.item to javafx.fxml;
    exports com.kinalas.gui.admin.create.item;

    exports com.kinalas.core.model.employee;
    exports com.kinalas.core.kinalas;

}