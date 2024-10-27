package org.charity.service;

import org.charity.CharitableCase;
import org.charity.Donation;
import org.charity.Donor;
import org.charity.observer.CharityObservable;
import org.charity.observer.CharityObserver;
import org.charity.observer.ServerObserver;
import org.charity.ICharitableCase;
import org.charity.IDonationRepository;
import org.charity.IDonorRepository;
import org.charity.IVolunteerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service implements CharityObservable {
    private ICharitableCase cases;
    private IDonorRepository donors;
    private IDonationRepository donations;
    private IVolunteerRepository volunteers;
    private List<CharityObserver> observers;

    ServerObserver serverObserver;

    public Service(ICharitableCase cases, IDonorRepository donors, IDonationRepository donations, IVolunteerRepository volunteers) {
        this.cases = cases;
        this.donors = donors;
        this.donations = donations;
        this.volunteers = volunteers;
        this.observers = new ArrayList<>();
    }

    public Iterable<CharitableCase> getAllCharitableCases(){
        return cases.findAll();
    }

    public Iterable<Donor> getAllDonors(){
        return donors.findAll();
    }

    public Iterable<Donor> getAllDonorsWithFilter(String filter){
        return donors.findAllWithFilter(filter);
    }

    public void saveDonor(Donor donor){
        donors.save(donor);
    }

    public Integer saveDonorWithoutId(Donor donor){
        return donors.saveWitoutId(donor);
    }

    public void updateCharitableCase(CharitableCase charitableCase, double amount){
        charitableCase.setAmount(charitableCase.getAmount() + amount);
        cases.update(charitableCase.getId(), charitableCase);
    }

    public Integer saveDonationNewDonor(Donation donation){
        Integer newId = saveDonorWithoutId(donation.getDonor());
        donation.getDonor().setId(newId);
        updateCharitableCase(donation.getCharitableCase(), donation.getAmount());
        donations.save(donation);
        return newId;
    }
    public void saveDonation(Donation donation){
        saveDonor(donation.getDonor());
        updateCharitableCase(donation.getCharitableCase(), donation.getAmount());
        donations.save(donation);
        List<CharitableCase> cases = StreamSupport.stream(this.cases.findAll().spliterator(), false).collect(Collectors.toList());
        notifyObservers(cases);

    }

    public void addObserver(CharityObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(CharityObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(List<CharitableCase> cases) {
        for (CharityObserver observer : observers) {
            observer.update(cases);
        }
    }

    public void setServerObserver(ServerObserver serverObserver) {
        this.serverObserver = serverObserver;
    }
}
