package com.example.bank_api.services;


import com.example.bank_api.entities.OperationHistory;
import com.example.bank_api.entities.User;
import com.example.bank_api.repositories.OperationHistoryRepository;
import com.example.bank_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Service
public class BalanceOperation {


    private final UserRepository userRepository;
    private final OperationHistoryRepository operationHistoryRepository;


    public BalanceOperation( UserRepository userRepository, OperationHistoryRepository operationHistoryRepository) {
        this.userRepository = userRepository;
        this.operationHistoryRepository = operationHistoryRepository;
    }


    public double getBalance(Long id) {

        return userRepository.findUserById(id).get().getBalance();

    }


    @Transactional(rollbackOn = {Exception.class})
    public int putMoney(Long id, double value) throws Exception {
        User user = userRepository.findUserById(id).orElseThrow();
        user.put(value);
        operationHistoryRepository.save(new OperationHistory(user, 2, value, LocalDateTime.now()));
        userRepository.save(user);
        return 1;

    }


    @Transactional
    public int takeMoney(Long id, double value) throws Exception {

        User user = userRepository.findUserById(id).get();

        if (user.withdraw(value) <= 0) throw new Exception();
        System.out.println(LocalDateTime.now());
        operationHistoryRepository.save(new OperationHistory(user, 1, value, LocalDateTime.now()));
        userRepository.save(user);
        Calendar.getInstance();
        return 1;

    }

    public int transferMoney(Long senderId, Long recipientId, double value) {
        try {
            User sender = userRepository.findUserById(senderId).get();
            User recipient = userRepository.findUserById(recipientId).get();
            if (sender.withdraw(value) == 0) throw new Exception();
            recipient.put(value);
            userRepository.save(sender);
            userRepository.save(recipient);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // public int createUser(double balance){

    //     try {
    //         insertUserRepository.insertWithQuery(new User( balance));
    //         System.out.println(userRepository.findAll());
    //         return 1;
    //     } catch (Exception e){
    //         System.out.println(e.getMessage());
    //         return 0;
    //     }

    // }


    public List<User> showAllUsers() {
        return userRepository.findAll();
    }
}
