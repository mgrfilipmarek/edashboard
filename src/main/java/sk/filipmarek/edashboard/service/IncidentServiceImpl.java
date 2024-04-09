package sk.filipmarek.edashboard.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.spatial.DistanceUnit;
import org.hibernate.search.engine.spatial.GeoPoint;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sk.filipmarek.edashboard.model.Incident;
import sk.filipmarek.edashboard.model.IncidentType;
import sk.filipmarek.edashboard.model.SeverityLevel;
import sk.filipmarek.edashboard.repository.IncidentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final EntityManager entityManager;

    @Override
    public Page<Incident> getAllIncidentsWithFilter(Pageable pageable, IncidentType incidentType, Double locationLongitude, Double locationLatitude, LocalDateTime timestamp, SeverityLevel severityLevel) {
        SearchResult<Incident> result = Search.session(entityManager)
                .search(Incident.class)
                .where((f, root) -> {
                    root.add(f.matchAll());
                    if (incidentType != null) {
                        root.add(f.match().field("incidentType")
                                .matching(incidentType));
                    }
                    /*if (locationLatitude != null) {
                        root.add(f.match().fields("locationLatitude")
                                .matching(locationLatitude));
                    }
                    if (locationLongitude != null) {
                        root.add(f.match().fields("locationLongitude")
                                .matching(locationLongitude));
                    }*/
                    if (locationLatitude != null && locationLongitude != null) {
                        GeoPoint center = GeoPoint.of(locationLatitude, locationLongitude);
                        root.add(f.spatial().within().field("location")
                                .circle(center, 2, DistanceUnit.KILOMETERS));
                    }
                    if (timestamp != null) {
                        root.add(f.range().field("timestamp")
                                .between(timestamp.minusMinutes(5), timestamp.plusMinutes(5))
                        );
                    }
                    if (severityLevel != null) {
                        root.add(f.match().field("severityLevel")
                                .matching(severityLevel));
                    }
                })
                .fetch(pageable.getPageSize() * pageable.getPageNumber(), pageable.getPageSize());

        return new PageImpl<>(result.hits(), pageable, result.total().hitCount());
    }

    @Override
    public List<Incident> getAllElastic(){
        SearchResult<Incident> result = Search.session(entityManager)
                .search(Incident.class).where(SearchPredicateFactory::matchAll).fetchAll();
          return result.hits();
    }

    @Override
    public List<Incident> getAllFromDB() {
        return incidentRepository.findAll();
    }

    @Override
    public Incident insertIncident(Incident incident) {
        return incidentRepository.save(incident);
    }

    @Override
    public Incident updateIncident(Incident incident) {
        return incidentRepository.save(incident);
    }

    @Override
    public void deleteIncident(String id) {
        incidentRepository.deleteById(id);
    }
}
