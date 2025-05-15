package com.uade.tpo.TurnosYa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.TurnosYa.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{ 
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByDNI(int dni);
}
