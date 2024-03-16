import java.time.LocalDateTime;

public class PrinterTaskRunner extends AbstractTaskRunner{

    public PrinterTaskRunner(TaskRunner runner){
        super(runner);
    }

    @Override
    public void executeOneTask() {
        runner.executeOneTask();
        decorateExecuteOneTask();
    }

    private void decorateExecuteOneTask(){
        System.out.println("Task executed at " + LocalDateTime.now().format(MessageTask.DATE_FORMATTER));
    }

}
