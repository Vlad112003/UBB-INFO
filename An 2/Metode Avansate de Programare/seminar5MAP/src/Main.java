import java.util.Arrays;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static final List<String> list = Arrays.asList("asf", "bcd", "asd", "bed", "bbb");

    private static void testParallelStreams(){
        list.parallelStream().filter(s->{
            System.out.println(Thread.currentThread().getName() + "filter " + s);
            return s.startsWith("b");
        }).map(s->{
            System.out.println("map ");
            return s.toUpperCase();
        }).forEachOrdered(s->
                System.out.println(Thread.currentThread()+ "forEach: " + s));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        list.stream().filter(s->{
            System.out.println("filter ");
            return s.startsWith("b");
        }).map(s->{
            System.out.println("map ");
            return s.toUpperCase();
        }).reduce((s1, s2)->{
            System.out.println("reduce ");
            return s1 + s2;
        }).ifPresent(System.out::println);


//        list.parallelStream().filter(s->{
//            System.out.println(Thread.currentThread().getName() + "filter " + s);
//            return s.startsWith("b");
//        }).map(s->{
//            System.out.println("map ");
//            return s.toUpperCase();
//        }).forEachOrdered(s->
//                System.out.println(Thread.currentThread()+ "forEach: " + s));
//
//        new ForkJoinPool(2).submit(Main::testParallelStreams).get();

    }



}