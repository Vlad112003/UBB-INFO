package org.charity.dto;

import org.charity.CharitableCase;
import org.charity.Donation;
import org.charity.Donor;
import org.charity.Volunteer;

public class DTOUtils {
    public static CharitableDTO getDTO(CharitableCase charitableCase){
        return new CharitableDTO(charitableCase.getId(), charitableCase.getCaseName(), charitableCase.getAmount());
    }

    public static CharitableCase getFromDTO(CharitableDTO charitableDTO) {
        return new CharitableCase(charitableDTO.getId(), charitableDTO.getCaseName(), charitableDTO.getAmount());
    }

    public static DonationDTO getDTO(Donation donation){
        return new DonationDTO(donation.getId(), getDTO(donation.getDonor()), getDTO(donation.getCharitableCase()), donation.getAmount());
    }

    public static Donation getFromDTO(DonationDTO donationDTO){
        return new Donation(donationDTO.getId(), getFromDTO(donationDTO.getDonor()), getFromDTO(donationDTO.getCharitableDTO()), donationDTO.getAmount());
    }

    public static DonorDTO getDTO(Donor donor){
        return new DonorDTO(donor.getId(), donor.getName(), donor.getAddress(), donor.getPhone());
    }

    public static Donor getFromDTO(DonorDTO donorDTO){
        return new Donor(donorDTO.getId(), donorDTO.getName(), donorDTO.getAddress(), donorDTO.getPhone());
    }

    public static Volunteer getFromDTO(VolunteerDTO volunteerDTO){
        return new Volunteer(volunteerDTO.getId(), volunteerDTO.getUsername(), volunteerDTO.getPassword());
    }

    public static VolunteerDTO getDTO(Volunteer volunteer){
        return new VolunteerDTO(volunteer.getId(), volunteer.getUsername(), volunteer.getPassword());
    }

}
