package org.charity.observer;

import org.charity.CharitableCase;

import java.util.List;

public interface UIObservalbe {
    void notifyObserver(List<CharitableCase> cases);
}
