package com.payment.sampleupi.upiRepository;

import com.payment.sampleupi.upiEntity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findBySender_Id(Long sender_id);

}
