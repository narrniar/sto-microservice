package com.example.sto.domain.models;

import com.example.sto.domain.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "service_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_request_sequence_generator")
    @SequenceGenerator(
            name = "service_request_sequence_generator",
            sequenceName = "service_request_sequence_generator", // Name of the PostgreSQL sequence
            initialValue = 1,              // Starting value (optional, matches DB default)
            allocationSize = 1             // How many IDs Hibernate fetches at once (matches DB default of 1 for simple auto-increment)
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String requestNumber;

    @Column
    private String description;

    @Column(nullable = false)
    private String carModel;

    @Column(nullable = false)
    private String carYear;

    @Column(nullable = false)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "serviceRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("createdAt ASC")
    private List<StatusHistory> statusHistory;

    @Column
    private String estimatedCost;

    @Column
    private String actualCost;

    @Column
    private LocalDateTime estimatedCompletionDate;

    @Column
    private LocalDateTime actualCompletionDate;

    @Column
    private String mechanicNotes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
