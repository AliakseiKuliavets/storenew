package com.aliakseikul.storenew.validation.constraint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class IdAnnotationCheckerTest {

    @InjectMocks
    private IdAnnotationChecker idAnnotationChecker;

    @Test
    void isValid(){
        assertFalse(idAnnotationChecker.isValid(null,null));
    }
}
