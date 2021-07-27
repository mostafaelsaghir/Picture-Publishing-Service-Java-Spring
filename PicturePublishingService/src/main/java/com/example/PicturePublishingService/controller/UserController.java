package com.example.PicturePublishingService.controller;

import com.example.PicturePublishingService.entity.Role;
import com.example.PicturePublishingService.entity.UserInfo;
import com.example.PicturePublishingService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public UserInfo addNewUser(@RequestBody UserInfo user) throws Exception{
        return this.userRepository.save(UserInfo.builder().username(user.getUsername()).password(user.getPassword()).role(Role.user).build());
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/adminSignup")
    public UserInfo addNewAdminUser(@RequestBody UserInfo user) throws Exception{
        return this.userRepository.save(UserInfo.builder().username(user.getUsername()).password(user.getPassword()).role(Role.admin).build());
    }
}
