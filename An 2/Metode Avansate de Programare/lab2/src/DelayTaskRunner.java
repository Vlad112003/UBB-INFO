import java.time.LocalDateTime;

public class DelayTaskRunner extends AbstractTaskRunner{
    public DelayTaskRunner(TaskRunner runner){
        super(runner);
    }

    @Override
    public void executeOneTask(){
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        runner.executeOneTask();
        decorateExecuteOneTask();
    }

    private void decorateExecuteOneTask(){
        System.out.println("Task executed at " + LocalDateTime.now().format(MessageTask.DATE_FORMATTER));
    }
}
