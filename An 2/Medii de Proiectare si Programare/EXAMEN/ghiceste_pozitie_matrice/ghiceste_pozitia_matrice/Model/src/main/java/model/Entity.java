package model;

import java.io.Serializable;

public interface Entity<Tid> {
    Tid getId();
    void setId(Tid id);
}
