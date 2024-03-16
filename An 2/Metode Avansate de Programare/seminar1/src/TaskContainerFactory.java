public class TaskContainerFactory implements Factory{
    @Override
    public Container createContainer(ContainerStrategy strategy) {
        return switch(strategy){
            case FIFO -> new QueueContainer();
            case LIFO -> new StackContainer();
        };
    }
}
