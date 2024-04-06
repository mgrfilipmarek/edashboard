package sk.filipmarek.edashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sk.filipmarek.edashboard.model.IncidentModel;
import sk.filipmarek.edashboard.repository.IncidentRepository;
import sk.filipmarek.edashboard.service.IncidentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incident")
public class IncidentController {

    private final IncidentService incidentService;

    @PostMapping
    public void save(@RequestBody IncidentModel incident) {
        incidentService.insertIncident(incident);
    }

    /*@GetMapping("/{id}")
    public IncidentModel findById(@PathVariable String id) {
        // TODO: orElse
        return incidentService.findById(id).orElse(null);
    }*/

    @GetMapping
    public Iterable<IncidentModel> findAll() {
        return incidentService.getAllIncidents();
    }

    /*@DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        incidentService.deleteById(id);
    }*/

    @PutMapping
    public void update(@RequestBody IncidentModel incident) {
        incidentService.updateIncident(incident);
    }

}
