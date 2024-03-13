package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Image;
import com.aliakseikul.storenew.repository.ImageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @InjectMocks
    private ImageServiceImpl imageService;

    @Mock
    private ImageRepository imageRepository;

    @Test
    void findByIdPositive(){
        Long id = 123L;
        Image image = new Image();
        when(imageRepository.findById(id)).thenReturn(Optional.of(image));
        assertEquals(image ,imageService.findById(id));
        verify(imageRepository).findById(id);
    }

    @Test
    void findByIdReturnNull(){
        Long id = 123L;
        when(imageRepository.findById(id)).thenReturn(Optional.empty());
        assertNull(imageService.findById(id));
        verify(imageRepository).findById(id);
    }
}
