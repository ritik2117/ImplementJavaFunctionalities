package interviewCodingQuestions;

import java.util.*;

/**
 * Given an array of integers having duplicate values
 * Return the sorted array according to the count of each value i.e.
 * The value with greater count will come first
 * and if count is same, then the value which is greater comes first.
 * In output: print the distinct values
 * TC: Input: 5, 3, 5, 2, 8, 8, 8, 3, 2, 2, 2, 5
 *     Output: 2, 8, 5, 3
 */
public class SortElementsCountBased {

    public static int[] sortByFrequency(int[] arr) {
//        Create a map to store the frequency of each element
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : arr) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

//        Create a list of unique elements
        List<Integer> uniqueElements = new ArrayList<>(frequencyMap.keySet());

//        Sort the list with a custom comparator
        Comparator<Integer> integerComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer n1, Integer n2) {
                int freqCompare = frequencyMap.get(n2).compareTo(frequencyMap.get((n1)));
                if (freqCompare == 0) {
                    return n2.compareTo(n1);
                } else {
                    return freqCompare;
                }
            }
        };
        Collections.sort(uniqueElements, integerComparator);
//        Using Lambda expressions
        /*Collections.sort(uniqueElements, (n1, n2) -> {
            int freqCompare = frequencyMap.get(n2).compareTo(frequencyMap.get((n1)));
            if (freqCompare == 0) {
                return n2.compareTo(n1);
            } else {
                return freqCompare;
            }
        });*/
//        Convert the sorted list back to an array
        int[] sortedArray = new int[uniqueElements.size()];
        for (int i = 0; i < uniqueElements.size(); i++) {
            sortedArray[i] = uniqueElements.get(i);
        }
        return sortedArray;
    }

    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
        int arr[] = {5, 3, 5, 2, 8, 8, 8, 3, 2, 2, 2, 5};
        int[] sortedArray = sortByFrequency(arr);
        System.out.println(Arrays.toString(sortedArray));
    }
}
