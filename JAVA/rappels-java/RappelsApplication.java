import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RappelsApplication {
    // public static void main(String[] args) {
    public static void main(String... args) {
        System.out.println("Hello world!");
        // main(new String[] { "test", "test2" });
        // main("test", "test2");

        Integer a = null;
        int b = 0; // Entier sur 4 octets

        boolean c; // 1 bit mais sur 1 octet
        // Boolean
        char d; // Caractère sur 2 octets
        // Character

        float e; // Flottant sur 4 octets
        // Float
        byte f; // Entier sur 1 octet
        // Byte
        double g; // Flottant sur 8 octets
        // Double
        long h; // Entier sur 8 octets
        // Long
        short i; // Entier sur 2 octets
        // Short



        // Notations
        // 5L / 5d / 5D / 5.0

        String var = "toto";
        System.out.println("Bonjour " + var);

        System.out.println(String.format("Bonjour %s", var));


        // Fonctionel
        // (arg1) -> { }
        addition(5, 6);

        BiFunction<Integer, Integer, Integer> mathsAdd = (l, m) -> {
            return l + m;
        };

        BiFunction<Integer, Integer, Integer> mathsSup = (l, m) -> l - m;

        BiFunction<Integer, Integer, Integer> mathsFonction = RappelsApplication::addition;

        List<String> prenoms = List.of("Jérémy", "Nicolas", "Albert");

        prenoms.forEach(System.out::println);

        prenoms.forEach(p -> {
            if (p.contains("r")) {
                System.out.println(p);
            }
        });

        prenoms.stream()
            .filter(p -> p.contains("r"))
            // .map(p -> p.toUpperCase())
            .map(String::toUpperCase)
            .forEach(System.out::println)
        ;
    }

    public static int addition(int a, Integer b) {
        return a + b;
    }

    public static void maths(BiFunction<Integer, Integer, Integer> m) {
        m.apply(5, 7);
    }
}
