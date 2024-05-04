package com.example.clear_solutions_test_assignment.repository;

import com.example.clear_solutions_test_assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to);

}
