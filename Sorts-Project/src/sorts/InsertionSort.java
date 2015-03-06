package sorts;

public class InsertionSort extends Sort {

    public void InsertionSort(int[] data) {
        int key, prevIndex;
        for(int i = 0; i < data.length; i++) {
            key = data[i];
            prevIndex = i - 1;
            while( prevIndex > -1 && data[prevIndex] > key) {
                //Incremenet Basic Op count(Comparrisons)
                incBasicOpCount();
                data[prevIndex+1] = data[prevIndex];
                prevIndex--;
            }
            data[prevIndex+1] = key;
        }
    }

    @Override
    public void sort(int[] data) {
        resetBasicOpCount();
        InsertionSort(data);
        addTotalOpCount(getLastBasicOpCount());
    }

    @Override
    public String getSortName() {
        return "Insertionsort";
    }

    @Override
    public void printDiagnostics() {
        System.out.println("Total extra memory: "/**/);
    }

}
