package com.example.bank_api.services;

import com.example.bank_api.entities.OperationHistory;
import com.example.bank_api.repositories.OperationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HistoryOperationService {


    final
    OperationHistoryRepository operationHistoryRepository;

    public HistoryOperationService(OperationHistoryRepository operationHistoryRepository) {
        this.operationHistoryRepository = operationHistoryRepository;
    }

    public List<OperationHistory> getOperationList( LocalDateTime from, LocalDateTime to) {
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
        return operationHistoryRepository.findOperationHistoriesByDateBetween(from, to);


    }
}
