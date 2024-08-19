package com.payment.sampleupi.upiService;

import com.payment.sampleupi.upiEntity.UpiAccount;
import com.payment.sampleupi.upiEntity.User;
import com.payment.sampleupi.upiRepository.UpiAccRepository;
import com.payment.sampleupi.upiRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UpiAccRepository upiAccRepository;
    public User save(User user, String UpiId){
        UpiAccount upiAccount=upiAccRepository.findByUpiId(UpiId).get();
        System.out.println(upiAccount);
        user.setUpiAccount(upiAccount);
        return userRepo.save(user);
    }
    public boolean existsByUsername(String username){
        return userRepo.findByUsername(username).isPresent();
    }

}
