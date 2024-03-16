import java.util.*;

public class MyMap {
    private TreeMap<Integer, List<Student>> tree = new TreeMap<>(Comparator.comparingInt(k -> k));

    public void add(Student s){
       int x = Math.round(s.getGrade());
       List<Student> list = tree.get(x);
       if(list == null){
           list = new ArrayList<>();
           tree.put(x, list);
       }
       list.add(s);
    }
    public Set<Map.Entry<Integer, List<Student>>> findAll(){
        return tree.entrySet();
    }
}
