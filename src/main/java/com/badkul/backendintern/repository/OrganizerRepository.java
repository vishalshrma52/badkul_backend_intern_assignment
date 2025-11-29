package com.badkul.backendintern.repository;

import com.badkul.backendintern.entity.Organizer;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<Organizer> findByOrganizerCode(String code);

    // Simple partial search for name/email/phone
    @Query("SELECT o FROM Organizer o WHERE " +
           "LOWER(o.name) LIKE LOWER(CONCAT('%',:q,'%')) OR " +
           "LOWER(o.email) LIKE LOWER(CONCAT('%',:q,'%')) OR " +
           "LOWER(o.phone) LIKE LOWER(CONCAT('%',:q,'%'))")
    Page<Organizer> searchByNameEmailPhone(@Param("q") String q, Pageable pageable);

    // To help generate latest code - find max organizer code numeric suffix
    @Query(value = "SELECT o.organizer_code FROM organizers o ORDER BY o.id DESC LIMIT 1", nativeQuery = true)
    String findLatestOrganizerCodeNative();
}
