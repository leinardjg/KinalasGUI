package com.kinalas.core.model.orderable;

import com.kinalas.core.model.BaseModel;
import com.kinalas.core.model.orderModifier.OrderModifier;

import java.util.ArrayList;

public class Orderable extends BaseModel {

    protected String name;
    protected String description;
    protected double price;
    protected ArrayList<OrderModifier> modifiers;

    protected Orderable(String id, String name, String description, double price, ArrayList<OrderModifier> modifiers) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<OrderModifier> getModifiers() {
        return this.modifiers;
    }
}
