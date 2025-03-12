package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;


    /**
     * Representa una persona con sus atributos y una colección de coches.
     * Esta clase combina elementos de programación orientada a objetos con
     * principios funcionales como inmutabilidad y métodos basados en predicados.
     */
    public class Person {
        private final UUID id;
        private final String name;
        private final String lastName;
        private final int age;
        private final LocalDate birthDate;
        private final String email;
        private final String phoneNumber;
        private final Gender gender;
        private final Address address;
        private final List<Car> cars;

        public enum Gender {
            MALE, FEMALE, OTHER
        }

        /**
         * Clase interna inmutable para representar direcciones.
         */
        public record Address(
                String street,
                String city,
                String state,
                String zipCode,
                String country
        ) {
            /**
             * Constructor compacto con validación.
             */
            public Address {
                if (street == null || street.isBlank()) {
                    throw new IllegalArgumentException("Street cannot be null or empty");
                }
                if (city == null || city.isBlank()) {
                    throw new IllegalArgumentException("City cannot be null or empty");
                }
                if (state == null || state.isBlank()) {
                    throw new IllegalArgumentException("State cannot be null or empty");
                }
                if (zipCode == null || zipCode.isBlank()) {
                    throw new IllegalArgumentException("Zip code cannot be null or empty");
                }
                if (country == null || country.isBlank()) {
                    throw new IllegalArgumentException("Country cannot be null or empty");
                }
            }

            /**
             * Método para obtener una representación formateada de la dirección.
             */
            public String getFormattedAddress() {
                return String.format("%s, %s, %s %s, %s", street, city, state, zipCode, country);
            }
        }

        /**
         * Constructor principal con todos los atributos.
         */
        public Person(UUID id, String name, String lastName, int age, LocalDate birthDate, String email,
                      String phoneNumber, Gender gender, Address address, List<Car> cars) {
            this.id = id != null ? id : UUID.randomUUID();
            this.name = Objects.requireNonNull(name, "Name cannot be null");
            this.lastName = Objects.requireNonNull(lastName, "Last name cannot be null");

            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            this.age = age;

            this.birthDate = Objects.requireNonNull(birthDate, "Birth date cannot be null");
            this.email = Objects.requireNonNull(email, "Email cannot be null");
            this.phoneNumber = phoneNumber;
            this.gender = Objects.requireNonNull(gender, "Gender cannot be null");
            this.address = address;

            // Crea una copia defensiva de la lista de coches
            this.cars = cars != null ? new ArrayList<>(cars) : new ArrayList<>();
        }

        /**
         * Constructor conveniente para crear una persona con un ID generado automáticamente.
         */
        public Person(String name, String lastName, int age, LocalDate birthDate, String email,
                      String phoneNumber, Gender gender, Address address, List<Car> cars) {
            this(UUID.randomUUID(), name, lastName, age, birthDate, email, phoneNumber, gender, address, cars);
        }

        /**
         * Constructor conveniente para crear una persona sin coches.
         */
        public Person(String name, String lastName, int age, LocalDate birthDate, String email,
                      String phoneNumber, Gender gender, Address address) {
            this(UUID.randomUUID(), name, lastName, age, birthDate, email, phoneNumber, gender, address, Collections.emptyList());
        }

        // Getters que mantienen la inmutabilidad

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFullName() {
            return name + " " + lastName;
        }

        public int getAge() {
            return age;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public Gender getGender() {
            return gender;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Retorna una copia inmodificable de la lista de coches para mantener la inmutabilidad.
         */
        public List<Car> getCars() {
            return Collections.unmodifiableList(cars);
        }

        /**
         * Método funcional para filtrar coches según un predicado.
         */
        public List<Car> filterCars(Predicate<Car> predicate) {
            return cars.stream()
                    .filter(predicate)
                    .toList();
        }

        /**
         * Método funcional para encontrar el coche más caro.
         */
        public Optional<Car> findMostExpensiveCar() {
            return cars.stream()
                    .max((c1, c2) -> Double.compare(c1.price(), c2.price()));
        }

        /**
         * Método para calcular el valor total de los coches de la persona.
         */
        public double calculateTotalCarValue() {
            return cars.stream()
                    .mapToDouble(Car::price)
                    .sum();
        }

        /**
         * Método para verificar si la persona tiene un coche eléctrico.
         */
        public boolean hasElectricCar() {
            return cars.stream()
                    .anyMatch(Car::isElectric);
        }

        /**
         * Crea una nueva Person con un coche adicional, manteniendo la inmutabilidad.
         */
        public Person addCar(Car car) {
            Objects.requireNonNull(car, "Car cannot be null");
            List<Car> newCars = new ArrayList<>(this.cars);
            newCars.add(car);
            return new Person(id, name, lastName, age, birthDate, email, phoneNumber, gender, address, newCars);
        }

        /**
         * Crea una nueva Person con una lista de coches actualizada, manteniendo la inmutabilidad.
         */
        public Person withCars(List<Car> newCars) {
            return new Person(id, name, lastName, age, birthDate, email, phoneNumber, gender, address, newCars);
        }

        /**
         * Crea una nueva Person con una dirección actualizada, manteniendo la inmutabilidad.
         */
        public Person withAddress(Address newAddress) {
            return new Person(id, name, lastName, age, birthDate, email, phoneNumber, gender, newAddress, cars);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(id, person.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", age=" + age +
                    ", birthDate=" + birthDate +
                    ", email='" + email + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", gender=" + gender +
                    ", address=" + address +
                    ", cars=" + cars +
                    '}';
        }
}
