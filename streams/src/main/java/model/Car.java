package model;

import java.util.UUID;

/**
 * Representa un automóvil como un record inmutable.
 * Los records en Java proporcionan una forma concisa de crear clases inmutables
 * que son principalmente portadoras de datos.
 */

public record Car(UUID id,
                  String make,
                  String model,
                  String color,
                  int year,
                  double price,
                  String vin,
                  FuelType fuelType,
                  int horsePower) {

    /**
     * Enumeración de tipos de combustible.
     */
    public enum FuelType {
        GASOLINE, DIESEL, ELECTRIC, HYBRID, HYDROGEN
    }

    /**
     * Constructor compacto con validación.
     * Demuestra cómo se pueden agregar validaciones en un record.
     */
    public Car {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (make == null || make.isBlank()) {
            throw new IllegalArgumentException("Make cannot be null or empty");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        if (year < 1900) {
            throw new IllegalArgumentException("Year must be at least 1900");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (vin == null || vin.isBlank()) {
            throw new IllegalArgumentException("VIN cannot be null or empty");
        }
        if (fuelType == null) {
            throw new IllegalArgumentException("Fuel type cannot be null");
        }
        if (horsePower < 0) {
            throw new IllegalArgumentException("Horse power cannot be negative");
        }
    }

    /**
     * Constructor conveniente para crear un coche con un ID generado automáticamente.
     */
    public Car(String make, String model, String color, int year, double price, String vin, FuelType fuelType, int horsePower) {
        this(UUID.randomUUID(), make, model, color, year, price, vin, fuelType, horsePower);
    }

    /**
     * Método de utilidad para crear una representación formateada del coche.
     * Los records pueden tener métodos adicionales.
     */
    public String getFullDescription() {
        return String.format("%s %s (%d) - %s - %s - %dHP - $%.2f",
                make, model, year, color, fuelType, horsePower, price);
    }

    /**
     * Método que evalúa si el coche es un vehículo eléctrico.
     * Demuestra cómo los records pueden tener métodos con lógica.
     */
    public boolean isElectric() {
        return fuelType == FuelType.ELECTRIC || fuelType == FuelType.HYBRID;
    }

    /**
     * Método para calcular el valor estimado del coche basado en su antigüedad.
     */
    public double calculateEstimatedValue(int currentYear) {
        int age = currentYear - year;
        double depreciationFactor = switch (fuelType) {
            case ELECTRIC -> 0.1;
            case HYBRID -> 0.12;
            case GASOLINE -> 0.15;
            case DIESEL -> 0.13;
            case HYDROGEN -> 0.08;
        };

        return price * Math.pow(1 - depreciationFactor, age);
    }
}
