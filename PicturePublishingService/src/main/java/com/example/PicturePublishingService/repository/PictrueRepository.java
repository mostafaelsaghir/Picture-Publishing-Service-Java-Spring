package com.example.PicturePublishingService.repository;

import com.example.PicturePublishingService.entity.Pictrue;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PictrueRepository extends PagingAndSortingRepository<Pictrue, Long> {
    List<Pictrue> findByStatus(String status);
}
