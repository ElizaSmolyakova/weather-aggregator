package com.liza;

import com.liza.controller.RecordController;
import com.liza.dao.Weather;
import com.liza.dao.WeatherRepository;
import com.liza.domain.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    RecordController controller;

    @Autowired
    WeatherRepository repository;

    @Autowired
    private LocationProperties props;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("hi");

        Scanner console = new Scanner(System.in);

        while (true) {
            switch (console.next()) {
                case "1":
                    Record record = controller.get();
                    repository.save(new Weather(record.getCity(), record.getCountry(), record.getTime(), record.getTemperature()));
                    System.out.println(record);
                    break;

                default:
                    System.exit(0);
            }
        }
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class);
    }
}
