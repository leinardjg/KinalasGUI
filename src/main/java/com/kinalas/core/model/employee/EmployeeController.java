package com.kinalas.core.model.employee;

import com.kinalas.core.database.DatabaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeController {

    private static final DatabaseController databaseController = DatabaseController.getInstance();
    private static final String tableName = "Employees";

    public static String getTableName() {
        return tableName;
    }

    public static String getTableCreatingQuery() {
        return String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    id TEXT NOT NULL PRIMARY KEY,
                    firstName TEXT NOT NULL,
                    lastName TEXT NOT NULL,
                    accessLevel INTEGER NOT NULL CHECK(accessLevel >= -1)
                );
                """, tableName);
    }

    protected static int create(Employee employee) {

        String query = String.format("""
                INSERT INTO %s (id, firstName, lastName, accessLevel)
                VALUES ('%s', '%s', '%s', '%d');
                """, tableName, employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getAccessLevel());
        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected static Employee get(String _id) {

        String query = String.format("""
                SELECT * FROM %s WHERE id = '%s' LIMIT 1;
                """, tableName, _id);
        try (ResultSet resultSet = databaseController.executeQuery(query)) {

            if (!resultSet.next()) return null;

            String id = resultSet.getString("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            int accessLevel = resultSet.getInt("accessLevel");
            return new Employee(id, firstName, lastName, accessLevel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected static ArrayList<Employee> getAll() {

        ArrayList<Employee> employees = new ArrayList<>();

        String query = String.format("""
                SELECT * FROM %s
                """, tableName);

        try (ResultSet resultSet = databaseController.executeQuery(query)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int accessLevel = resultSet.getInt("accessLevel");
                employees.add(new Employee(id, firstName, lastName, accessLevel));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employees;

    }

    protected static int update(Employee employee) {

        String query = String.format("""
                UPDATE %s
                SET firstName='%s', lastName='%s', accessLevel='%d'
                WHERE id = '%s'
                """, tableName, employee.getFirstName(), employee.getLastName(), employee.getAccessLevel(), employee.getId());

        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected static int delete(Employee employee) {

        String query = String.format("""
                DELETE FROM %s
                WHERE id = '%s'
                """, tableName, employee.getId());

        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
