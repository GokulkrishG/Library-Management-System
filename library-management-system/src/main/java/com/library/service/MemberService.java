package com.library.service;

import com.library.model.Member;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void createMember(Member member) {
        memberRepository.save(member);
    }

    public void updateMember(Long id, Member member) {
        memberRepository.update(id, member);
    }

    public void deleteMember(Long id) {
        memberRepository.delete(id);
    }
}