package sorts;

public class InsertionSort extends Sort {

    private void insertionSort(int[] data) {
        int key, prevIndex;
        for(int i = 0; i < data.length; i++) {
            key = data[i];
            prevIndex = i - 1;
            while(incBasicOpCountInLoop() && prevIndex > -1 && data[prevIndex] > key) {
                data[prevIndex+1] = data[prevIndex];
                prevIndex--;
            }
            data[prevIndex+1] = key;
        }
    }

    @Override
    public void sort(int[] data) {
        resetBasicOpCount();
        insertionSort(data);
    }

    @Override
    public String getSortName() {
        return "Insertionsort";
    }

}
