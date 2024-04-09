package sk.filipmarek.edashboard.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.filipmarek.edashboard.model.Incident;
import sk.filipmarek.edashboard.repository.IncidentRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static sk.filipmarek.edashboard.model.IncidentType.FIRE;
import static sk.filipmarek.edashboard.model.IncidentType.MEDICAL;

@ExtendWith(MockitoExtension.class)
class IncidentServiceImplUnitTest {

    @InjectMocks
    private IncidentServiceImpl incidentService;

    @Mock
    private IncidentRepository incidentRepository;

    @Test
    void getAllFromDB() {
        Incident incident1 = new Incident();
        incident1.setId(UUID.randomUUID().toString());
        incident1.setIncidentType(FIRE);

        Incident incident2 = new Incident();
        incident2.setId(UUID.randomUUID().toString());
        incident2.setIncidentType(MEDICAL);

        List<Incident> incidents = List.of(incident1, incident2);

        when(incidentRepository.findAll()).thenReturn(incidents);

        List<Incident> result = incidentService.getAllFromDB();

        assertNotEquals(null, result);
        assertEquals(2, result.size());
    }

    @Test
    void insertIncident() {
        Incident incident = new Incident();
        incident.setId(UUID.randomUUID().toString());
        incident.setIncidentType(FIRE);

        incidentService.insertIncident(incident);

        verify(incidentRepository).save(incident);
    }

    @Test
    void updateIncident() {
        // TODO test
    }

    @Test
    void deleteIncident() {
        // TODO test
    }

}