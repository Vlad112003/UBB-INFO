import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        MessageTask task1 = new MessageTask("id1", "desc1", "message1", "from", "to", LocalDateTime.now());
        MessageTask task2 = new MessageTask("id2", "desc2", "message2", "from", "to", LocalDateTime.now());
        MessageTask task3 = new MessageTask("id3", "desc3", "message3", "from", "to", LocalDateTime.now());

        MessageTask[] tasks = new MessageTask[]{task1,task2,task3};

//        for(MessageTask i : tasks){
//            System.out.println(i);
//        }

        StrategyTaskRunner runner = new StrategyTaskRunner(ContainerStrategy.valueOf(args[0]));

        for (MessageTask messageTask : tasks) {
            runner.addTask(messageTask);
        }
        runner.executeAll();

        //11. decorator (am ramas)
    }
}
