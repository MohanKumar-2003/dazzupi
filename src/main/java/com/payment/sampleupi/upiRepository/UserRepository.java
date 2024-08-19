package com.payment.sampleupi.upiRepository;

import com.payment.sampleupi.upiEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    @Query("SELECT u from User u where u.upiAccount.upiId=:upiId")
    User findByUpiAccountUpiId(@Param("upiId") String upiId);
}
