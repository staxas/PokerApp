import java.util.Arrays;
import java.util.List;

public class SortTwoDimensionalArray {
    DeckOfCards deckOfCards = new DeckOfCards();

    List<String> ranksList = Arrays.asList(deckOfCards.RANKS);

    private int partitionCards(String[][] arr, int low, int high) {
        // pivot (Element to be placed at right position)
        int pivot = ranksList.indexOf(arr[high][0]);

        int i = (low - 1); // Index of smaller element

        for (int j = low; j <= high - 1; j++) {
            // If current element is smaller than the pivot
            if (ranksList.indexOf(arr[j][0]) < pivot) {
                i++;    // increment index of smaller element
                String[] tmpArr = new String[2];
                tmpArr = arr[i];
                arr[i] = arr[j];
                arr[j] = tmpArr;
            }
        }
        String[] tmpArr = new String[2];
        tmpArr = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = tmpArr;

        return (i + 1);
    }
    private int partition(Integer[][] arr, int low, int high) {
        // pivot (Element to be placed at right position)
        int pivot = arr[high][0];

        int i = (low - 1); // Index of smaller element

        for (int j = low; j <= high - 1; j++) {
            // If current element is smaller than the pivot
            if (arr[j][0] < pivot) {
                i++;    // increment index of smaller element
                Integer[] tmpArr = new Integer[2];
                tmpArr = arr[i];
                arr[i] = arr[j];
                arr[j] = tmpArr;
            }
        }
        Integer[] tmpArr = new Integer[2];
        tmpArr = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = tmpArr;

        return (i + 1);
    }
    public void quickSortCards(String[][] arr, int low, int high) {
        if (low < high) {
        /* pi is partitioning index, arr[pi] is now
           at right place */
            int pi = partitionCards(arr, low, high);

            this.quickSortCards(arr, low, pi - 1);  // Before pi
            this.quickSortCards(arr, pi + 1, high); // After pi
        }
    }

    public void quickSort(Integer[][] arr, int low, int high) {
        if (low < high) {
        /* pi is partitioning index, arr[pi] is now
           at right place */
            int pi = partition(arr, low, high);

            this.quickSort(arr, low, pi - 1);  // Before pi
            this.quickSort(arr, pi + 1, high); // After pi
        }
    }

}
