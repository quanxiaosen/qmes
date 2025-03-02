package com.example.qmex.Service;

import com.example.qmex.Entity.User;
import com.example.qmex.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<User> getUsers(int page,int size){
        return userRepository.findAll(PageRequest.of(page, size));
    }
}
