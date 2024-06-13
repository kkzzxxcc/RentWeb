package com.mysite.rent.Car;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, String> {
	boolean existsByCarNumber(String carNumber);
	Optional<Car> findByCarNumber(String carNumber);
}
