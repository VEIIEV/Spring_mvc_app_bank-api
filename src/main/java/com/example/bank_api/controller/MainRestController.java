package com.example.bank_api.controller;


import com.example.bank_api.entities.AppError;
import com.example.bank_api.entities.User;
import com.example.bank_api.repositories.UserRepository;
import com.example.bank_api.services.BalanceOperation;
import com.example.bank_api.services.HistoryOperationService;
import com.example.bank_api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api")
@AllArgsConstructor
public class MainRestController {

    final private UserRepository userRepository;
    final private BalanceOperation balanceOperation;

    final private HistoryOperationService historyOperationService;

    final private UserService userService;

    @GetMapping("/employeeswithvariable/{id}")
    @ResponseBody
    public User getEmployeesByIdWithVariableName(@PathVariable("id") Long employeeId) {

        //return "ID: " + employeeId;
        return userService.getById(employeeId).get();
    }


    //получение баланса с использованием ResponseEntity
//без использования обработчика исключений
    @GetMapping("/getBalance")
    public ResponseEntity<?> getBalance(Long id) {
        try {

            return new ResponseEntity<>(
                    balanceOperation.getBalance(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(-1,
                    "User with id " + id + " nor found"),
                    HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/takeMoney")
    public ResponseEntity<?> takeMoney(@RequestParam("id") Long id, @RequestParam("value") double value) {
        try {
            balanceOperation.takeMoney(id, value);
            return new ResponseEntity<>(1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(0, "Insufficient funds"), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/putMoney")
    public ResponseEntity<?> putMoney(@RequestParam("id") Long id, @RequestParam("value") double value) {
        try {
            balanceOperation.putMoney(id, value);
            return new ResponseEntity<>(1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(0, "Insufficient funds"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getOperationList")
    public String getOperationList(
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to) {

        return historyOperationService.getOperationList(from, to).toString();

    }


}
