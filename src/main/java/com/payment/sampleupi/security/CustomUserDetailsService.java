package com.payment.sampleupi.security;


import com.payment.sampleupi.upiEntity.User;
import com.payment.sampleupi.upiRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.lang.Long;

@Service
public class CustomUserDetailsService implements UserDetailsService {
   @Autowired
    private UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id){
        User user=userRepo.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));

        return UserPrincipal.create(user);
    }

}
