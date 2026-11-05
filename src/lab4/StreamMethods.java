package lab4;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class StreamMethods {

    private StreamMethods() {
    }

    public static double average(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Нельзя посчитать среднее пустого списка"));
    }

    public static List<String> toUpperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(s -> "_new_" + s.toUpperCase())
                .toList();
    }

    public static List<Integer> squaresOfUnique(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(entry -> entry.getKey() * entry.getKey())
                .toList();
    }

    public static <T> T last(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));
    }

    public static int sumOfEven(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    public static Map<Character, String> toFirstCharMap(List<String> strings) {
        return strings.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.substring(1),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new));
    }
}
