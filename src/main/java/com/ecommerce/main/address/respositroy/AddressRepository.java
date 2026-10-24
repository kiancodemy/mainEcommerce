package com.ecommerce.main.address.respositroy;

import com.ecommerce.main.address.entity.Address;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {




    List<Address> findByUserIdOrderByCreatedAtDesc(Integer userId);

    Optional<Address> findByIdAndUserId(Integer id, Integer userId);
}