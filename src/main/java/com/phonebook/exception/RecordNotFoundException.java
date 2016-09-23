package com.phonebook.exception;

public class RecordNotFoundException extends RuntimeException {

    private long recordId;

    public RecordNotFoundException(long recordId) {
        this.recordId = recordId;
    }

    public long getRecordId() {
        return recordId;
    }

}
