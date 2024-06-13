package com.mysite.rent.Member;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    
    // 회원 등록
    public void registerMember(String id, String password, String name, String address, String phoneNum) {
        Member member = new Member();
        member.setId(id);
        member.setPassword(password);
        member.setName(name);
        member.setAddress(address);
        member.setPhoneNum(phoneNum);
        memberRepository.save(member);
    }
    
    // 아이디가 데이터베이스에 존재하는지 확인
    public boolean existMemId(String id) {
        return memberRepository.existsById(id);
    }
    
    // 아이디가 데이터베이스에 있는지 확인 후 있으면 리턴 없으면 널을 리턴
    public Member findMemId(String id) {
        Optional<Member> member = memberRepository.findById(id);	
        return member.orElse(null);
    }
    
    // 전체 회원 조회
    public List<Member> loadMembers() {
        return memberRepository.findAll();
    }
    
    // 회원 수정
    public void updateMember(String id, String password, String name, String address, String phoneNum) {
        Member member = findMemId(id);
        if (member != null) {
            member.setPassword(password);
            member.setName(name);
            member.setAddress(address);
            member.setPhoneNum(phoneNum);
            memberRepository.save(member);
        }

        else {
            throw new RuntimeException("Member not found");
        }
    }
    
    // 회원 삭제
    public void deleteMemId(String id) {
        memberRepository.deleteById(id);
    }
}