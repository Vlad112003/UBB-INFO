

public abstract class ContainerSuperclass implements Container{

    protected Task[] tasks;
    protected int size;

    public ContainerSuperclass(){
        size = 0;
        tasks = new Task[10];
    }

    public void add(Task t){
        if(size == tasks.length){
            Task[] t2 = new Task[tasks.length*2];
            System.arraycopy(tasks, 0, t2, 0, tasks.length);
            tasks = t2;
        }
        tasks[size] = t;
        size++;
    }
    @Override
    public abstract Task remove();

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
}
