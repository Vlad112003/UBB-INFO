package project.oacoacdiridiridac.Domain;

import java.io.Serializable;
import java.util.Objects;

public class Entity<ID> implements Serializable {
    protected ID id;

    public ID getId(){
        return id;
    }

    public void setId(ID id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
