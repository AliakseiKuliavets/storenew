package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Image;
import com.aliakseikul.storenew.repository.ImageRepository;
import com.aliakseikul.storenew.service.interf.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Image findById(Long imageId) {
        return imageRepository.findById(imageId).orElse(null);
    }
}

