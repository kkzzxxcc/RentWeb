package com.mysite.rent.Res;

import java.util.List;
import org.springframework.stereotype.Service;

import com.mysite.rent.Member.MemberRepository;
import com.mysite.rent.Car.*;
import com.mysite.rent.Member.*;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class ResService {
	
	private final CarRepository carRepository;
	private final MemberRepository memberRepository;
	private final ResRepository resRepository;
	

	public boolean existCarNum(String carNumber) {
        return carRepository.findByCarNumber(carNumber).isPresent();
    }


    // 회원 존재 확인
	public boolean existMemId(String memberId) {
        return memberRepository.findById(memberId).isPresent();
    }

    // 예약 등록
    public void registerRes(String resNumber, LocalDate resDate, LocalDate useBeginDate, LocalDate returnDate, double price, String carNumber, String memberId) {
        Car car = carRepository.findByCarNumber(carNumber).orElseThrow(() -> new IllegalArgumentException("Invalid car number"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        Res res = new Res();
        res.setResNumber(resNumber);
        res.setResDate(resDate);
        res.setUseBeginDate(useBeginDate);
        res.setReturnDate(returnDate);
        res.setCar(car);
        res.setMember(member);
        res.setPrice(price);
        resRepository.save(res);
    }

    // 예약 존재 확인
	 public boolean existResNum(String resNumber) {
	        Optional<Res> existingRes = resRepository.findByResNumber(resNumber);
	        return existingRes.isPresent();
	 }
	 
	// 전체 예약 조회
	public List<Res> loadAllRes() {
	   return resRepository.findAll();
	}
	
	// 예약번호가 데이터베이스에 있는지 확인 후 있으면 리턴 없으면 널을 리턴
    public Res findResNum(String resNumber) {
        Optional<Res> res = resRepository.findByResNumber(resNumber);
        return res.orElse(null);
    }

    // 예약 수정
    public void ModifyRes(String resNumber, String carNumber, LocalDate resDate, LocalDate useBeginDate, LocalDate returnDate, double price) {
        Res res = findResNum(resNumber);
        if (res != null) {
            res.getCar().setCarNumber(carNumber);
            res.setResDate(resDate);
            res.setUseBeginDate(useBeginDate);
            res.setReturnDate(returnDate);
            res.setPrice(price);
            resRepository.save(res);
        }

        else {
            throw new IllegalArgumentException("예약 수정에 실패하셨습니다.");
        }
    }
    
    // 예약 삭제
    public void deleteResResNum(String resNumber) {
        resRepository.deleteById(resNumber);
    }
        
}
