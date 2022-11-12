package com.example.bank_api.repositories;

import com.example.bank_api.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long>  {

    Optional<User> findUserById(Long id);

    List<User> findAll();





}
