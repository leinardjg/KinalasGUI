package com.kinalas.core.model.orderModifier;

import com.kinalas.core.model.orderable.Orderable;

import java.util.UUID;

public class OrderModifier {
    private final String id;
    private final Orderable target;
    private final String name;
    private final double multiplier;
    private final double adder;

    public OrderModifier(String id, String name, Orderable target, double multiplier, double adder) {
        this.id = id;
        this.name = name;
        this.target = target;
        this.multiplier = multiplier;
        this.adder = adder;
    }

    public OrderModifier(String name, Orderable target, double multiplier, double adder) {
        this(UUID.randomUUID().toString(), name, target, multiplier, adder);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Orderable getTarget() {
        return target;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public double getAdder() {
        return adder;
    }

    public double getPrice() {
        return target.getPrice() * multiplier + adder;
    }

    // region database

    public boolean create() {
        return OrderModifierController.create(this) > 0;
    }

    public boolean delete() {
        return OrderModifierController.delete(this) > 0;
    }

    // region statics

    public static OrderModifier get(String id) {
        return OrderModifierController.get(id);
    }

    // end region

    @Override
    public String toString() {
        String string = this.name + " " + target.getName();
        if (this.getPrice() != 0) string += " ($" + String.format("%.2f", this.getPrice()) + ")";
        return string;
    }
}
