package com.aliakseikul.storenew.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
