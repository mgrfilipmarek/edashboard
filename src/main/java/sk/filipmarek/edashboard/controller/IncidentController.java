package sk.filipmarek.edashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.filipmarek.edashboard.model.Incident;
import sk.filipmarek.edashboard.model.IncidentType;
import sk.filipmarek.edashboard.model.SeverityLevel;
import sk.filipmarek.edashboard.service.IncidentService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/incident")
public class IncidentController {

    private final IncidentService incidentService;

    @PostMapping
    public ResponseEntity<Incident> save(@RequestBody Incident incident) {
        return ResponseEntity.ok(incidentService.insertIncident(incident));
    }

    @PutMapping
    public ResponseEntity<Incident> update(@RequestBody Incident incident) {
        return ResponseEntity.ok(incidentService.updateIncident(incident));
    }

    @GetMapping
    public ResponseEntity<Page<Incident>> findAll(
            Pageable pageable,
            @RequestParam(required = false) IncidentType incidentType,
            @RequestParam(required = false) Double locationLatitude,
            @RequestParam(required = false) Double locationLongitude,
            @RequestParam(required = false) LocalDateTime timestamp,
            @RequestParam(required = false) SeverityLevel severityLevel
    ) {
        return ResponseEntity.ok(incidentService.getAllIncidentsWithFilter(pageable, incidentType, locationLongitude, locationLatitude, timestamp, severityLevel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        incidentService.deleteIncident(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
