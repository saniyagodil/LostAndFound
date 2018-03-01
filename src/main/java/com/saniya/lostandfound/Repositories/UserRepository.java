package com.saniya.lostandfound.Repositories;

import com.saniya.lostandfound.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);


}
