package com.liza;

import com.liza.service.WeatherAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Scanner;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

    @Autowired
    WeatherAggregator weatherAggregator;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("hi");

        Scanner console = new Scanner(System.in);

        while (true) {
            switch (console.next()) {
                case "1":
                    try {
                        weatherAggregator.calculateWeather();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
