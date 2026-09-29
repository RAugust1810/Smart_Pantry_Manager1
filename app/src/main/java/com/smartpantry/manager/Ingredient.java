package com.smartpantry.manager;

public class Ingredient {

    private int id;
    private String name;
    private double quantity;
    private String unit;
    private String category;

    public Ingredient(
            int id,
            String name,
            double quantity,
            String unit,
            String category) {

        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getCategory() {
        return category;
    }
}