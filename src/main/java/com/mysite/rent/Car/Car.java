package com.mysite.rent.Car;

import java.time.LocalDateTime;
import java.util.List; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

import com.mysite.rent.Res.Res;

@Getter
@Setter
@Entity
public class Car {
	@Id
	@Column(length = 200)
    private String carNumber;

    @Column(length = 200)
    private String carName;

    @Column(length = 200)
    private String carColor;

    @Column
    private Integer carSize;
    
    @Column(length = 200)
    private String carMaker;
    
    @OneToMany(mappedBy = "car")
    private List<Res> cars;

}
