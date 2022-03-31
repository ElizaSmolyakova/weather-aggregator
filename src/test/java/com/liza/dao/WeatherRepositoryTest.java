package com.liza.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
public class WeatherRepositoryTest {

    @Autowired
    private WeatherRepository repo;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldGetNoResultWhenEmpty() {
        List<Weather> allForDate = repo.getAllForDate("London", "GB", "2022-02-02");
        assertEquals(allForDate.size(), 0);
    }

    @Test
    public void shouldReturnAllDateDat(){

    }

}
