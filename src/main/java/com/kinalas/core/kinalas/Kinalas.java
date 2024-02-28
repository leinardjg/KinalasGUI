package com.kinalas.core.kinalas;

import com.kinalas.core.model.employee.Employee;
import com.kinalas.core.model.order.Order;
import com.kinalas.core.model.orderable.item.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Kinalas {

    private final Employee employee;
    private final ArrayList<Item> availableItems;
    private final ObservableList<Order> orders;
    private Order currentOrder;
    private final ArrayList<Order> checkedOutOrders;
    private int orderNumber = 1;

    // region constants

    private final int maxBills = 100;
    private final int maxOrderNumber = this.maxBills - 1;

    // end region

    // region constructors

    public Kinalas(String employeeID) {

        this.employee = Employee.get(employeeID);

        if (this.employee == null) {
            System.out.println("Employee not found");
            System.exit(-1);
        }

        this.availableItems = Item.getAll();
        this.orders = FXCollections.observableList(new ArrayList<>());
        this.checkedOutOrders = new ArrayList<>();

    }

    // endregion

    public Employee getEmployee() {
        return employee;
    }

    public ArrayList<Item> getAvailableItems() {
        return availableItems;
    }

    public ObservableList<Order> getOrders() {
        return this.orders;
    }

    public Order newOrder() {
        Order order = new Order(this.employee, this.orderNumber++);
        this.orders.add(order);
        this.currentOrder = order;
        return order;
    }

    public ArrayList<Order> getCheckedOutOrders() {
        return checkedOutOrders;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }
}
