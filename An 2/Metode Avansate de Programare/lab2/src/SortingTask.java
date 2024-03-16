import java.util.Arrays;
import java.util.Random;

public class SortingTask extends Task {

    public int[] array = new int[0];
    private AbstractSorter taskSorter;

    public SortingTask(String taskId, String description, int[] arr, SortStrategy strategy) {
        super(taskId, description);
        array = arr;

        if(strategy == SortStrategy.QUICK)
            this.taskSorter = new QuickSort();
        else
            this.taskSorter = new BubbleSort();
    }

    public void sort() {
      /* Random r = new Random();

         Arrays.sort(array);

        if(r.nextInt() % 2 == 1) {
            taskSorter = new BubbleSort();
            taskSorter.sort(array);
        }
        else {
            taskSorter = new QuickSort();
            taskSorter.sort(array);
        }*/

        taskSorter.sort(array);
    }
    @Override
    public void execute() {
        sort();
        System.out.println("The array is:");
        for(int x : array)
            System.out.println(x);
    }
}