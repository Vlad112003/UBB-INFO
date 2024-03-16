import java.util.Objects;

public class Student implements Comparable<Student>{
    String nume;
    float grade;

    public Student(String nume, float grade){
        this.nume = nume;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", grade=" + grade +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Float.compare(grade, student.grade) == 0 && Objects.equals(nume, student.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, grade);
    }

    @Override
    public int compareTo(Student o){
        return this.nume.compareTo(o.nume);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
