package sk.filipmarek.edashboard.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sk.filipmarek.edashboard.model.IncidentModel;

public interface IncidentRepository extends ElasticsearchRepository<IncidentModel, String> {
}
