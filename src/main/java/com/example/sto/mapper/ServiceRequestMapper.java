package com.example.sto.mapper;

import com.example.sto.domain.enums.RequestStatus;
import com.example.sto.domain.models.Client;
import com.example.sto.domain.models.ServiceRequest;
import com.example.sto.domain.models.StatusHistory;
import com.example.sto.dto.ClientDto;
import com.example.sto.dto.CreateServiceRequestDto;
import com.example.sto.dto.ServiceRequestDto;
import com.example.sto.dto.StatusHistoryDto;
import org.mapstruct.*;

import java.util.List;

/**
 * MapStruct mapper for converting between ServiceRequest entity and DTOs.
 * Handles complex mappings including nested objects and collections.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {RequestStatus.class}
)
public interface ServiceRequestMapper {

    /**
     * Maps a ServiceRequest entity to a ServiceRequestDto.
     * Includes client details and status history.
     *
     * @param serviceRequest the source entity
     * @return the mapped DTO
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "requestNumber", source = "requestNumber")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "carModel", source = "carModel")
    @Mapping(target = "carYear", source = "carYear")
    @Mapping(target = "licensePlate", source = "licensePlate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "estimatedCost", source = "estimatedCost")
    @Mapping(target = "actualCost", source = "actualCost")
    @Mapping(target = "estimatedCompletionDate", source = "estimatedCompletionDate")
    @Mapping(target = "actualCompletionDate", source = "actualCompletionDate")
    @Mapping(target = "mechanicNotes", source = "mechanicNotes")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "statusHistory", source = "statusHistory")
    @Mapping(target = "client", source = "client")
    ServiceRequestDto toDto(ServiceRequest serviceRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    ClientDto mapClient(Client client);

    @Mapping(target = "fromStatus", source = "fromStatus")
    @Mapping(target = "toStatus", source = "toStatus")
    @Mapping(target = "changedBy", source = "changedBy")
    @Mapping(target = "reason", source = "reason")
    @Mapping(target = "createdAt", source = "createdAt")
    StatusHistoryDto mapStatusHistory(StatusHistory statusHistory);

    List<StatusHistoryDto> mapStatusHistoryList(List<StatusHistory> statusHistories);

    /**
     * Maps a CreateServiceRequestDto to a new ServiceRequest entity.
     * Sets the initial status to CREATED.
     *
     * @param createDto the source DTO
     * @return the mapped entity
     */

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "requestNumber", ignore = true)
    @Mapping(target = "statusHistory", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "actualCompletionDate", ignore = true)
    @Mapping(target = "actualCost", ignore = true)
    ServiceRequest toEntity(CreateServiceRequestDto createDto);



    /**
     * Updates an existing ServiceRequest entity with data from a ServiceRequestDto.
     * Ignores client and status history fields which should be updated through dedicated methods.
     *
     * @param dto the source DTO with updated data
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "requestNumber", ignore = true)
    @Mapping(target = "statusHistory", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(ServiceRequestDto dto, @MappingTarget ServiceRequest entity);

    List<ServiceRequestDto> toDtoList(List<ServiceRequest> serviceRequests);

    /**
     * Creates a Client reference with only the ID set.
     * Used for mapping client ID to a Client entity reference.
     *
     * @param id the client ID
     * @return a Client entity with only the ID set
     */
    default Client clientFromId(Long id) {
        if (id == null) {
            return null;
        }
        return Client.builder().id(id).build();
    }
}