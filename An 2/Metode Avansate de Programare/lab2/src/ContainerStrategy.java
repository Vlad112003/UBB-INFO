public enum ContainerStrategy {

    FIFO {
        @Override
        public Container getContainer() {
            return new QueueContainer();
        }
    },
    LIFO {
        @Override
        public Container getContainer() {
            return new StackContainer();
        }
    };

    public abstract Container getContainer();
}
