package Interview_Synposis;

public class StaircaseProblem {
    public static void main(String[] args) {
        int n = 8;
//        now[N-1] + now[N-2]
        int a = 1;
        int b = 1;
        int noOfWaysAtI = 0;
        for (int i=3; i<=n; i++) {
            noOfWaysAtI = a + b;
            a = b;
            b = noOfWaysAtI;
        }
        System.out.println(noOfWaysAtI);

        /***
         * Length of longest substring without repeating any character
         */
        /*int arr[26] = -1;
        int currentLength = 0;
        int maxLength = 1;
        int i = 0;
        while (j < str.length && i <= j) {
            if (arr[str[j]-'a'] != -1) {
                arr[str[j]-'a'] = j;
                currentLength++;
            } else {
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                }
                i = str[j-'a']+1;
                str[j-'a'] = j;
                currentLength = j-i+1;
            }
        }*/
    }
}
/**
 *
 */
