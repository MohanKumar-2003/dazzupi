package com.payment.sampleupi.upiService;

import com.payment.sampleupi.upiEntity.UpiAccount;
import com.payment.sampleupi.upiRepository.UpiAccRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpiAccService {

    @Autowired
    private UpiAccRepository upiAccRepository;

    public boolean existsByUpiAccName(String upiid) {
        return upiAccRepository.findByUpiId(upiid).isPresent();
    }
    public UpiAccount findUpiAcc(String upiid){
        return upiAccRepository.findByUpiId(upiid).get();
    }
}
