package com.kinalas.core.model.order;

import com.kinalas.core.database.DatabaseController;
import com.kinalas.core.model.employee.Employee;
import com.kinalas.core.model.employee.EmployeeController;
import com.kinalas.core.model.orderable.item.Item;
import com.kinalas.core.model.relations.orderItem.OrderItemController;
import utils.DateString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class OrderController {
    private static final DatabaseController databaseController = DatabaseController.getInstance();
    public static final String tableName = "Orders";

    public static String getTableCreatingQuery() {
        return String.format("""
                CREATE TABLE IF NOT EXISTS %s(
                    id TEXT NOT NULL PRIMARY KEY,
                    employeeID TEXT NOT NULL,
                    orderNumber INTEGER NOT NULL CHECK (orderNumber > 0),
                    checkoutDate NOT NULL,
                    
                    FOREIGN KEY (employeeID) REFERENCES %s(id) ON DELETE CASCADE
                );
                """, tableName, EmployeeController.getTableName());
    }

    protected static int create(Order order) {

        String query = String.format("""
                INSERT INTO %s (id, employeeID, orderNumber, checkoutDate)
                VALUES ('%s', '%s', '%s', '%s');
                """, tableName, order.getId(), order.getEmployee().getId(), order.getOrderNumber(), DateString.dateToString(order.getCheckoutDate()));
        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected static Order get(String _id) {

        String query = String.format("""
                SELECT * FROM %s WHERE id = '%s' LIMIT 1;
                """, tableName, _id);
        try (ResultSet resultSet = databaseController.executeQuery(query)) {

            if (!resultSet.next()) return null;

            String id = resultSet.getString("id");
            String employeeID = resultSet.getString("employeeID");
            int orderNumber = resultSet.getInt("orderNumber");
            Date checkoutDate = resultSet.getDate("checkoutDate");

            ArrayList<Item> items = OrderItemController.getMany(id);

            return new Order(id, Employee.get(employeeID), orderNumber, items, checkoutDate);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected static ArrayList<Order> getAll() {

        String query = String.format("""
                SELECT id FROM %s
                """, tableName);

        try (ResultSet resultSet = databaseController.executeQuery(query)) {
            ArrayList<String> orderIDs = new ArrayList<>();
            ArrayList<Order> orders = new ArrayList<>();

            while (resultSet.next()) {
                orderIDs.add(resultSet.getString("id"));
            }

            orderIDs.forEach(id -> {
                orders.add(get(id));
            });

            return orders;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected static int delete(Order order) {
        String query = String.format("""
                DELETE FROM %s
                WHERE id = '%s'
                """, tableName, order.getId());

        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
