package com.example.bank_api.repositories;

import com.example.bank_api.entities.OperationHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface OperationHistoryRepository extends CrudRepository<OperationHistory, Long> {

    List<OperationHistory> findOperationHistoriesByDateBetween(LocalDateTime from, LocalDateTime to);


    List<OperationHistory> findAll();

    Optional<OperationHistory> findFirstByOrderByIdAsc();
    Optional<OperationHistory> findFirstByOrderByIdDesc();





}
