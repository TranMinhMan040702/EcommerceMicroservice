package com.criscode.identity.repository;

import com.criscode.identity.entity.Address;
import com.criscode.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findByStatusAndUser(boolean status, User user);
}
