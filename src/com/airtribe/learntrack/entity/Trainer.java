package com.airtribe.learntrack.entity;

public class Trainer extends Person {

    private int experience;
    private String qualification;

    public Trainer(int id, String firstName, String lastName, String email, int experience, String qualification) {
        super(id, firstName, lastName, email);
        this.experience = experience;
        this.qualification = qualification;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public void getDisplayName() {
        System.out.println("Displaying Trainer name: " + getFirstName() + getLastName());
    }

    @Override
    public String toString() {
        return super.toString() +
                "experience=" + experience +
                ", qualification='" + qualification + '\'';
    }
}
