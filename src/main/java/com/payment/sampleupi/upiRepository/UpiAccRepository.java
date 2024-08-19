package com.payment.sampleupi.upiRepository;

import com.payment.sampleupi.upiEntity.UpiAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UpiAccRepository extends JpaRepository<UpiAccount, Long> {
    Optional<UpiAccount> findByUpiId(String upiId);
}
