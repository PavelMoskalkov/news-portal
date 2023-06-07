package org.newsportal.service.impl;

import lombok.RequiredArgsConstructor;
import org.newsportal.database.entity.User;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.service.UserService;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    @Override
    public List<UserDto> getAll() {
        return userMapper.toDto(userRepository.findAll()
                .orElseThrow(()-> new RuntimeException("User not found")));
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found")));
    }

    @Override
    public Long add(UserDto userDto) {
        return userRepository.create(userMapper.toEntity(userDto))
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Override
    public User changeById(Long id, UserDto userDto) {
        return userRepository.updateById(id, userMapper.toEntity(userDto))
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }
}
