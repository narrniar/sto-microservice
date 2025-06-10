package com.example.sto.repository;

import com.example.sto.domain.enums.RequestStatus;
import com.example.sto.domain.models.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {

    @Query("SELECT sh FROM StatusHistory sh WHERE sh.serviceRequest.id = :serviceRequestId ORDER BY sh.createdAt")
    List<StatusHistory> findByServiceRequestIdOrderByCreatedAt(@Param("serviceRequestId") Long serviceRequestId);

    @Query("SELECT sh FROM StatusHistory sh WHERE sh.changedBy = :changedBy AND sh.createdAt BETWEEN :startDate AND :endDate")
    List<StatusHistory> findByChangedByAndCreatedAtBetween(
            @Param("changedBy") String changedBy,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT COUNT(sh) FROM StatusHistory sh WHERE sh.toStatus = :status")
    long countByToStatus(@Param("status") RequestStatus status);
}

