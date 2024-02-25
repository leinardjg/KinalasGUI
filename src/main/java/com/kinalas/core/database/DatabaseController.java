package com.kinalas.core.database;

import com.kinalas.core.model.employee.EmployeeController;
import com.kinalas.core.model.order.OrderController;
import com.kinalas.core.model.orderModifier.OrderModifierController;
import com.kinalas.core.model.orderable.item.ItemController;
import com.kinalas.core.model.relations.orderItem.OrderItemController;

import java.sql.*;

public class DatabaseController {
    private static final String dbPath = "kinalas.db";
    private static DatabaseController instance = null;
    private Connection connection = null;
    private Statement statement = null;

    private DatabaseController() {
        this.init();
    }

    public static DatabaseController getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DatabaseController();
                return instance;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void init() {
        try {
            String url = "jdbc:sqlite:" + dbPath;
            this.connection = DriverManager.getConnection(url);
            this.statement = connection.createStatement();

            // enable SQLite foreign keys
            this.statement.execute(
                    """
                    PRAGMA foreign_keys = ON;
                    """);

            this.createTables();

        } catch (SQLException e) {
            System.out.println("SQL Exception on DatabaseController init()");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void createTables() {
        try {
            execute(EmployeeController.getTableCreatingQuery());
            execute(ItemController.getTableCreatingQuery());
            execute(OrderController.getTableCreatingQuery());
            execute(OrderItemController.getTableCreatingQuery());
            execute(OrderModifierController.getTableCreatingQuery());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean execute(String query) throws SQLException {
        return this.statement.execute(query);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return this.statement.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        return this.statement.executeUpdate(query);
    }

}
