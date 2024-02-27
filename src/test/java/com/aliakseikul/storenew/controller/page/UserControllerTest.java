package com.aliakseikul.storenew.controller.page;


import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.UserRole;
import com.aliakseikul.storenew.mapper.UserMapper;
import com.aliakseikul.storenew.repository.UserRepository;
import com.aliakseikul.storenew.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;


    private final UUID validId = UUID.fromString("10d83df1-7247-4a7e-af09-96d418317ec2");

    private User user;
    private UserDto userDto;
    private UserCreateDto userCreateDto;

    @BeforeEach
    public void init() {
        user = new User(
                validId,
                "TestNickName",
                "TestPassword",
                "TestFirstName",
                "TestLastName",
                "TestEmail",
                "TestNumber",
                true,
                UserRole.USER,
                null,
                null,
                null,
                null,
                null,
                null
        );
        userDto = new UserDto(
                "TestNickNameDto",
                "TestFirstNameDto",
                "TestLastNameDto",
                "TestEmailDto",
                "TestNumberDto",
                true,
                UserRole.USER
        );
        userCreateDto = new UserCreateDto(
                "TestNickNameCreateDto",
                "TestPasswordCreateDto",
                "TestFirstNameCreateDto",
                "TestLastNameCreateDto",
                "TestEmailCreateDto"
        );
    }

//    private Product product;
//
//    @BeforeEach
//    public void init() {
//        product = new Product(
//                validId,
//                "Banana",
//                BigDecimal.valueOf(10),
//                "Simple description",
//                ProductCategory.OTHER,
//                ProductBrand.APPLE,
//                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
//                1234L,
//                new User(),
//                new User(),
//                null
//        );
//    }

    @Test
    void getProductById() {
        Mockito.when(userRepository.findById(validId)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toDto(user)).thenReturn(userDto);
        Assertions.assertEquals(userDto, userService.findById(validId.toString()));
        Mockito.verify(userRepository).findById(validId);
        Mockito.verify(userMapper).toDto(user);
    }

    @Test
    void addUser() {
        Mockito.when(userRepository.findUserByNickName(userCreateDto.getUserNickname()))
                .thenReturn(null);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Assertions.assertEquals(user, userService.addUser(userCreateDto));
        Mockito.verify(userRepository).findUserByNickName(userCreateDto.getUserNickname());
        Mockito.verify(userRepository).save(user);
    }
}
