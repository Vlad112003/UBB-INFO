public class BubbleSort implements AbstractSorter{

    @Override
    public void sort(int arr[]){
        boolean ok = true;
        while(ok){
            ok = false;
            for(int i=0; i<arr.length-1; ++i){
                if(arr[i] > arr[i+1]){
                    ok = true;
                    int aux = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = aux;

                }
            }
        }
    }
}
