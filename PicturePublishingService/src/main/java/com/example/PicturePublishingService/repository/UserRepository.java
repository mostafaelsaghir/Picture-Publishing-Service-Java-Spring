package com.example.PicturePublishingService.repository;

import com.example.PicturePublishingService.entity.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository  extends PagingAndSortingRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
    Optional<UserInfo> findById(Long id);

}
