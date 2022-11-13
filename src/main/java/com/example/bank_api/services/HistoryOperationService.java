package com.example.bank_api.services;

import com.example.bank_api.entities.OperationHistory;
import com.example.bank_api.entities.User;
import com.example.bank_api.repositories.OperationHistoryRepository;
import com.example.bank_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryOperationService {


    final UserRepository userRepository;
    final
    OperationHistoryRepository operationHistoryRepository;

    public HistoryOperationService(UserRepository userRepository, OperationHistoryRepository operationHistoryRepository) {
        this.userRepository = userRepository;
        this.operationHistoryRepository = operationHistoryRepository;
    }

    public List<OperationHistory> getOperationList(Long id, LocalDateTime from, LocalDateTime to) {
        try {
                if (from == null) {
                    System.out.println("from");
                        from = operationHistoryRepository.findFirstByOrderByIdAsc().orElseThrow().getDate();
                    }
                if (to == null) {
                    System.out.println("to");
                        to = operationHistoryRepository.findFirstByOrderByIdDesc().orElseThrow().getDate();
                    }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        Optional<User> usrId = userRepository.findUserById(id);
        return operationHistoryRepository.findOperationHistoriesByDateBetweenAndUserId(from, to, usrId);


    }
}
