package com.theroom.server.repository;

import com.theroom.server.domain.entity.Content;
import com.theroom.server.domain.entity.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("select c from Content c where c.contentType = :contentType order by c.ord")
    List<Content> findAllByContentType(@Param("contentType") ContentType contentType);
}
