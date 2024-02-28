package com.aliakseikul.storenew.repository;

import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from  User u where u.userNickname = :userNickname")
    Optional<User> findUserByNickName(String userNickname);

    @Modifying
    @Query("update User u set u.userRole = :userRole where u.userId = :uuid")
    void changeRole(UUID uuid, UserRole userRole);
}
