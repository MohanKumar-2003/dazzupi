package com.payment.sampleupi.upiController;

import com.payment.sampleupi.payload.MessageResponse;
import com.payment.sampleupi.payload.Payment;
import com.payment.sampleupi.upiEntity.Transaction;
import com.payment.sampleupi.upiEntity.User;
import com.payment.sampleupi.upiRepository.TranactionRepository;
import com.payment.sampleupi.upiRepository.UserRepository;
import com.payment.sampleupi.upiService.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TranactionRepository tranactionRepository;

    @PutMapping("/gateway")
    public ResponseEntity<?> payment(@RequestParam String senderId, @RequestParam String receiverId, @RequestBody Payment payment){
        if(paymentService.checkUpiPassword(senderId,payment.getUpiPassword()) && paymentService.checkBalance(senderId,payment.getAmount())) {
            paymentService.paymentGateway(senderId, receiverId, payment.getAmount(),payment.getUpiPassword(), payment.getDescription());
            return ResponseEntity.ok(new MessageResponse("Amount sent Successfully"));
        }
        User sender=userRepository.findByUpiAccountUpiId(senderId);
        User receiver=userRepository.findByUpiAccountUpiId(receiverId);
        Transaction transaction=new Transaction();
        transaction.setStatus("No");
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTransactionNumber(paymentService.getTransactionNumber());
        transaction.setAmount(payment.getAmount());
        tranactionRepository.save(transaction);
        return ResponseEntity.ok(new MessageResponse("Amount is not sent successfully"));
    }
    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam Long senderid){
        return paymentService.getTransactionDetails(senderid);
    }
}
