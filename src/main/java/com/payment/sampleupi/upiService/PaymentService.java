package com.payment.sampleupi.upiService;

import com.payment.sampleupi.upiEntity.Transaction;
import com.payment.sampleupi.upiEntity.UpiAccount;
import com.payment.sampleupi.upiEntity.User;
import com.payment.sampleupi.upiRepository.TranactionRepository;
import com.payment.sampleupi.upiRepository.UpiAccRepository;
import com.payment.sampleupi.upiRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class PaymentService{
    @Autowired
    private UpiAccRepository upiAccRepository;
    @Autowired
    private TranactionRepository tranactionRepository;
    @Autowired
    private UserRepository userRepository;
    public void paymentGateway(String senderId, String receiverId, BigDecimal amount, String upiPassword, String description){
        UpiAccount senderAccount=upiAccRepository.findByUpiId(senderId).get();
        UpiAccount receiverAccount=upiAccRepository.findByUpiId(receiverId).get();
        User sender=userRepository.findByUpiAccountUpiId(senderId);
        User receiver=userRepository.findByUpiAccountUpiId(receiverId);
        if(checkBalance(senderId,amount) && checkUpiPassword(senderId,upiPassword)){
                 senderAccount.setBalanceAmount(senderAccount.getBalanceAmount().subtract(amount));
                 receiverAccount.setBalanceAmount(receiverAccount.getBalanceAmount().add(amount));
                 Transaction transaction=new Transaction();
                 transaction.setAmount(amount);
                 transaction.setTransactionNumber(getTransactionNumber());
                 transaction.setDescription(description);
                 transaction.setStatus("Yes");
                 transaction.setSender(sender);
                 transaction.setReceiver(receiver);
                 upiAccRepository.save(senderAccount);
                 upiAccRepository.save(receiverAccount);
                 tranactionRepository.save(transaction);
        }

    }
    public boolean checkUpiPassword(String senderId, String password){
        UpiAccount senderAccount=upiAccRepository.findByUpiId(senderId).get();
        if(senderAccount.getUpiPassword().equals(password)){
            return true;
        }
        return false;
    }
    public boolean checkBalance(String senderId, BigDecimal amount){
        UpiAccount senderAccount=upiAccRepository.findByUpiId(senderId).get();

        if(senderAccount.getBalanceAmount().compareTo(amount)>=0){
            return true;
        }
      return false;
    }
    public String getTransactionNumber(){
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=1;i<10;i++){
            sb.append(random.nextInt(i));
        }
        return sb.toString();
    }
    public List<Transaction> getTransactionDetails(Long sender_id){
        List<Transaction> transactions=tranactionRepository.findBySender_Id(sender_id);
        return transactions;
    }
}
