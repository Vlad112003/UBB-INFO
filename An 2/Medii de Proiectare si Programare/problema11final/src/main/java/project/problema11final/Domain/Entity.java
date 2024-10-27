package project.problema11final.Domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    protected ID _id;
    public ID getId() {
        return _id;
    }

    public void setId(ID _id) {
        this._id = _id;
    }
}
