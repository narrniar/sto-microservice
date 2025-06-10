package com.example.sto.repository;

import com.example.sto.domain.enums.RequestStatus;
import com.example.sto.domain.models.ServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    Optional<ServiceRequest> findByRequestNumber(String requestNumber);

    @Query("SELECT sr FROM ServiceRequest sr WHERE sr.client.id = :clientId")
    Page<ServiceRequest> findByClientId(@Param("clientId") Long clientId, Pageable pageable);

    @Query("SELECT sr FROM ServiceRequest sr WHERE sr.status = :status")
    Page<ServiceRequest> findByStatus(@Param("status") RequestStatus status, Pageable pageable);

    @Query("SELECT sr FROM ServiceRequest sr WHERE sr.status IN :statuses")
    List<ServiceRequest> findByStatusIn(@Param("statuses") List<RequestStatus> statuses);

    @Query("SELECT COUNT(sr) FROM ServiceRequest sr WHERE sr.status = :status")
    long countByStatus(@Param("status") RequestStatus status);

    @Query("SELECT sr FROM ServiceRequest sr WHERE sr.createdAt BETWEEN :startDate AND :endDate")
    List<ServiceRequest> findByCreatedAtBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT sr FROM ServiceRequest sr LEFT JOIN FETCH sr.statusHistory WHERE sr.id = :id")
    Optional<ServiceRequest> findByIdWithHistory(@Param("id") Long id);
}
