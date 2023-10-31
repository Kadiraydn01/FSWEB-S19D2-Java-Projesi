package com.example.bank.service;

import com.example.bank.entity.Member;
import com.example.bank.entity.Role;
import com.example.bank.repository.MemberRepository;
import com.example.bank.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AuthenticationService {
    private MemberRepository memberRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthenticationService(MemberRepository memberRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Member register(String email, String password){
        Optional<Member> foundMember = memberRepository.findByEmail(email);
        if(foundMember.isPresent()) {
            throw new RuntimeException("User with given email already exist, please login:  " + email);
        }
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        List<Role> roleList = new ArrayList<>();
        roleList.add(userRole);

        Member member = new Member();
        member.setEmail(email);
        member.setAuthorities(roleList);
        member.setPassword(encodedPassword);
        return memberRepository.save(member);
        }

}
