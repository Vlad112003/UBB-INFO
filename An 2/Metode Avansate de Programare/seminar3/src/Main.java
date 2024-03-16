import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Dan", 4.5f);
        Student s2 = new Student("Ana", 8.5f);
        Student s3 = new Student("Dan", 4.5f);
        Student s4 = new Student("Marius", 6.5f);

        HashSet<Student> set = new HashSet<>();

        set.add(s1);
        set.add(s2);
        set.add(s3);

        System.out.println(set);


        Set<Student> tree = new TreeSet<>();
        tree.add(s1);
        tree.add(s2);
        tree.add(s3);
        tree.add(s4);

        System.out.println(tree);

//        Map<String, Student> hash = new HashMap<>();
        //treemap ordoneaza automat dupa cheie, nu dupa valoare
        Map<String, Student> hash = new TreeMap<>();
        hash.put(s1.getNume(), s1);
        hash.put(s2.getNume(), s2);
        hash.put(s3.getNume(), s3);
        hash.put(s4.getNume(), s4);

        for(String i : hash.keySet()){
            System.out.println(hash.get(i));
        }

        System.out.print("\n");

        for(Map.Entry<String, Student> i : hash.entrySet()) {
            System.out.println(i.getValue());
        }

        MyMap mapa = new MyMap();

        mapa.add(s1);
        mapa.add(s2);
        mapa.add(s3);
        mapa.add(s4);

        System.out.print("\n");

        System.out.println(mapa.findAll());


        System.out.print("\n");
        
        for(Map.Entry<Integer, List<Student>> i : mapa.findAll()){
            List<Student> students = i.getValue();
            Collections.sort(students, Comparator.comparing(Student::getNume));
            System.out.println(students);
        }
    }
 }
