import java.util.*;

import static java.lang.Math.abs;
import static java.lang.System.out;


// a Class to find the first numbers in an array that sum K, an Integer number
public class ArraySearchKSum {
    private static final List<List<Integer>> possible_index = new ArrayList<>();
    private static final Map<Integer, List<Integer>> combinations = new HashMap<>();
    private static final Map<Integer, List<Integer>> registry = new HashMap<>();

    private static int k_number;
    @SafeVarargs
    private static <T> List<T> mutateAdd(final List<T> al, final T... ts) {
        final List<T> bl = new ArrayList<>(al);
        Collections.addAll(bl, ts);
        return List.copyOf(bl);
    }

    static List<Integer> combinations_keys_iteration(Integer a_number, int index) {
        Map<Integer, List<Integer>> temp_combinations = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> combination : combinations.entrySet()) {
            Integer saved_combination = combination.getKey();
            List<Integer> value = combination.getValue();
            if (a_number + saved_combination == k_number) return value;
            if (abs(a_number + saved_combination) < abs(k_number)) {
                temp_combinations.put(a_number + saved_combination, mutateAdd(value, index));
            }
        }
        temp_combinations.forEach((key, value) ->
                combinations.merge(key, value, (v1, v2) -> v1));
        return new ArrayList<>();
    }

    static List<Integer> registry_keys_iteration(Integer a_number, int index) {
        for (Integer saved : registry.keySet()) {
            if (a_number + saved == k_number) return List.of(registry.get(saved).get(0));
            if (abs(a_number + saved) < abs(k_number) && combinations.get(a_number + saved) == null) {
                combinations.put(a_number + saved, List.of(registry.get(saved).get(0), index));
            }
        }
        return Collections.emptyList();
    }

    static boolean no_success_with_registred() {
        return possible_index.get(1).isEmpty() || (!possible_index.get(0).isEmpty() && possible_index.get(1).get(0) > possible_index.get(0).get(0));
    }

    static List<Integer> search_solution(Integer index) {
        return mutateAdd(possible_index.get(no_success_with_registred() ? 0 : 1),index);
    }

    static List<Integer> process_number(int a_number, int index) {
        possible_index.add(combinations_keys_iteration(a_number, index));
        possible_index.add(registry_keys_iteration(a_number, index));
        if (!(possible_index.stream()
                .mapToLong(List::size).sum() == 0)) return search_solution(index);
        possible_index.clear();
        return null;
    }
    //receive an array like '1 2 3', and a number, returns the expected array of indexes
    public static void main(String[] args) {
        k_number = Integer.parseInt(args[0]);
        final String[] num = args[1].split(" ");
        final List<Integer> numbers = new ArrayList<>();
        for (final String cur : num) {
            numbers.add(Integer.valueOf(cur));
        }
        Integer[] the_array = numbers.toArray(new Integer[0]);
        for (int index = 0; index < the_array.length; index++) {
            int a_number = the_array[index];
            if (k_number - a_number == 0) {
                out.println(index);
                return;
            }
            var result = process_number(a_number, index);
            if (result != null) {
                out.println(result);
                return;
            }
            int finalIndex = index;
            registry.computeIfAbsent(a_number, k -> List.of(finalIndex));
        }
        out.println("no se encontro resultado");
    }
}