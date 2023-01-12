package com.example.bank_api;


import com.example.bank_api.entities.User;
import com.example.bank_api.repositories.OperationHistoryRepository;
import com.example.bank_api.repositories.UserRepository;
import com.example.bank_api.services.BalanceOperation;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BalanceOperationTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OperationHistoryRepository operationHistoryRepository;

    @Autowired
    BalanceOperation balanceOperation;

    static final Long TEST_USER = 4L;
    static final Long TEST_RECEIVER = 5L;

    @BeforeAll
    public void beforeAll(){
        User user= userRepository.findUserById(TEST_USER).get();
        User user1= userRepository.findUserById(TEST_RECEIVER).get();
        user.setBalance(0);
        user1.setBalance(0);
        userRepository.save(user);
        userRepository.save(user1);
    }

    @AfterAll
    public void afterAll(){
        System.out.println("testUser's balance ins "+userRepository.findUserById(TEST_USER).get().getBalance());
    }

    @Test
    public void testPut() {
        int check = balanceOperation.putMoney(TEST_USER, 25);
        Assertions.assertEquals(1, check);
    }


    @SneakyThrows
    @Test
    void testTake() {
        int check = balanceOperation.takeMoney(TEST_USER, -13);
        Assertions.assertEquals(1, check);
    }

    @Test
    public void testTakeMoreThanUserHave() {
        int check = balanceOperation.takeMoney(TEST_USER, 1000000);
        Assertions.assertEquals(0, check);
    }


    @Test
    public void testTransfer() {
        int check = balanceOperation.transferMoney(TEST_USER, TEST_RECEIVER, 1);
        Assertions.assertEquals(1, check);
    }

}
