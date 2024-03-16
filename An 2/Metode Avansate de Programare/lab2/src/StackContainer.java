import java.util.ArrayList;

public class StackContainer extends ContainerSuperclass{

    public StackContainer(){
        super();
    }

    @Override
    public Task remove(){
        if(size == 0)
            return null;
        Task t = tasks[size-1];
        size--;
        return t;
    }

}

