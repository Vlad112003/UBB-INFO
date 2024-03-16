import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.*;
import java.util.stream.Stream;

public class Main {

    private static <E> void printArea(List<E> l, Area<E> area){
        for(E i : l){
            System.out.println(area.compute(i));
        }
    }

    private static void testPredicate(){

        Circle c1 = new Circle(12);
        Circle c2 = new Circle(4);

        List<Circle> list = Arrays.asList(c1, c2);

        //filtreaza cercurile cu raza mai mica decat 10
        Predicate<Circle> smallCircle = c -> c.getRadius() < 10;

        //filtreaza in lista cu ajutorul predicatului
        for(Circle i : list){
            if(smallCircle.test(i)){
                System.out.println(i);
            }
        }
        System.out.println();

        Predicate<Circle> largeCircle = smallCircle.negate();
        for(Circle i : list){
            if(largeCircle.test(i)){
                System.out.println(i);
            }
        }
        System.out.println();

        //predicat care zice ca cercul e mic si raza e un numar par
        Predicate<Circle> smallEven = smallCircle.and(c -> c.getRadius() % 2 == 0);
        for(Circle i : list){
            if(smallEven.test(i)){
                System.out.println(i);
            }
        }
    }

    private static void testFunction(){
        //functie
        Function<String, Integer> f = s->Integer.valueOf(s);

        //method reference (caz special de lambda)
        Function<String,Integer> f2 = Integer::valueOf;

        //compunere de functii
        Function<String, Integer> f3 = f.andThen(i -> i * i);

        System.out.println(f.apply("123"));
        System.out.println();
        System.out.println(f2.apply("123"));
        System.out.println();
        System.out.println(f3.apply("12"));


    }

    private static void testSupplier(){
        Supplier<LocalDateTime> sup = () -> LocalDateTime.now();
        System.out.println(sup.get());

        Supplier<LocalDateTime> sup2 = LocalDateTime::now;
        System.out.println(sup2.get());

        Supplier<List<String>> sup3 = () -> new ArrayList<>();
        Supplier<List<String>> sup4 = ArrayList::new;

    }

    private static void testComparator(){
        List<Integer> list;
        list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Comparator<Integer> descComp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        };

        Comparator<Integer> descComp2 = (o1, o2) -> o2-o1;

        list.sort(descComp);

        System.out.println(list);

        list.sort(descComp.reversed());

        System.out.println(list);

        list.sort(descComp2);

        System.out.println(list);


    }
    private static <E> List<E> filterGeneric(List<E> list, Predicate<E> pred, Comparator<E> comp){
        return list.stream().filter(pred).sorted(comp).collect(Collectors.toList());
    }
    private static void testStream(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        //list=filterGeneric<list, x->x%2==0);

        list=filterGeneric(list, x->x%2==0, (x,y)->y-x);
        System.out.println(list);

    }
    public static void main(String[] args) {

        Circle c1 = new Circle(12);
        Circle c2 = new Circle(3.14);

        List<Circle> list = Arrays.asList(c1, c2);

        //functie anonima
        Area<Circle> a1 = new Area<Circle>() {
            @Override
            public double compute(Circle shape) {
                return Math.PI * shape.getRadius() * shape.getRadius();
            }
        };

        //functie lambda
        Area<Circle> a2 = shape -> Math.PI * shape.getRadius() * shape.getRadius();

        printArea(list, a1);

        System.out.println();

        testPredicate();
        System.out.println();
        System.out.println();

        testFunction();
        System.out.println();
        System.out.println();
        testSupplier();
        System.out.println();
        System.out.println();
        testComparator();
        System.out.println();
        testStream();

    }
}