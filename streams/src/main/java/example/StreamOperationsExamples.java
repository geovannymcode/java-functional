package example;

import model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamOperationsExamples {

    public static void main(String[] args) {
        // Crear datos de ejemplo
        List<Person> people = createSamplePeople();

        System.out.println("===== Ejemplos de Operaciones con Streams =====");

        // Ejemplo básico como el proporcionado
        basicExample();
    }

    private static void basicExample() {
        System.out.println("\n----- Ejemplo Básico -----");
        List<String> names = Arrays.asList("Geovanny", "Elena", "Maria", "Omar", "Gabriel", "Eva");

        List<String> filteredNames = names.stream()
                .filter(name -> name.startsWith("G") || name.startsWith("E"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());

        System.out.println("Nombres originales: " + names);
        System.out.println("Nombres filtrados y transformados: " + filteredNames);
    }
}
