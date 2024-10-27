package org.charity.observer;

import org.charity.CharitableCase;

import java.util.List;

public interface UIObserver {
    void update(List<CharitableCase> cases);
}
