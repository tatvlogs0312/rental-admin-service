package org.example.administrationservice.repository;

import org.example.administrationservice.entity.UserContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContractRepository extends JpaRepository<UserContract, String> {

}