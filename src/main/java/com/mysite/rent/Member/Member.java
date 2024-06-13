package com.mysite.rent.Member;

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
public class Member {
	@Id
	@Column(length = 200)
    private String id;

    @Column(length = 200)
    private String password;

    @Column(length = 200)
    private String name;

    @Column(length = 200) 
    private String address;
    
    @Column(length = 200)
    private String phoneNum;
    
    @OneToMany(mappedBy = "member")
    private List<Res> reservations;

}
