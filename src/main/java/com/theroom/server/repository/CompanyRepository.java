package com.theroom.server.repository;

import com.theroom.server.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select c from Company c join fetch c.address a where c.id = :id")
    Optional<Company> findByIdWithAddress(@Param("id") Long id);
}
