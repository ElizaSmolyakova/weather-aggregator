package com.liza.controller;

import com.liza.domain.Record;
import com.liza.service.RecordService;
import org.springframework.stereotype.Service;


@Service
public class RecordController {
    private final RecordService service;

    public RecordController(RecordService service) {
        this.service = service;
    }

    public Record get() {
        return service.getWeather();
    }

}
