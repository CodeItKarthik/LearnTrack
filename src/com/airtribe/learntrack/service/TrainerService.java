package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Trainer;
import com.airtribe.learntrack.utils.IdGenerator;

import static com.airtribe.learntrack.constants.AppConstants.EQUALS;

public class TrainerService {

    DisplayService displayService = new DisplayService();

    public void addTrainer(String firstName, String lastName, String email) {
        int id = IdGenerator.getNextTrainerId();
        Trainer trainer = new Trainer(id, firstName, lastName, email, 10, "PhD");
        System.out.println(EQUALS + " Trainer added successfully " + EQUALS);
        displayService.displayPerson(trainer);
    }

}
