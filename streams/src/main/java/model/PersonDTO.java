package model;

import java.time.LocalDate;
import java.util.List;

/**
     * DTO (Data Transfer Object) para la clase Person.
     * Implementado como un record para demostrar la inmutabilidad y concisión.
     */
    public record PersonDTO(
            String fullName,
            int age,
            LocalDate birthDate,
            String email,
            String phoneNumber,
            String gender,
            String address,
            List<CarSummaryDTO> cars
    ) {
        /**
         * Record anidado para representar un resumen de información de coche.
         */
        public record CarSummaryDTO(
                String makeAndModel,
                int year,
                String color,
                double price
        ) {}

        /**
         * Constructor compacto con validación.
         */
        public PersonDTO {
            if (fullName == null || fullName.isBlank()) {
                throw new IllegalArgumentException("Full name cannot be null or empty");
            }
            if (email == null) {
                throw new IllegalArgumentException("Email cannot be null");
            }
            // Defensive copy para la lista de coches
            cars = List.copyOf(cars != null ? cars : List.of());
        }

        /**
         * Método factory para crear un PersonDTO a partir de un objeto Person.
         * Demuestra el uso de streams y operaciones funcionales.
         */
        public static PersonDTO fromPerson(Person person) {
            List<CarSummaryDTO> carSummaries = person.getCars().stream()
                    .map(car -> new CarSummaryDTO(
                            car.make() + " " + car.model(),
                            car.year(),
                            car.color(),
                            car.price()))
                    .toList();

            String addressString = person.getAddress()
                    .map(Person.Address::getFormattedAddress)
                    .orElse("No address provided");

            return new PersonDTO(
                    person.getFullName(),
                    person.getAge(),
                    person.getBirthDate(),
                    person.getEmail(),
                    person.getPhoneNumber(),
                    person.getGender().toString(),
                    addressString,
                    carSummaries
            );
        }

        /**
         * Método para filtrar coches por un criterio específico.
         * Demuestra cómo los records pueden tener métodos funcionales.
         */
        public List<CarSummaryDTO> filterCars(java.util.function.Predicate<CarSummaryDTO> predicate) {
            return cars.stream()
                    .filter(predicate)
                    .toList();
        }

        /**
         * Método para calcular el valor total de los coches.
         */
        public double calculateTotalCarValue() {
            return cars.stream()
                    .mapToDouble(CarSummaryDTO::price)
                    .sum();
        }

        /**
         * Método para encontrar el coche más caro.
         */
        public java.util.Optional<CarSummaryDTO> findMostExpensiveCar() {
            return cars.stream()
                    .max((c1, c2) -> Double.compare(c1.price(), c2.price()));
        }
}
