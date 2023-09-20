package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.exception.request.EmployerNotFoundException;
import cal.projeteq3.glucose.exception.request.JobOffreNotFoundException;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.repository.EmployerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {

    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createEmployerTest() {

        //Arrange


        //Employer employer = new Employer("michel", "michaud", "test@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        Employer employer = Employer.builder()
                .firstName("michel")
                .lastName("michaud")
                .email("test@test.com")
                .password("Ose12asd3")
                .organisationName("Fritz")
                .organisationPhone("123-456-7890")
                .build();
        when(employerRepository.save(employer)).thenReturn(
                Employer.builder()
                        .firstName("michel")
                        .lastName("michaud")
                        .email("test@test.com")
                        .password("Ose12asd3")
                        .organisationName("Fritz")
                        .organisationPhone("123-456-7890")
                        .build()
        );

        //Act

        //employerService.createEmployer(employer);

        //Assert

        verify(employerRepository, times(1)).save(employer);

    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.InvalidDataAccessApiUsageException: detached entity passed to persist: cal.projeteq3.glucose.model.auth.Credentials
        //       at jdk.proxy5.$Proxy166.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateEmployer(EmployerService.java:92)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateEmployer(<generated>)
        //   org.hibernate.PersistentObjectException: detached entity passed to persist: cal.projeteq3.glucose.model.auth.Credentials
        //       at org.hibernate.event.internal.DefaultPersistEventListener.persist(DefaultPersistEventListener.java:88)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:77)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:138)
        //       at org.hibernate.internal.SessionImpl.firePersist(SessionImpl.java:797)
        //       at org.hibernate.internal.SessionImpl.persist(SessionImpl.java:745)
        //       at org.hibernate.engine.spi.CascadingActions$7.cascade(CascadingActions.java:290)
        //       at org.hibernate.engine.spi.CascadingActions$7.cascade(CascadingActions.java:280)
        //       at org.hibernate.engine.internal.Cascade.cascadeToOne(Cascade.java:513)
        //       at org.hibernate.engine.internal.Cascade.cascadeAssociation(Cascade.java:434)
        //       at org.hibernate.engine.internal.Cascade.cascadeProperty(Cascade.java:220)
        //       at org.hibernate.engine.internal.Cascade.cascade(Cascade.java:153)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.cascadeBeforeSave(AbstractSaveEventListener.java:459)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:275)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:180)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:136)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.entityIsTransient(DefaultPersistEventListener.java:175)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.persist(DefaultPersistEventListener.java:93)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:77)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:54)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.firePersist(SessionImpl.java:755)
        //       at org.hibernate.internal.SessionImpl.persist(SessionImpl.java:739)
        //       at jdk.proxy5.$Proxy163.persist(null)
        //       at jdk.proxy5.$Proxy166.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateEmployer(EmployerService.java:92)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateEmployer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        employerService.updateEmployer(1L, new EmployerDTO());
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    void testUpdateEmployer2() {
        assertThrows(EmployerNotFoundException.class, () -> employerService.updateEmployer(7L, new EmployerDTO()));
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.DataIntegrityViolationException: not-null property references a null or transient value : cal.projeteq3.glucose.model.auth.Credentials.email
        //       at jdk.proxy5.$Proxy166.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateEmployer(EmployerService.java:92)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateEmployer(<generated>)
        //   org.hibernate.PropertyValueException: not-null property references a null or transient value : cal.projeteq3.glucose.model.auth.Credentials.email
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:109)
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:54)
        //       at org.hibernate.event.internal.DefaultFlushEntityEventListener.scheduleUpdate(DefaultFlushEntityEventListener.java:248)
        //       at org.hibernate.event.internal.DefaultFlushEntityEventListener.onFlushEntity(DefaultFlushEntityEventListener.java:143)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.flushEntities(AbstractFlushingEventListener.java:221)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.flushEverythingToExecutions(AbstractFlushingEventListener.java:90)
        //       at org.hibernate.event.internal.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:38)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.doFlush(SessionImpl.java:1412)
        //       at org.hibernate.internal.SessionImpl.managedFlush(SessionImpl.java:485)
        //       at org.hibernate.internal.SessionImpl.flushBeforeTransactionCompletion(SessionImpl.java:2301)
        //       at org.hibernate.internal.SessionImpl.beforeTransactionCompletion(SessionImpl.java:1966)
        //       at org.hibernate.engine.jdbc.internal.JdbcCoordinatorImpl.beforeTransactionCompletion(JdbcCoordinatorImpl.java:439)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl.beforeCompletionCallback(JdbcResourceLocalTransactionCoordinatorImpl.java:169)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl$TransactionDriverControlImpl.commit(JdbcResourceLocalTransactionCoordinatorImpl.java:267)
        //       at org.hibernate.engine.transaction.internal.TransactionImpl.commit(TransactionImpl.java:101)
        //       at jdk.proxy5.$Proxy166.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateEmployer(EmployerService.java:92)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateEmployer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setId(1L);
        employerService.updateEmployer(1L, updatedEmployer);
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "cal.projeteq3.glucose.dto.user.EmployerDTO.getId()" because "updatedEmployer" is null
        //       at cal.projeteq3.glucose.service.EmployerService.updateEmployer(EmployerService.java:85)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateEmployer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        employerService.updateEmployer(1L, null);
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.DataIntegrityViolationException: not-null property references a null or transient value : cal.projeteq3.glucose.model.auth.Credentials.email
        //       at jdk.proxy5.$Proxy166.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateEmployer(EmployerService.java:92)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateEmployer(<generated>)
        //   org.hibernate.PropertyValueException: not-null property references a null or transient value : cal.projeteq3.glucose.model.auth.Credentials.email
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:109)
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:54)
        //       at org.hibernate.event.internal.DefaultFlushEntityEventListener.scheduleUpdate(DefaultFlushEntityEventListener.java:248)
        //       at org.hibernate.event.internal.DefaultFlushEntityEventListener.onFlushEntity(DefaultFlushEntityEventListener.java:143)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.flushEntities(AbstractFlushingEventListener.java:221)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.flushEverythingToExecutions(AbstractFlushingEventListener.java:90)
        //       at org.hibernate.event.internal.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:38)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.doFlush(SessionImpl.java:1412)
        //       at org.hibernate.internal.SessionImpl.managedFlush(SessionImpl.java:485)
        //       at org.hibernate.internal.SessionImpl.flushBeforeTransactionCompletion(SessionImpl.java:2301)
        //       at org.hibernate.internal.SessionImpl.beforeTransactionCompletion(SessionImpl.java:1966)
        //       at org.hibernate.engine.jdbc.internal.JdbcCoordinatorImpl.beforeTransactionCompletion(JdbcCoordinatorImpl.java:439)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl.beforeCompletionCallback(JdbcResourceLocalTransactionCoordinatorImpl.java:169)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl$TransactionDriverControlImpl.commit(JdbcResourceLocalTransactionCoordinatorImpl.java:267)
        //       at org.hibernate.engine.transaction.internal.TransactionImpl.commit(TransactionImpl.java:101)
        //       at jdk.proxy5.$Proxy166.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateEmployer(EmployerService.java:92)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateEmployer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setId(1L);
        employerService.updateEmployer(2L, updatedEmployer);
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.DataIntegrityViolationException: not-null property references a null or transient value : cal.projeteq3.glucose.model.auth.Credentials.email
        //       at jdk.proxy5.$Proxy166.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateEmployer(EmployerService.java:92)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateEmployer(<generated>)
        //   org.hibernate.PropertyValueException: not-null property references a null or transient value : cal.projeteq3.glucose.model.auth.Credentials.email
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:109)
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:54)
        //       at org.hibernate.event.internal.DefaultFlushEntityEventListener.scheduleUpdate(DefaultFlushEntityEventListener.java:248)
        //       at org.hibernate.event.internal.DefaultFlushEntityEventListener.onFlushEntity(DefaultFlushEntityEventListener.java:143)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.flushEntities(AbstractFlushingEventListener.java:221)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.flushEverythingToExecutions(AbstractFlushingEventListener.java:90)
        //       at org.hibernate.event.internal.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:38)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.doFlush(SessionImpl.java:1412)
        //       at org.hibernate.internal.SessionImpl.managedFlush(SessionImpl.java:485)
        //       at org.hibernate.internal.SessionImpl.flushBeforeTransactionCompletion(SessionImpl.java:2301)
        //       at org.hibernate.internal.SessionImpl.beforeTransactionCompletion(SessionImpl.java:1966)
        //       at org.hibernate.engine.jdbc.internal.JdbcCoordinatorImpl.beforeTransactionCompletion(JdbcCoordinatorImpl.java:439)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl.beforeCompletionCallback(JdbcResourceLocalTransactionCoordinatorImpl.java:169)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl$TransactionDriverControlImpl.commit(JdbcResourceLocalTransactionCoordinatorImpl.java:267)
        //       at org.hibernate.engine.transaction.internal.TransactionImpl.commit(TransactionImpl.java:101)
        //       at jdk.proxy5.$Proxy166.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateEmployer(EmployerService.java:92)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateEmployer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setId(7L);
        employerService.updateEmployer(1L, updatedEmployer);
    }

    /**
     * Method under test: {@link EmployerService#updateEmployer(Long, EmployerDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateEmployer7() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: cal.projeteq3.glucose.model.user.Employer.jobOffers: could not initialize proxy - no Session
        //       at org.hibernate.collection.spi.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:635)
        //       at org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:218)
        //       at org.hibernate.collection.spi.AbstractPersistentCollection.initialize(AbstractPersistentCollection.java:615)
        //       at org.hibernate.collection.spi.AbstractPersistentCollection.read(AbstractPersistentCollection.java:136)
        //       at org.hibernate.collection.spi.PersistentBag.iterator(PersistentBag.java:371)
        //       at java.util.Spliterators$IteratorSpliterator.estimateSize(Spliterators.java:1865)
        //       at java.util.Spliterator.getExactSizeIfKnown(Spliterator.java:414)
        //       at java.util.stream.AbstractPipeline.exactOutputSizeIfKnown(AbstractPipeline.java:470)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:574)
        //       at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
        //       at java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
        //       at cal.projeteq3.glucose.dto.user.EmployerDTO.<init>(EmployerDTO.java:29)
        //       at cal.projeteq3.glucose.service.EmployerService.updateEmployer(EmployerService.java:92)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateEmployer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setEmail("jane.doe@example.org");
        updatedEmployer.setId(1L);
        employerService.updateEmployer(1L, updatedEmployer);
    }

    /*@Test
    public void getAllEmployersTest() {

        //Arrange

        List<Employer> employers = new ArrayList<>();
        Employer employer1 = new Employer("michel1", "michaud1", "test1@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        Employer employer2 = new Employer("michel2", "michaud2", "test2@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        employers.add(employer1);
        employers.add(employer2);

        when(employerRepository.findAll()).thenReturn(employers);

        //Act

        List<EmployerDTO> employerList = employerService.getAllEmployers();

        //Assert

        assertEquals(2, employerList.size());
        verify(employerRepository, times(1)).findAll();

    }*/

    /*@Test
    public void getEmployerByIDTest() {

        //Arrange

        Long id = 1L;
        Employer employer = new Employer("michel", "michaud", "test@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        when(employerRepository.findById(id)).thenReturn(Optional.of(employer));

        //Act

        Optional<EmployerDTO> employerDTO = employerService.getEmployerByID(id);

        //Assert

        assertTrue(employerDTO.isPresent());
        verify(employerRepository, times(1)).findById(id);

    }*/

    @Test
    public void updateEmployerTest() {

        // Arrange

        Long employerId = 1L;
        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setId(employerId);
        updatedEmployer.setFirstName("UpdatedFirstName");
        updatedEmployer.setLastName("UpdatedLastName");
        updatedEmployer.setEmail("updated@example.com");
        updatedEmployer.setOrganisationName("UpdatedOrgName");
        updatedEmployer.setOrganisationPhone("123-456-7890");

        Employer existingEmployer = new Employer();
        existingEmployer.setId(employerId);
        existingEmployer.setFirstName("OriginalFirstName");
        existingEmployer.setLastName("OriginalLastName");
        existingEmployer.setEmail("original@example.com");
        existingEmployer.setOrganisationName("OriginalOrgName");
        existingEmployer.setOrganisationPhone("987-654-3210");

        when(employerRepository.findById(employerId)).thenReturn(Optional.of(existingEmployer));
        when(employerRepository.save(any(Employer.class))).thenReturn(existingEmployer);

        // Act

        EmployerDTO updatedDTO = employerService.updateEmployer(employerId, updatedEmployer);

        // Assert

        assertNotNull(updatedDTO);
        assertEquals(updatedEmployer.getId(), updatedDTO.getId());
        assertEquals(updatedEmployer.getFirstName(), updatedDTO.getFirstName());
        assertEquals(updatedEmployer.getLastName(), updatedDTO.getLastName());
        assertEquals(updatedEmployer.getEmail(), updatedDTO.getEmail());
        assertEquals(updatedEmployer.getOrganisationName(), updatedDTO.getOrganisationName());
        assertEquals(updatedEmployer.getOrganisationPhone(), updatedDTO.getOrganisationPhone());

    }

    /**
     * Method under test: {@link EmployerService#createJobOffer(JobOfferDTO, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateJobOffer() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.DataIntegrityViolationException: not-null property references a null or transient value : cal.projeteq3.glucose.model.jobOffer.JobOffer.department
        //       at jdk.proxy5.$Proxy171.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.createJobOffer(EmployerService.java:105)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.createJobOffer(<generated>)
        //   org.hibernate.PropertyValueException: not-null property references a null or transient value : cal.projeteq3.glucose.model.jobOffer.JobOffer.department
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:109)
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:54)
        //       at org.hibernate.action.internal.AbstractEntityInsertAction.nullifyTransientReferencesIfNotAlready(AbstractEntityInsertAction.java:124)
        //       at org.hibernate.action.internal.AbstractEntityInsertAction.makeEntityManaged(AbstractEntityInsertAction.java:133)
        //       at org.hibernate.engine.spi.ActionQueue.addResolvedEntityInsertAction(ActionQueue.java:299)
        //       at org.hibernate.engine.spi.ActionQueue.addInsertAction(ActionQueue.java:272)
        //       at org.hibernate.engine.spi.ActionQueue.addAction(ActionQueue.java:259)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.addInsertAction(AbstractSaveEventListener.java:376)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:277)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:180)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:136)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.entityIsTransient(DefaultPersistEventListener.java:175)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.persist(DefaultPersistEventListener.java:93)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:77)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:54)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.firePersist(SessionImpl.java:755)
        //       at org.hibernate.internal.SessionImpl.persist(SessionImpl.java:739)
        //       at jdk.proxy5.$Proxy163.persist(null)
        //       at jdk.proxy5.$Proxy171.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.createJobOffer(EmployerService.java:105)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.createJobOffer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        employerService.createJobOffer(new JobOfferDTO(), 1L);
    }

    /**
     * Method under test: {@link EmployerService#createJobOffer(JobOfferDTO, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateJobOffer2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.DataIntegrityViolationException: not-null property references a null or transient value : cal.projeteq3.glucose.model.jobOffer.JobOffer.department
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.createJobOffer(<generated>)
        //   org.hibernate.PropertyValueException: not-null property references a null or transient value : cal.projeteq3.glucose.model.jobOffer.JobOffer.department
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:109)
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:54)
        //       at org.hibernate.event.internal.DefaultFlushEntityEventListener.scheduleUpdate(DefaultFlushEntityEventListener.java:248)
        //       at org.hibernate.event.internal.DefaultFlushEntityEventListener.onFlushEntity(DefaultFlushEntityEventListener.java:143)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.flushEntities(AbstractFlushingEventListener.java:221)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.flushEverythingToExecutions(AbstractFlushingEventListener.java:90)
        //       at org.hibernate.event.internal.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:38)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.doFlush(SessionImpl.java:1412)
        //       at org.hibernate.internal.SessionImpl.managedFlush(SessionImpl.java:485)
        //       at org.hibernate.internal.SessionImpl.flushBeforeTransactionCompletion(SessionImpl.java:2301)
        //       at org.hibernate.internal.SessionImpl.beforeTransactionCompletion(SessionImpl.java:1966)
        //       at org.hibernate.engine.jdbc.internal.JdbcCoordinatorImpl.beforeTransactionCompletion(JdbcCoordinatorImpl.java:439)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl.beforeCompletionCallback(JdbcResourceLocalTransactionCoordinatorImpl.java:169)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl$TransactionDriverControlImpl.commit(JdbcResourceLocalTransactionCoordinatorImpl.java:267)
        //       at org.hibernate.engine.transaction.internal.TransactionImpl.commit(TransactionImpl.java:101)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.createJobOffer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        JobOfferDTO jobOffer = new JobOfferDTO();
        jobOffer.setId(1L);
        employerService.createJobOffer(jobOffer, 1L);
    }

    /**
     * Method under test: {@link EmployerService#createJobOffer(JobOfferDTO, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateJobOffer3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "cal.projeteq3.glucose.dto.JobOfferDTO.toEntity()" because "jobOffer" is null
        //       at cal.projeteq3.glucose.service.EmployerService.createJobOffer(EmployerService.java:103)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.createJobOffer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        employerService.createJobOffer(null, 1L);
    }

    /**
     * Method under test: {@link EmployerService#createJobOffer(JobOfferDTO, Long)}
     */
    @Test
    void testCreateJobOffer4() {
        assertThrows(EmployerNotFoundException.class, () -> employerService.createJobOffer(new JobOfferDTO(), 7L));
    }

    /**
     * Method under test: {@link EmployerService#updateJobOffer(JobOfferDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateJobOffer() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.InvalidDataAccessApiUsageException: The given id must not be null
        //       at jdk.proxy5.$Proxy171.findById(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateJobOffer(EmployerService.java:112)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateJobOffer(<generated>)
        //   java.lang.IllegalArgumentException: The given id must not be null
        //       at jdk.proxy5.$Proxy171.findById(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateJobOffer(EmployerService.java:112)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateJobOffer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        employerService.updateJobOffer(new JobOfferDTO());
    }

    /**
     * Method under test: {@link EmployerService#updateJobOffer(JobOfferDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateJobOffer2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.DataIntegrityViolationException: not-null property references a null or transient value : cal.projeteq3.glucose.model.jobOffer.JobOffer.department
        //       at jdk.proxy5.$Proxy171.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateJobOffer(EmployerService.java:116)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateJobOffer(<generated>)
        //   org.hibernate.PropertyValueException: not-null property references a null or transient value : cal.projeteq3.glucose.model.jobOffer.JobOffer.department
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:109)
        //       at org.hibernate.engine.internal.Nullability.checkNullability(Nullability.java:54)
        //       at org.hibernate.event.internal.DefaultFlushEntityEventListener.scheduleUpdate(DefaultFlushEntityEventListener.java:248)
        //       at org.hibernate.event.internal.DefaultFlushEntityEventListener.onFlushEntity(DefaultFlushEntityEventListener.java:143)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.flushEntities(AbstractFlushingEventListener.java:221)
        //       at org.hibernate.event.internal.AbstractFlushingEventListener.flushEverythingToExecutions(AbstractFlushingEventListener.java:90)
        //       at org.hibernate.event.internal.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:38)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
        //       at org.hibernate.internal.SessionImpl.doFlush(SessionImpl.java:1412)
        //       at org.hibernate.internal.SessionImpl.managedFlush(SessionImpl.java:485)
        //       at org.hibernate.internal.SessionImpl.flushBeforeTransactionCompletion(SessionImpl.java:2301)
        //       at org.hibernate.internal.SessionImpl.beforeTransactionCompletion(SessionImpl.java:1966)
        //       at org.hibernate.engine.jdbc.internal.JdbcCoordinatorImpl.beforeTransactionCompletion(JdbcCoordinatorImpl.java:439)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl.beforeCompletionCallback(JdbcResourceLocalTransactionCoordinatorImpl.java:169)
        //       at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl$TransactionDriverControlImpl.commit(JdbcResourceLocalTransactionCoordinatorImpl.java:267)
        //       at org.hibernate.engine.transaction.internal.TransactionImpl.commit(TransactionImpl.java:101)
        //       at jdk.proxy5.$Proxy171.save(null)
        //       at cal.projeteq3.glucose.service.EmployerService.updateJobOffer(EmployerService.java:116)
        //       at cal.projeteq3.glucose.service.EmployerService$$SpringCGLIB$$0.updateJobOffer(<generated>)
        //   See https://diff.blue/R013 to resolve this issue.

        JobOfferDTO updatedJobOffer = new JobOfferDTO();
        updatedJobOffer.setId(1L);
        employerService.updateJobOffer(updatedJobOffer);
    }

    /**
     * Method under test: {@link EmployerService#updateJobOffer(JobOfferDTO)}
     */
    @Test
    void testUpdateJobOffer3() {
        // Arrange
        JobOfferDTO nullJobOfferDTO = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            employerService.updateJobOffer(nullJobOfferDTO);
        });
    }

    /**
     * Method under test: {@link EmployerService#updateJobOffer(JobOfferDTO)}
     */
    @Test
    void testUpdateJobOffer4() {
        LocalDateTime startDate = LocalDate.of(1970, 1, 1).atStartOfDay();
        JobOfferDTO actualUpdateJobOfferResult = employerService.updateJobOffer(
                new JobOfferDTO(1L, "Dr", "Department", "Location", "The characteristics of someone or something", 10.0f,
                        startDate, 1, LocalDate.of(1970, 1, 1).atStartOfDay(), JobOfferState.SUBMITTED, 1, "Just cause"));
        assertEquals("Department", actualUpdateJobOfferResult.getDepartment());
        assertEquals("Dr", actualUpdateJobOfferResult.getTitle());
        assertEquals(10.0f, actualUpdateJobOfferResult.getSalary());
        assertEquals("00:00", actualUpdateJobOfferResult.getStartDate().toLocalTime().toString());
        assertEquals("Location", actualUpdateJobOfferResult.getLocation());
        assertEquals(JobOfferState.SUBMITTED, actualUpdateJobOfferResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferResult.getId().longValue());
        assertEquals(1, actualUpdateJobOfferResult.getHoursPerWeek());
        assertEquals(1, actualUpdateJobOfferResult.getDuration());
        assertEquals("00:00", actualUpdateJobOfferResult.getExpirationDate().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualUpdateJobOfferResult.getDescription());
    }

    /**
     * Method under test: {@link EmployerService#updateJobOffer(JobOfferDTO)}
     */
    @Test
    void testUpdateJobOffer5() {
        JobOfferDTO updatedJobOffer = new JobOfferDTO();
        updatedJobOffer.setId(9L);
        assertThrows(JobOffreNotFoundException.class, () -> employerService.updateJobOffer(updatedJobOffer));
    }

    @Test
    public void deleteEmployerTest() {

        // Arrange

        Long employerId = 1L;
        doNothing().when(employerRepository).deleteById(employerId);

        // Act

        employerService.deleteEmployer(employerId);

        // Assert

        verify(employerRepository, times(1)).deleteById(employerId);

    }

    /**
     * Method under test: {@link EmployerService#getJobOffersDTOByEmployerId(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetJobOffersDTOByEmployerId(){
        // Arrange
        Long employerId = 1L;
        List<JobOfferDTO> jobOffers = new ArrayList<>();
        jobOffers.add(new JobOfferDTO());
        when(employerRepository.findById(employerId)).thenReturn(Optional.of(new Employer()));
        when(employerService.getJobOffersDTOByEmployerId(employerId)).thenReturn(jobOffers);

        // Act
        List<JobOfferDTO> jobOfferDTOs = employerService.getJobOffersDTOByEmployerId(employerId);

        // Assert
        assertNotNull(jobOfferDTOs);
        assertFalse(jobOfferDTOs.isEmpty());
    }

    /**
     * Method under test: {@link EmployerService#getJobOffersDTOByEmployerId(Long)}
     */
    @Test
    void testGetJobOffersDTOByEmployerId2() {
        assertTrue(employerService.getJobOffersDTOByEmployerId(7L).isEmpty());
    }

    @Test
    public void testCreateEmployer() {
        RegisterEmployerDTO registerEmployerDTO = new RegisterEmployerDTO();
        when(employerRepository.save(any(Employer.class))).thenReturn(new Employer());
        EmployerDTO employerDTO = employerService.createEmployer(registerEmployerDTO);
        assertNotNull(employerDTO);
    }

    @Test
    public void testGetAllEmployers() {
        List<Employer> employers = new ArrayList<>();
        when(employerRepository.findAll()).thenReturn(employers);
        List<EmployerDTO> employerDTOs = employerService.getAllEmployers();
        assertNotNull(employerDTOs);
        assertTrue(employerDTOs.isEmpty());
    }

    @Test
    public void testGetEmployerByEmail() {
        String email = "test@example.com";
        Optional<Employer> employerOptional = Optional.of(new Employer());
        when(employerRepository.findByCredentialsEmail(email)).thenReturn(employerOptional);
        EmployerDTO employerDTO = employerService.getEmployerByEmail(email);
        assertNotNull(employerDTO);
    }

    @Test
    public void testGetEmployerByEmailNotFound() {
        String email = "test@example.com";
        when(employerRepository.findByCredentialsEmail(email)).thenReturn(Optional.empty());
        assertThrows(EmployerNotFoundException.class, () -> employerService.getEmployerByEmail(email));
    }

    @Test
    public void testGetEmployerByID() {
        Long id = 1L;
        Optional<Employer> employerOptional = Optional.of(new Employer());
        when(employerRepository.findById(id)).thenReturn(employerOptional);
        Optional<EmployerDTO> employerDTOOptional = employerService.getEmployerByID(id);
        assertTrue(employerDTOOptional.isPresent());
    }

    @Test
    public void testGetEmployerByIDNotFound() {
        Long id = 1L;
        when(employerRepository.findById(id)).thenReturn(Optional.empty());
        Optional<EmployerDTO> employerDTOOptional = employerService.getEmployerByID(id);
        assertFalse(employerDTOOptional.isPresent());
    }
}
