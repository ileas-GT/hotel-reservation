package com.skypay.hotelreservation.repository;

import com.skypay.hotelreservation.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Integer userId);
    List<User> findAllByOrderByCreatedAtDesc();
}
