package com.kinalas.core.model.orderable.item;

import com.kinalas.core.database.DatabaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemController {

    private static final DatabaseController databaseController = DatabaseController.getInstance();
    private static final String tableName = "Items";

    public static String getTableName() {
        return tableName;
    }

    public static String getTableCreatingQuery() throws SQLException {
        return String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    id TEXT NOT NULL PRIMARY KEY,
                    name TEXT NOT NULL,
                    description TEXT,
                    price REAL NOT NULL,
                    type TEXT NOT NULL
                );
                """, tableName);
    }

    protected static int create(Item item) {

        String query = String.format("""
                INSERT INTO %s (id, name, description, price, type)
                VALUES ('%s', '%s', '%s', %2f, '%s');
                """, tableName, item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getType());

        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            return 0;
        }

    }

    protected static Item get(String _id) {

        String query = String.format("""
                SELECT * FROM %s
                WHERE id = '%s';
                """, tableName, _id);

        try (ResultSet resultSet = databaseController.executeQuery(query)) {

            if (!resultSet.next()) return null;

            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");
            String type = resultSet.getString("type");

            return new Item(id, name, description, price, type, new ArrayList<>());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected static ArrayList<Item> getAll() {

        String query = String.format("""
                SELECT * FROM %s;
                """, tableName);

        ArrayList<Item> items = new ArrayList<>();

        try (ResultSet resultSet = databaseController.executeQuery(query)) {

            while (resultSet.next()) {

                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                String type = resultSet.getString("type");

                items.add(new Item(id, name, description, price, type, new ArrayList<>()));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return items;

    }

    protected static int update(Item item) {
        String query = String.format("""
                UPDATE %s
                SET name='%s',
                    description='%s',
                    price=%2f,
                    type='%s'
                """, tableName, item.getName(), item.getDescription(), item.getPrice(), item.getType());

        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected static int delete(Item item) {
        String query = String.format("""
                DELETE FROM %s
                WHERE id = '%s';
                """, tableName, item.getId());

        try {
            return databaseController.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
