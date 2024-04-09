package sk.filipmarek.edashboard.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sk.filipmarek.edashboard.model.Incident;
import sk.filipmarek.edashboard.testcontainers.IncidentElasticsearchContainer;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sk.filipmarek.edashboard.model.IncidentType.FIRE;
import static sk.filipmarek.edashboard.model.IncidentType.MEDICAL;
import static sk.filipmarek.edashboard.model.SeverityLevel.MEDIUM;

@ExtendWith(SpringExtension.class)
@Transactional
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IncidentServiceImplIntegrationTest {

    @Autowired
    private IncidentServiceImpl incidentService;

    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new IncidentElasticsearchContainer();

    @Autowired
    private EntityManager entityManager;

    private final int PAGE_SIZE = 5;

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @BeforeEach
    void testIsContainerRunning() throws InterruptedException {
        assertTrue(elasticsearchContainer.isRunning());

        SearchSession searchSession = Search.session(entityManager);
        MassIndexer indexer = searchSession.massIndexer(Incident.class);
        indexer.startAndWait();
    }

    @Test
    void testAllIncidents() {
        List<Incident> allDB = incidentService.getAllFromDB();
        List<Incident> allElastic = incidentService.getAllElastic();

        assertEquals(allDB.size(), allElastic.size());
    }

    @Test
    void testAllIncidentsPaged() {
        int totalHits = 6;

        PageRequest pageRequest = PageRequest.of(1, PAGE_SIZE);
        Page<Incident> fireIncidents = incidentService.getAllIncidentsWithFilter(pageRequest, FIRE, null, null, null, null);

        assertEquals(totalHits, fireIncidents.getTotalElements());
        assertEquals(1, fireIncidents.getContent().size());
    }

    @Test
    void testAllIncidentsFilterIncidentType() {
        int totalHits = 2;
        PageRequest pageRequest = PageRequest.ofSize(PAGE_SIZE);
        Page<Incident> fireIncidents = incidentService.getAllIncidentsWithFilter(pageRequest, MEDICAL, null, null, null, null);

        assertEquals(totalHits, fireIncidents.getTotalElements());
    }

    @Test
    void testAllIncidentsFilterLocation() {
        int totalHits = 1;
        PageRequest pageRequest = PageRequest.ofSize(PAGE_SIZE);
        Page<Incident> fireIncidents = incidentService.getAllIncidentsWithFilter(pageRequest, null, 14.001, 50.001, null, null);

        assertEquals(totalHits, fireIncidents.getTotalElements());
    }

    @Test
    void testAllIncidentsFilterTimestamp() {
        int totalHits = 1;
        PageRequest pageRequest = PageRequest.ofSize(PAGE_SIZE);
        LocalDateTime timestamp = LocalDateTime.of(2022, 4, 8, 12, 28, 0);

        Page<Incident> fireIncidents = incidentService.getAllIncidentsWithFilter(pageRequest, null, null, null, timestamp, null);

        assertEquals(totalHits, fireIncidents.getTotalElements());
    }

    @Test
    void testAllIncidentsFilterSeverityLevel() {
        int totalHits = 2;
        PageRequest pageRequest = PageRequest.ofSize(PAGE_SIZE);

        Page<Incident> fireIncidents = incidentService.getAllIncidentsWithFilter(pageRequest, null, null, null, null, MEDIUM);

        assertEquals(totalHits, fireIncidents.getTotalElements());
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }

}