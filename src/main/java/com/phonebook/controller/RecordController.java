package com.phonebook.controller;

import com.phonebook.exception.Error;
import com.phonebook.exception.InvalidRecordException;
import com.phonebook.entity.Record;
import com.phonebook.exception.RecordNotFoundException;
import com.phonebook.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recordsapi")
public class RecordController {

    @Autowired
    RecordRepository repository;

    @GetMapping
    public List<Record> records() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Record recordById(@PathVariable long id) {
        Record record = repository.findOne(id);
        if (record == null) {
            throw new RecordNotFoundException(id);
        }
        return repository.findOne(id);
    }

    @PostMapping/*(consumes = "application/json")*/
    public ResponseEntity<Record> saveRecord(@Valid @RequestBody Record record,
                                             BindingResult result,
                                             UriComponentsBuilder ucb) {
        if (result.hasErrors()) {
            throw new InvalidRecordException();
        }

        Record savedRecord = repository.save(record);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/records/")
                .path(String.valueOf(savedRecord.getId()))
                .build().toUri();
        headers.setLocation(locationUri);

        return new ResponseEntity<>(record, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Record updateRecord(@PathVariable long id, @Valid @RequestBody Record record, BindingResult result) {
        Record model = repository.findOne(id);
        if (model == null) {
            throw new RecordNotFoundException(id);
        }
        if (result.hasErrors()) {
            throw new InvalidRecordException();
        }
        model.setName(record.getName());
        model.setSurname(record.getSurname());
        model.setPhoneNumber(record.getPhoneNumber());
        model.setEmail(record.getEmail());
        repository.save(model);
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable long id) {
        repository.delete(id);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error recordNotFound(RecordNotFoundException e) {
        return new Error(404, "Record [" + e.getRecordId() + "] not found!");
    }

    @ExceptionHandler(InvalidRecordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error invalidRecord(InvalidRecordException e) {
        return new Error(400, "Invalid Record");
    }

}