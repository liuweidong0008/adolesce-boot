package com.boot.adolesce.module.users.repository;

import com.boot.adolesce.module.users.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}