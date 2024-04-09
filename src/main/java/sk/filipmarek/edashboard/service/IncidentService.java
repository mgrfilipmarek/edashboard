package sk.filipmarek.edashboard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sk.filipmarek.edashboard.model.Incident;
import sk.filipmarek.edashboard.model.IncidentType;
import sk.filipmarek.edashboard.model.SeverityLevel;

import java.time.LocalDateTime;
import java.util.List;

public interface IncidentService {

    List<Incident> getAllFromDB();

    List<Incident> getAllElastic();

    Page<Incident> getAllIncidentsWithFilter(Pageable pageable, IncidentType incidentType, Double locationLongitude, Double locationLatitude, LocalDateTime timestamp, SeverityLevel severityLevel);

    Incident insertIncident(Incident incident);

    Incident updateIncident(Incident incident);

    void deleteIncident(String id);
}
