package com.kinalas.core.model.orderable.item;

import com.kinalas.core.model.orderModifier.OrderModifier;
import com.kinalas.core.model.orderable.Orderable;

import java.util.ArrayList;

public class Item extends Orderable {

    private String type;

    // region constructors

    protected Item(String id, String name, String description, double price, String type, ArrayList<OrderModifier> modifiers) {
        super(id, name, description, price, modifiers);
        this.type = type.strip().replaceAll(" ", "_").toLowerCase();
    }

    public Item(String name, String description, double price, String type) {
        this(name.strip().replaceAll(" ", "_").toLowerCase(), name, description, price, type, new ArrayList<>());
    }

    // endregion

    // region getters, setters

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // endregion

    // region controller

    public boolean create() {
        return ItemController.create(this) > 0;
    }

    public boolean update() {
        return ItemController.update(this) > 0;
    }

    public boolean delete() {
        return ItemController.delete(this) > 0;
    }

    // region statics

    public static Item get(String id) {
        return ItemController.get(id);
    }

    public static ArrayList<Item> getAll() {
        return ItemController.getAll();
    }

    public static ArrayList<String> getTypes() {
        return ItemController.getTypes();
    }

    // endregion

    // endregion

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("(" + this.id + ", " + this.type + ") " + this.name + ", " + this.description + ", " + this.type);
        if (this.modifiers.size() > 0) {
            stringBuilder.append(", ");
        }

        for (int i=0; i <= this.modifiers.size() - 1; i++) {
            stringBuilder.append(this.modifiers.get(i)).append(" ");
        }

        return stringBuilder.toString();
    }

}
