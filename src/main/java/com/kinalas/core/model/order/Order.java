package com.kinalas.core.model.order;

import com.kinalas.core.model.BaseModel;
import com.kinalas.core.model.employee.Employee;
import com.kinalas.core.model.orderable.item.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Order extends BaseModel {

    private final Employee employee;
    private final int orderNumber;
    private final ArrayList<Item> items;
    private Date checkoutDate = null;

    public Order(String id, Employee employee, int orderNumber, ArrayList<Item> items, Date checkoutDate) {
        super(id);
        this.id = id;
        this.employee = employee;
        this.orderNumber = orderNumber;
        this.checkoutDate = checkoutDate;
        this.items = items;
    }

    public Order(Employee employee, int orderNumber) {
        this(UUID.randomUUID().toString(), employee, orderNumber, new ArrayList<>(), null);
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    // region database

    public boolean create() {
        this.checkoutDate = new Date();
        return OrderController.create(this) > 0;
    }

    public boolean delete() {
        return OrderController.delete(this) > 0;
    }

    // region statics

    public static Order get(String id) {
        return OrderController.get(id);
    }

    public static ArrayList<Order> getAll() {
        return OrderController.getAll();
    }

    // end region

    // end region

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("(");
        stringBuilder.append(this.employee.getFirstName()).append(", ").append(this.orderNumber).append(")\n");
        this.items.forEach(item -> {
            stringBuilder.append(item).append("\n");
        });
        return stringBuilder.toString();
    }
}
