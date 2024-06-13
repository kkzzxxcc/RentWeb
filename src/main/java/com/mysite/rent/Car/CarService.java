package com.mysite.rent.Car;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarService {
	
	private final CarRepository carRepository;
	
	// 차량 등록
    public void registerCar(String carNumber, String carName, String carColor, int carSize, String carMaker) {
        Car car = new Car();
        car.setCarNumber(carNumber);
        car.setCarName(carName);
        car.setCarColor(carColor);
        car.setCarSize(carSize);
        car.setCarMaker(carMaker);
        carRepository.save(car);
    }
     
    // 차량번호가 데이터베이스에 존재하는지 확인
    public boolean existCarNumber(String carNumber) {
        return carRepository.existsByCarNumber(carNumber);
    }
    
    // 차량번호가 데이터베이스에 있는지 확인 후 있으면 리턴 없으면 널을 리턴
    public Car findCarNum(String carNumber) {
        Optional<Car> car = carRepository.findByCarNumber(carNumber);
        return car.orElse(null);
    }
    
    // 전체 차량 조회
    public List<Car> loadAllCar() {
        return carRepository.findAll();
    }
    
    // 차량 수정
    public void updateCar(String carNumber, String carName, String carColor, int carSize, String carMaker) {
        Car car = findCarNum(carNumber);
        if (car != null) {
            car.setCarName(carName);
            car.setCarColor(carColor);
            car.setCarSize(carSize);
            car.setCarMaker(carMaker);
            carRepository.save(car);
        }

        else {
            throw new RuntimeException("Car not found");
        }
    }
    
    // 차량 삭제
    public void deleteCarNum(String carNumber) {
        carRepository.deleteById(carNumber);
    }
}
