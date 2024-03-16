public final class TaskContainerFactory implements Factory{


    private static TaskContainerFactory INSTANCE;

    TaskContainerFactory(){}

    public synchronized static TaskContainerFactory getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TaskContainerFactory();
        }
        return INSTANCE;
    }

    @Override
    public Container createContainer(ContainerStrategy strategy) {
        switch(strategy){
            case FIFO:
                return new QueueContainer();
            case LIFO:
                return new StackContainer();
            default:
                throw new UnsupportedOperationException("The container strategy " + strategy + " is not implemented");
        }
    }


}