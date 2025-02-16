package com.theroom.server.repository;

import com.theroom.server.domain.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c left join fetch c.address a order by c.id desc")
    List<Contact> findAllWithAddress();

    @Query("select c from Contact c " +
            "left join fetch c.address a " +
            "left join fetch c.customerFiles cf " +
            "where c.id = :id")
    Optional<Contact> findByIdWithAll(@Param("id") Long id);

    @Query("select c from Contact c " +
            "left join fetch c.address a " +
            "where c.id = :id")
    Optional<Contact> findByIdWithAddress(@Param("id") Long id);

    @Query("select c from Contact c " +
            "left join fetch c.customerFiles cf " +
            "where c.id = :id")
    Optional<Contact> findByIdWithFiles(@Param("id") Long id);
}
