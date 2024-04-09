package sk.filipmarek.edashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.search.mapper.pojo.bridge.builtin.annotation.GeoPointBinding;
import org.hibernate.search.mapper.pojo.bridge.builtin.annotation.Latitude;
import org.hibernate.search.mapper.pojo.bridge.builtin.annotation.Longitude;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.time.LocalDateTime;

@Data
@Entity
@Indexed(index = "idx_incident")
@GeoPointBinding(fieldName = "location")
public class Incident {

    @Id
    @GenericField
    @UuidGenerator
    private String id;

    @Enumerated(EnumType.STRING)
    @GenericField
    private IncidentType incidentType;

    @Longitude
    @GenericField
    private Double locationLongitude;

    @Latitude
    @GenericField
    private Double locationLatitude;

    @GenericField
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @GenericField
    private SeverityLevel severityLevel;

}
