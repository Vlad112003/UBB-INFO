package org.charity.observer;

import org.charity.CharitableCase;

import java.util.List;

public interface CharityObservable {
    void addObserver(CharityObserver observer);

    void removeObserver(CharityObserver observer);

    void notifyObservers(List<CharitableCase> cases);
}
