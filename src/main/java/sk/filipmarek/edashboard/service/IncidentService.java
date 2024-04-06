package sk.filipmarek.edashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.filipmarek.edashboard.model.IncidentModel;
import sk.filipmarek.edashboard.repository.IncidentRepository;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public Iterable<IncidentModel> getAllIncidents(){
        return incidentRepository.findAll();
    }

    public void insertIncident(IncidentModel incident) {
        incidentRepository.save(incident);
    }

    public IncidentModel updateIncident(IncidentModel incident) {
        return incidentRepository.save(incident);
    }
}
