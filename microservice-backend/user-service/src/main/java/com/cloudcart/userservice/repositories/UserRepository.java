package com.cloudcart.userservice.repositories;

import com.cloudcart.userservice.modals.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
