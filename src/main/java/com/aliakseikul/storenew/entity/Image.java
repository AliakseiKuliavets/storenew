package com.aliakseikul.storenew.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Blob;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_original_file_name")
    private String imageOriginalFileName;

    @Lob
    private Blob imageSize;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "image_is_preview_image")
    private boolean imageIsPreviewImage;

    @Column(name = "bytes")
    private byte[] bytes;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(imageId, image.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId);
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", imageName='" + imageName + '\'' +
                ", imageOriginalFileName='" + imageOriginalFileName + '\'' +
                ", imageSize=" + imageSize +
                ", imageContentType='" + imageContentType + '\'' +
                ", imageIsPreviewImage=" + imageIsPreviewImage +
                ", bytes=" + Arrays.toString(bytes) +
                ", product=" + product +
                '}';
    }
}
