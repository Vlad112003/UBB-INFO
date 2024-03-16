package ro.ubbcluj.cs.map.Domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {

    protected ID id;

public long getId() {
        return (long) id;
    }

public void setId(ID id) {
        this.id = id;
    }

@Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }

@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Entity)) return false;
        Entity entity = (Entity) obj;
        return id.equals(entity.id);
    }

@Override
    public int hashCode() {
        return id.hashCode();
    }

}
