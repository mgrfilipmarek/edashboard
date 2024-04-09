package sk.filipmarek.edashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.filipmarek.edashboard.model.Incident;

public interface IncidentRepository extends JpaRepository<Incident, String> {
}
