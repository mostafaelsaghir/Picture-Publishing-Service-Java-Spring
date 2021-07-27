package com.example.PicturePublishingService.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Pictures")
public class Pictrue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String category;
    private String fileurl;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    @JsonManagedReference(value = "id")
    private UserInfo userInfo;
    private String status;
}
