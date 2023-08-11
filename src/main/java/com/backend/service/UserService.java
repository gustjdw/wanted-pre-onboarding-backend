package com.backend.service;

import com.backend.dto.user.UserReqDto;
import com.backend.entity.User;
import com.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void join(UserReqDto userReqDto) {

        String email = userReqDto.getEmail();
        if (userRepository.existsByEmail(email)) throw new IllegalArgumentException("이미 존재하는 회원입니다.");

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(userReqDto.getPassword()))
                .build();
        userRepository.save(user);
    }

    public void validate(UserReqDto userReqDto) {

        String email = userReqDto.getEmail();
        String password = userReqDto.getPassword();

        if(!email.contains("@")) throw new IllegalArgumentException("이메일에는 @가 포함되어야 합니다.");
        if(password.length() < 8) throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");
    }
}
