package com.saniya.lostandfound.Repositories;

import com.saniya.lostandfound.Model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRoleName(String roleName);



}




