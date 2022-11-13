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

    private final UserRepository userRepository;
    private final BalanceOperation balanceOperation;

    private final HistoryOperationService historyOperationService;

    private final UserService userService;

    @GetMapping("/employeeswithvariable/{id}")
    @ResponseBody
    public User getEmployeesByIdWithVariableName(@PathVariable("id") Long employeeId) {

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

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(
            @RequestParam("senderId") Long id1,
            @RequestParam("recipientId") Long id2,
            @RequestParam("value") double value) {
        try {
            balanceOperation.transferMoney(id1, id2, value);
        } catch (Exception e) {
            new ResponseEntity<>("sender doesn't have enough fonds", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("1", HttpStatus.OK);
    }


    //dateFormat:  yyyy-mm-ddThh:mm:ssZ like 2011-12-03T10:15:30-03:00
    @GetMapping("/getOperationList")
    public String getOperationList(
            @RequestParam("id") Long id,
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to) {
        return historyOperationService.getOperationList(id, from, to).toString();
    }


}
