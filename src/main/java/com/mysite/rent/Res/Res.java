package com.mysite.rent.Res;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

import com.mysite.rent.Member.Member;
import com.mysite.rent.Car.Car;
@Getter
@Setter
@Entity
public class Res {
	@Id
	@Column(length = 200)
    private String resNumber;

	@Column(nullable = false)
    private LocalDate resDate;

	@Column(nullable = false)
    private LocalDate useBeginDate;
    
	@Column(nullable = false)
    private LocalDate returnDate;

    @Column(nullable = true)
    private double price;
    
    @ManyToOne
    @JoinColumn(name = "resUserId", referencedColumnName = "id")
    private Member member;
   
    @ManyToOne
    @JoinColumn(name = "resCarNumber", referencedColumnName = "carNumber")
    private Car car;

}
