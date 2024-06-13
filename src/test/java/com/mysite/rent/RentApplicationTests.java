package com.mysite.rent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.rent.Car.Car;
import com.mysite.rent.Car.CarRepository;

import com.mysite.rent.Member.Member;
import com.mysite.rent.Member.MemberRepository;

import com.mysite.rent.Res.Res;
import com.mysite.rent.Res.ResRepository;


@SpringBootTest
class RentApplicationTests {
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ResRepository resRepository;
	
	@Test
	void testJpa() {
		Car car = new Car();
        car.setCarNumber("ABC123");
        car.setCarName("Sedan");
        car.setCarColor("Black");
        car.setCarSize(5);
        car.setCarMaker("Toyota");
        carRepository.save(car);

        // Create and save a new member
        Member member = new Member();
        member.setId("user123");
        member.setPassword("password");
        member.setName("John Doe");
        member.setAddress("123 Main St");
        member.setPhoneNum("555-1234");
        memberRepository.save(member);

        // Create and save a new reservation
        Res res = new Res();
        res.setResNumber("RES123");
        res.setResDate(LocalDate.now());
        res.setUseBeginDate(LocalDate.now().plusDays(1));
        res.setReturnDate(LocalDate.now().plusDays(7));
        res.setCar(car);
        res.setMember(member);
        resRepository.save(res);
	}

}
