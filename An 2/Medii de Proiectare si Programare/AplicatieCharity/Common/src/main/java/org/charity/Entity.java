package org.charity;

import java.io.Serializable;

public abstract class Entity<ID> implements Serializable {

    protected ID _id;

    public ID getId() {
        return _id;
    }

    public void setId(ID _id) {
        this._id = _id;
    }

}
