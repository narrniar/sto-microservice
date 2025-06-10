package com.example.sto.domain.models;

import com.example.sto.domain.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "status_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_history_sequence_generator")
    @SequenceGenerator(
            name = "status_history_sequence_generator",
            sequenceName = "status_history_sequence_generator", // Name of the PostgreSQL sequence
            initialValue = 1,              // Starting value (optional, matches DB default)
            allocationSize = 1             // How many IDs Hibernate fetches at once (matches DB default of 1 for simple auto-increment)
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_request_id", nullable = false)
    private ServiceRequest serviceRequest;

    @Enumerated(EnumType.STRING)
    private RequestStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus toStatus;

    @Column(nullable = false)
    private String changedBy;

    @Column
    private String reason;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
