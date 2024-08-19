package com.payment.sampleupi.upiController;

import com.payment.sampleupi.upiEntity.UpiAccount;
import com.payment.sampleupi.upiService.UpiAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UpiAccService upiAccService;

    @GetMapping("/upidetails")
    public UpiAccount getUpidetails(@RequestParam String upiId){
        return upiAccService.findUpiAcc(upiId);
    }
}
