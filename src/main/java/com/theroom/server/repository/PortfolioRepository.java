package com.theroom.server.repository;

import com.theroom.server.domain.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("select p from Portfolio p join fetch p.thumbnailFile t")
    List<Portfolio> findAllWithThumbnail();

    @Query("select p from Portfolio p " +
            "left join fetch p.portfolioImageFiles pi " +
            "left join fetch p.address a " +
            "where p.id = :id")
    Optional<Portfolio> findByIdWithImageList(@Param("id") Long id);

    @Query("select p from Portfolio p " +
            "left join fetch p.address a " +
            "left join fetch p.thumbnailFile t " +
            "left join fetch p.portfolioImageFiles pi " +
            "where p.id = :id")
    Optional<Portfolio> findByIdWithAll(@Param("id") Long id);

    @Query("select p from Portfolio p " +
            "left join fetch p.thumbnailFile t " +
            "left join fetch p.portfolioImageFiles pi " +
            "where p.id = :id")
    Optional<Portfolio> findByIdWithAllImage(@Param("id") Long id);
}
