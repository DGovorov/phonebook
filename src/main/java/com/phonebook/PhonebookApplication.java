package com.phonebook;

import com.phonebook.entity.Record;
import com.phonebook.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PhonebookApplication implements CommandLineRunner {

    @Autowired
    private RecordRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(PhonebookApplication.class, args);
    }

    public void run(String... args) throws Exception {
        List<Record> records = new ArrayList<>();
        records.add(new Record("Bill", "Croft", "5551122", "email@unknown.gr"));
        records.add(new Record("Bob", "Carpenter", "5552233", "another@email.com"));
        records.add(new Record("Barney", "Stinson", "5553344", "stinson@barney.net"));
        records.add(new Record("Berry", "Straw", "5554455", "box@nowhere.ru"));
        records.add(new Record("Bart", "Centaur", "5555566", "haha@notfunny.com"));
        records.add(new Record("Betsy", "Simon", "5556677", "relative@family.com"));
        records.add(new Record("Belle", "Mertence", "5557788", "databases@postgre.s"));
        records.add(new Record("Brock", "MacCormick", "5558899", "wow@woah.huh"));
        records.add(new Record("Bruce", "Wayne", "91102102", "batman@gotham.us"));
        records.add(new Record("Ten", "Numbers", "5550000", "outof@index.exception"));

        for (int i = 0; i < records.size(); i++) {
            repository.save(records.get(i));
        }
    }

}
