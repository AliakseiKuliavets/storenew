package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Image;
import com.aliakseikul.storenew.repository.ImageRepository;
import com.aliakseikul.storenew.service.interf.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image findById(Long imageId) {
        return imageRepository.getById(imageId);
    }
}

