package com.liza.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    //todo fix date format
    @Query(value="SELECT * FROM WEATHER WHERE CITY = ?1 AND COUNTRY = ?2 AND TIME LIKE %?3%", nativeQuery = true)
    List<Weather> getAllForDate(String city, String country, String date);

    Weather findTop1ByCityAndCountryOrderByTimeDesc(String city, String country);
}
