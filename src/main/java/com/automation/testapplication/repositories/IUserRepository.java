package com.automation.testapplication.repositories;

import com.automation.testapplication.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
    List<User> findByName(String name);
}
