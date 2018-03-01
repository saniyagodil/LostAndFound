package com.saniya.lostandfound.Security;


import com.saniya.lostandfound.Model.Role;
import com.saniya.lostandfound.Model.User;
import com.saniya.lostandfound.Repositories.RoleRepository;
import com.saniya.lostandfound.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Loading Data... ");

        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));

        Role userRole = roleRepository.findByRoleName("USER");
        Role adminRole = roleRepository.findByRoleName("ADMIN");

        User newUser = new User("User1", "upassword");
        newUser.addRole(userRole);
        userRepository.save(newUser);

        User newAdmin = new User("Admin1", "apassword");
        newAdmin.addRole(adminRole);
        userRepository.save(newAdmin);


    }

}