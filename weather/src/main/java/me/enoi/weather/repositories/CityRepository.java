package me.enoi.weather.repositories;

import me.enoi.weather.dto.CitiesTop;
import me.enoi.weather.entities.CityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface  CityRepository <C> extends CrudRepository<CityEntity, Long>{
    List<CityEntity> findByName(String name);
    CityEntity findById(long id);

    @Query(value = "select city, count(city) as searches from city_search group by city ORDER BY searches desc limit 10", nativeQuery = true)
    List<CitiesTop> findTopSearch();
}
