package example;

import MockData.MockData;
import model.Person;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamOperationsExamples {

    public static void main(String[] args) throws IOException {
        // Crear datos de ejemplo
        List<Person> people = MockData.getPeople();

        System.out.println("===== Ejemplos de Operaciones con Streams =====");

        // Ejemplo básico como el proporcionado
        basicExample(people);
    }

    private static void basicExample(List<Person> people) {
        System.out.println("\n----- Ejemplo Básico -----");

        List<String> filteredNames = people.stream()
                .map(Person::getName)
                .filter(name -> name.startsWith("G") || name.startsWith("E"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());

        System.out.println("Total de personas cargadas: " + people.size());
        System.out.println("Nombres originales (primeros 5): " +
                people.stream().limit(5).map(Person::getName).collect(Collectors.toList()));
        System.out.println("Nombres filtrados y transformados: " + filteredNames);
    }
}
