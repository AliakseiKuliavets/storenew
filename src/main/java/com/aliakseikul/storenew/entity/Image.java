package com.aliakseikul.storenew.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class Image {

    @Id
    @Column(name = "image_id")
    private UUID imageId;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_original_file_name")
    private String imageOriginalFileName;

    @Column(name = "image_size")
    private Long imageSize;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "image_is_preview_image")
    private boolean imageIsPreviewImage;

    @Lob
    private byte[] bytes;

    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private Product product;
}
