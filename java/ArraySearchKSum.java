import java.util.*;

import static java.lang.Math.abs;
import static java.lang.System.out;


// a Class to find the first numbers in an array that sum K, an Integer number
public class ArraySearchKSum {
    private static final List<List<Integer>> possible_index = new ArrayList<>();
    private static final Map<Integer, List<Integer>> combinations = new HashMap<>();
    private static final Map<Integer, List<Integer>> registry = new HashMap<>();

    private static int kNumber;
    @SafeVarargs
    private static <T> List<T> mutateAdd(final List<T> al, final T... ts) {
        final List<T> bl = new ArrayList<>(al);
        Collections.addAll(bl, ts);
        return List.copyOf(bl);
    }

    static List<Integer> combinationsKeysIteration(Integer aNumber, int index) {
        Map<Integer, List<Integer>> tempCombinations = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> combination : combinations.entrySet()) {
            Integer combinationKey = combination.getKey();
            List<Integer> value = combination.getValue();
            if (aNumber + combinationKey == kNumber) return value;
            if (abs(aNumber + combinationKey) < abs(kNumber)) {
                tempCombinations.put(aNumber + combinationKey, mutateAdd(value, index));
            }
        }
        tempCombinations.forEach((key, value) ->
                combinations.merge(key, value, (v1, v2) -> v1));
        return new ArrayList<>();
    }

    static List<Integer> registryKeysIteration(Integer aNumber, int index) {
        int savedKey;
        for (Map.Entry<Integer,List<Integer>> entry : registry.entrySet()) {
            savedKey= entry.getKey();
            if (aNumber + savedKey == kNumber) return List.of(entry.getValue().get(0));
            if (abs(aNumber + savedKey) < abs(kNumber) && combinations.get(aNumber + savedKey) == null) {
                combinations.put(aNumber + savedKey, List.of(entry.getValue().get(0), index));
            }
        }
        return Collections.emptyList();
    }

    static boolean noSuccessWithRegistred() {
        return possible_index.get(1).isEmpty() || (!possible_index.get(0).isEmpty() && possible_index.get(1).get(0) > possible_index.get(0).get(0));
    }

    static List<Integer> searchSolution(Integer index) {
        return mutateAdd(possible_index.get(noSuccessWithRegistred() ? 0 : 1),index);
    }

    static List<Integer> processNumber(int aNumber, int index) {
        possible_index.add(combinationsKeysIteration(aNumber, index));
        possible_index.add(registryKeysIteration(aNumber, index));
        if ((possible_index.stream()
                .mapToLong(List::size).sum() != 0)) return searchSolution(index);
        possible_index.clear();
        return null;
    }
    //receive an array like '1 2 3', and a number, returns the expected array of indexes
    public static void main(String[] args) {
        kNumber = Integer.parseInt(args[0]);
        final String[] num = args[1].split(" ");
        final List<Integer> numbers = new ArrayList<>();
        for (final String cur : num) {
            numbers.add(Integer.valueOf(cur));
        }
        Integer[] theArray = numbers.toArray(new Integer[0]);
        for (int index = 0; index < theArray.length; index++) {
            int aNumber = theArray[index];
            if (kNumber - aNumber == 0) {
                out.println(index);
                return;
            }
            var result = processNumber(aNumber, index);
            if (result != null) {
                out.println(result);
                return;
            }
            int finalIndex = index;
            registry.computeIfAbsent(aNumber, k -> List.of(finalIndex));
        }
        out.println("no se encontro resultado");
    }
}