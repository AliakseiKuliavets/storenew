package com.aliakseikul.storenew.repository;

import com.aliakseikul.storenew.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from  User u where u.userNickname = :userNickname")
    User findUserByNickName(String userNickname);

    @Modifying
    @Query("update User u set u.userFirstName = :userName where u.userId = :uuid")
    void changeUserNameById(UUID uuid, String userName);

    @Modifying
    @Query("update User u set u.userLastName = :userLastName where u.userId = :uuid")
    void changeLastNameUserById(UUID uuid, String userLastName);

    @Modifying
    @Query("update User u set u.userEmail = :email where u.userId = :uuid")
    void changeEmailUserById(UUID uuid, String email);

    @Modifying
    @Query("update User u set u.userPhoneNumber = :phoneNumber where u.userId = :uuid")
    void changePhoneNumberUserById(UUID uuid, String phoneNumber);
}
