package com.example.bank_api.repositories;

import com.example.bank_api.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

//баласт, реализовал его в обычном UserRepo
//jooq;
@Repository
public class InsertUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertWithQuery(User user){
        entityManager.createNativeQuery("INSERT INTO bank.user (balance) VALUES (?)")
                .setParameter(1, user.getBalance())
                .executeUpdate();

    }
}
