package sk.filipmarek.edashboard.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Document(indexName = "incidentindex")
public class IncidentModel {

    @Id
    private String id;

    @Field(type = FieldType.Auto)
    private IncidentType incidentType;
    @Field(type = FieldType.Double)
    private double locationLatitude;
    @Field(type = FieldType.Double)
    private double locationLongitude;
    @Field(type = FieldType.Date)
    private Date timestamp;
    @Field(type = FieldType.Auto)
    private SeverityLevel severityLevel;

}
