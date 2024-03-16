import java.util.ArrayList;

public class QueueContainer extends ContainerSuperclass {
    public QueueContainer(){
        super();
    }

    @Override
    public Task remove(){
        if(size == 0)
            return null;
        Task t = tasks[0];
        for(int i = 0; i < size-1; i++){
            tasks[i] = tasks[i+1];
        }
        size--;
        return t;
    }


}
