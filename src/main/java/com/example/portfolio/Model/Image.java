package com.example.portfolio.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imgId;

    private String name;
    private String type;

    @Column(name = "image_data", length = 1000000)
    private byte[] imageData;

}
