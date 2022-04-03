package com.liza.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
public class WeatherRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private WeatherRepository repo;


    //test data
    Weather weather1 = new Weather("Moscow", "RU", Timestamp.from(Instant.now()), 25.0);
    Weather weather2 = new Weather("Moscow", "RU", Timestamp.from(Instant.now()), 27.0);
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


    @Test
    public void shouldGetNoResultWhenEmpty() {
        List<Weather> allForDate = repo.getAllForDate("London", "GB", formatter.format(date));
        assertEquals(allForDate.size(), 0);
    }

    @Test
    public void shouldReturnAllDateData() {
        testEntityManager.persist(weather1);
        testEntityManager.persist(weather2);
        List<Weather> allForDate = repo.getAllForDate("Moscow", "RU", formatter.format(date));
        assertEquals(allForDate.size(), 2);
    }

    @Test
    public void shouldReturnLatestData() {
        testEntityManager.persist(weather1);
        testEntityManager.persist(weather2);
        Weather result = repo.findTop1ByCityAndCountryOrderByTimeDesc("Moscow", "RU");
        //todo persist records with hardcoded time diff
        assertEquals(result.getTemperature(),
                weather1.getTime().before(weather2.getTime()) ? weather2.getTemperature() : weather1.getTemperature(),
               0.01);
    }

}
