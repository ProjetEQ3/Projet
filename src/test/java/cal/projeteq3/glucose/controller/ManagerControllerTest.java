package cal.projeteq3.glucose.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.repository.*;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

class ManagerControllerTest {
    /**
     * Method under test: {@link ManagerController#getJobOfferByEmploye(Long)}
     */
    @Test
    void testGetJobOfferByEmploye() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());

        Employer employer = new Employer();
        employer.setCredentials(credentials);
        employer.setEmail("jane.doe@example.org");
        employer.setFirstName("Jane");
        employer.setId(1L);
        ArrayList<JobOffer> jobOffers = new ArrayList<>();
        employer.setJobOffers(jobOffers);
        employer.setLastName("Doe");
        employer.setOrganisationName("Organisation Name");
        employer.setOrganisationPhone("6625550144");
        employer.setPassword("iloveyou");
        employer.setRole(Role.ADMIN);
        Optional<Employer> ofResult = Optional.of(employer);
        EmployerRepository employerRepository = mock(EmployerRepository.class);
        when(employerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        EmployerService employerService = new EmployerService(employerRepository, mock(JobOfferRepository.class));

        ResponseEntity<List<JobOfferDTO>> actualJobOfferByEmploye = (new ManagerController(
                new ManagerService(mock(ManagerRepository.class), mock(StudentRepository.class), mock(JobOfferRepository.class),
                        mock(CvFileRepository.class)),
                employerService)).getJobOfferByEmploye(1L);
        assertEquals(jobOffers, actualJobOfferByEmploye.getBody());
        assertEquals(200, actualJobOfferByEmploye.getStatusCodeValue());
        assertTrue(actualJobOfferByEmploye.getHeaders().isEmpty());
        verify(employerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ManagerController#getJobOfferByEmploye(Long)}
     */
    @Test
    void testGetJobOfferByEmploye2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());
        Employer employer = mock(Employer.class);
        when(employer.getJobOffers()).thenReturn(new ArrayList<>());
        doNothing().when(employer).setJobOffers(Mockito.<List<JobOffer>>any());
        doNothing().when(employer).setOrganisationName(Mockito.<String>any());
        doNothing().when(employer).setOrganisationPhone(Mockito.<String>any());
        doNothing().when(employer).setCredentials(Mockito.<Credentials>any());
        doNothing().when(employer).setEmail(Mockito.<String>any());
        doNothing().when(employer).setFirstName(Mockito.<String>any());
        doNothing().when(employer).setId(Mockito.<Long>any());
        doNothing().when(employer).setLastName(Mockito.<String>any());
        doNothing().when(employer).setPassword(Mockito.<String>any());
        doNothing().when(employer).setRole(Mockito.<Role>any());
        employer.setCredentials(credentials);
        employer.setEmail("jane.doe@example.org");
        employer.setFirstName("Jane");
        employer.setId(1L);
        ArrayList<JobOffer> jobOffers = new ArrayList<>();
        employer.setJobOffers(jobOffers);
        employer.setLastName("Doe");
        employer.setOrganisationName("Organisation Name");
        employer.setOrganisationPhone("6625550144");
        employer.setPassword("iloveyou");
        employer.setRole(Role.ADMIN);
        Optional<Employer> ofResult = Optional.of(employer);
        EmployerRepository employerRepository = mock(EmployerRepository.class);
        when(employerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        EmployerService employerService = new EmployerService(employerRepository, mock(JobOfferRepository.class));

        ResponseEntity<List<JobOfferDTO>> actualJobOfferByEmploye = (new ManagerController(
                new ManagerService(mock(ManagerRepository.class), mock(StudentRepository.class),
                        mock(JobOfferRepository.class), mock(CvFileRepository.class)),
                employerService)).getJobOfferByEmploye(1L);
        assertEquals(jobOffers, actualJobOfferByEmploye.getBody());
        assertEquals(200, actualJobOfferByEmploye.getStatusCodeValue());
        assertTrue(actualJobOfferByEmploye.getHeaders().isEmpty());
        verify(employerRepository).findById(Mockito.<Long>any());
        verify(employer).getJobOffers();
        verify(employer).setJobOffers(Mockito.<List<JobOffer>>any());
        verify(employer).setOrganisationName(Mockito.<String>any());
        verify(employer).setOrganisationPhone(Mockito.<String>any());
        verify(employer).setCredentials(Mockito.<Credentials>any());
        verify(employer).setEmail(Mockito.<String>any());
        verify(employer).setFirstName(Mockito.<String>any());
        verify(employer).setId(Mockito.<Long>any());
        verify(employer).setLastName(Mockito.<String>any());
        verify(employer).setPassword(Mockito.<String>any());
        verify(employer).setRole(Mockito.<Role>any());
    }

    /**
     * Method under test: {@link ManagerController#getJobOfferByEmploye(Long)}
     */
    @Test
    void testGetJobOfferByEmploye3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());

        Credentials credentials2 = new Credentials();
        credentials2.setEmail("jane.doe@example.org");
        credentials2.setId(1L);
        credentials2.setPassword("iloveyou");
        credentials2.setRole(Role.ADMIN);
        credentials2.setUser(new Employer());

        Employer employer = new Employer();
        employer.setCredentials(credentials2);
        employer.setEmail("jane.doe@example.org");
        employer.setFirstName("Jane");
        employer.setId(1L);
        employer.setJobOffers(new ArrayList<>());
        employer.setLastName("Doe");
        employer.setOrganisationName("Organisation Name");
        employer.setOrganisationPhone("6625550144");
        employer.setPassword("iloveyou");
        employer.setRole(Role.ADMIN);

        JobOffer jobOffer = new JobOffer();
        jobOffer.setDepartment("Department");
        jobOffer.setDescription("The characteristics of someone or something");
        jobOffer.setDuration(1);
        jobOffer.setEmployer(employer);
        jobOffer.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        jobOffer.setHoursPerWeek(1);
        jobOffer.setId(1L);
        jobOffer.setJobApplications(new ArrayList<>());
        jobOffer.setJobOfferState(JobOfferState.SUBMITTED);
        jobOffer.setLocation("Location");
        jobOffer.setSalary(10.0f);
        jobOffer.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        jobOffer.setTitle("Dr");

        ArrayList<JobOffer> jobOfferList = new ArrayList<>();
        jobOfferList.add(jobOffer);
        Employer employer2 = mock(Employer.class);
        when(employer2.getJobOffers()).thenReturn(jobOfferList);
        doNothing().when(employer2).setJobOffers(Mockito.<List<JobOffer>>any());
        doNothing().when(employer2).setOrganisationName(Mockito.<String>any());
        doNothing().when(employer2).setOrganisationPhone(Mockito.<String>any());
        doNothing().when(employer2).setCredentials(Mockito.<Credentials>any());
        doNothing().when(employer2).setEmail(Mockito.<String>any());
        doNothing().when(employer2).setFirstName(Mockito.<String>any());
        doNothing().when(employer2).setId(Mockito.<Long>any());
        doNothing().when(employer2).setLastName(Mockito.<String>any());
        doNothing().when(employer2).setPassword(Mockito.<String>any());
        doNothing().when(employer2).setRole(Mockito.<Role>any());
        employer2.setCredentials(credentials);
        employer2.setEmail("jane.doe@example.org");
        employer2.setFirstName("Jane");
        employer2.setId(1L);
        employer2.setJobOffers(new ArrayList<>());
        employer2.setLastName("Doe");
        employer2.setOrganisationName("Organisation Name");
        employer2.setOrganisationPhone("6625550144");
        employer2.setPassword("iloveyou");
        employer2.setRole(Role.ADMIN);
        Optional<Employer> ofResult = Optional.of(employer2);
        EmployerRepository employerRepository = mock(EmployerRepository.class);
        when(employerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        EmployerService employerService = new EmployerService(employerRepository, mock(JobOfferRepository.class));

        ResponseEntity<List<JobOfferDTO>> actualJobOfferByEmploye = (new ManagerController(
                new ManagerService(mock(ManagerRepository.class), mock(StudentRepository.class),
                        mock(JobOfferRepository.class), mock(CvFileRepository.class)),
                employerService)).getJobOfferByEmploye(1L);
        assertTrue(actualJobOfferByEmploye.hasBody());
        assertEquals(200, actualJobOfferByEmploye.getStatusCodeValue());
        assertTrue(actualJobOfferByEmploye.getHeaders().isEmpty());
        verify(employerRepository).findById(Mockito.<Long>any());
        verify(employer2).getJobOffers();
        verify(employer2).setJobOffers(Mockito.<List<JobOffer>>any());
        verify(employer2).setOrganisationName(Mockito.<String>any());
        verify(employer2).setOrganisationPhone(Mockito.<String>any());
        verify(employer2).setCredentials(Mockito.<Credentials>any());
        verify(employer2).setEmail(Mockito.<String>any());
        verify(employer2).setFirstName(Mockito.<String>any());
        verify(employer2).setId(Mockito.<Long>any());
        verify(employer2).setLastName(Mockito.<String>any());
        verify(employer2).setPassword(Mockito.<String>any());
        verify(employer2).setRole(Mockito.<Role>any());
    }

    /**
     * Method under test: {@link ManagerController#getJobOfferByEmploye(Long)}
     */
    @Test
    void testGetJobOfferByEmploye4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        Credentials credentials = new Credentials();
        credentials.setEmail("jane.doe@example.org");
        credentials.setId(1L);
        credentials.setPassword("iloveyou");
        credentials.setRole(Role.ADMIN);
        credentials.setUser(new Employer());

        Credentials credentials2 = new Credentials();
        credentials2.setEmail("jane.doe@example.org");
        credentials2.setId(1L);
        credentials2.setPassword("iloveyou");
        credentials2.setRole(Role.ADMIN);
        credentials2.setUser(new Employer());

        Employer employer = new Employer();
        employer.setCredentials(credentials2);
        employer.setEmail("jane.doe@example.org");
        employer.setFirstName("Jane");
        employer.setId(1L);
        employer.setJobOffers(new ArrayList<>());
        employer.setLastName("Doe");
        employer.setOrganisationName("Organisation Name");
        employer.setOrganisationPhone("6625550144");
        employer.setPassword("iloveyou");
        employer.setRole(Role.ADMIN);

        JobOffer jobOffer = new JobOffer();
        jobOffer.setDepartment("Department");
        jobOffer.setDescription("The characteristics of someone or something");
        jobOffer.setDuration(1);
        jobOffer.setEmployer(employer);
        jobOffer.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        jobOffer.setHoursPerWeek(1);
        jobOffer.setId(1L);
        jobOffer.setJobApplications(new ArrayList<>());
        jobOffer.setJobOfferState(JobOfferState.SUBMITTED);
        jobOffer.setLocation("Location");
        jobOffer.setSalary(10.0f);
        jobOffer.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        jobOffer.setTitle("Dr");

        Credentials credentials3 = new Credentials();
        credentials3.setEmail("john.smith@example.org");
        credentials3.setId(2L);
        credentials3.setPassword("Password");
        credentials3.setRole(Role.MANAGER);
        credentials3.setUser(new Employer());

        Employer employer2 = new Employer();
        employer2.setCredentials(credentials3);
        employer2.setEmail("john.smith@example.org");
        employer2.setFirstName("John");
        employer2.setId(2L);
        employer2.setJobOffers(new ArrayList<>());
        employer2.setLastName("Smith");
        employer2.setOrganisationName("cal.projeteq3.glucose.model.user.Employer");
        employer2.setOrganisationPhone("8605550118");
        employer2.setPassword("Password");
        employer2.setRole(Role.MANAGER);

        JobOffer jobOffer2 = new JobOffer();
        jobOffer2.setDepartment("cal.projeteq3.glucose.model.jobOffer.JobOffer");
        jobOffer2.setDescription("Description");
        jobOffer2.setDuration(0);
        jobOffer2.setEmployer(employer2);
        jobOffer2.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        jobOffer2.setHoursPerWeek(0);
        jobOffer2.setId(2L);
        jobOffer2.setJobApplications(new ArrayList<>());
        jobOffer2.setJobOfferState(JobOfferState.OPEN);
        jobOffer2.setLocation("cal.projeteq3.glucose.model.jobOffer.JobOffer");
        jobOffer2.setSalary(0.5f);
        jobOffer2.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        jobOffer2.setTitle("Mr");

        ArrayList<JobOffer> jobOfferList = new ArrayList<>();
        jobOfferList.add(jobOffer2);
        jobOfferList.add(jobOffer);
        Employer employer3 = mock(Employer.class);
        when(employer3.getJobOffers()).thenReturn(jobOfferList);
        doNothing().when(employer3).setJobOffers(Mockito.<List<JobOffer>>any());
        doNothing().when(employer3).setOrganisationName(Mockito.<String>any());
        doNothing().when(employer3).setOrganisationPhone(Mockito.<String>any());
        doNothing().when(employer3).setCredentials(Mockito.<Credentials>any());
        doNothing().when(employer3).setEmail(Mockito.<String>any());
        doNothing().when(employer3).setFirstName(Mockito.<String>any());
        doNothing().when(employer3).setId(Mockito.<Long>any());
        doNothing().when(employer3).setLastName(Mockito.<String>any());
        doNothing().when(employer3).setPassword(Mockito.<String>any());
        doNothing().when(employer3).setRole(Mockito.<Role>any());
        employer3.setCredentials(credentials);
        employer3.setEmail("jane.doe@example.org");
        employer3.setFirstName("Jane");
        employer3.setId(1L);
        employer3.setJobOffers(new ArrayList<>());
        employer3.setLastName("Doe");
        employer3.setOrganisationName("Organisation Name");
        employer3.setOrganisationPhone("6625550144");
        employer3.setPassword("iloveyou");
        employer3.setRole(Role.ADMIN);
        Optional<Employer> ofResult = Optional.of(employer3);
        EmployerRepository employerRepository = mock(EmployerRepository.class);
        when(employerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        EmployerService employerService = new EmployerService(employerRepository, mock(JobOfferRepository.class));

        ResponseEntity<List<JobOfferDTO>> actualJobOfferByEmploye = (new ManagerController(
                new ManagerService(mock(ManagerRepository.class), mock(StudentRepository.class),
                        mock(JobOfferRepository.class), mock(CvFileRepository.class)),
                employerService)).getJobOfferByEmploye(1L);
        assertTrue(actualJobOfferByEmploye.hasBody());
        assertEquals(200, actualJobOfferByEmploye.getStatusCodeValue());
        assertTrue(actualJobOfferByEmploye.getHeaders().isEmpty());
        verify(employerRepository).findById(Mockito.<Long>any());
        verify(employer3).getJobOffers();
        verify(employer3).setJobOffers(Mockito.<List<JobOffer>>any());
        verify(employer3).setOrganisationName(Mockito.<String>any());
        verify(employer3).setOrganisationPhone(Mockito.<String>any());
        verify(employer3).setCredentials(Mockito.<Credentials>any());
        verify(employer3).setEmail(Mockito.<String>any());
        verify(employer3).setFirstName(Mockito.<String>any());
        verify(employer3).setId(Mockito.<Long>any());
        verify(employer3).setLastName(Mockito.<String>any());
        verify(employer3).setPassword(Mockito.<String>any());
        verify(employer3).setRole(Mockito.<Role>any());
    }

    /**
     * Method under test: {@link ManagerController#getJobOfferByEmploye(Long)}
     */
    @Test
    void testGetJobOfferByEmploye5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        EmployerRepository employerRepository = mock(EmployerRepository.class);
        Optional<Employer> emptyResult = Optional.empty();
        when(employerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        EmployerService employerService = new EmployerService(employerRepository, mock(JobOfferRepository.class));

        ResponseEntity<List<JobOfferDTO>> actualJobOfferByEmploye = (new ManagerController(
                new ManagerService(mock(ManagerRepository.class), mock(StudentRepository.class),
                        mock(JobOfferRepository.class), mock(CvFileRepository.class)),
                employerService)).getJobOfferByEmploye(1L);
        assertTrue(actualJobOfferByEmploye.hasBody());
        assertEquals(200, actualJobOfferByEmploye.getStatusCodeValue());
        assertTrue(actualJobOfferByEmploye.getHeaders().isEmpty());
        verify(employerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ManagerController#getJobOfferByEmploye(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetJobOfferByEmploye6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "cal.projeteq3.glucose.service.EmployerService.getJobOffersDTOByEmployerId(java.lang.Long)" because "this.employerService" is null
        //       at cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(ManagerController.java:40)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ManagerController(new ManagerService(mock(ManagerRepository.class), mock(StudentRepository.class),
                mock(JobOfferRepository.class), mock(CvFileRepository.class)), null)).getJobOfferByEmploye(1L);
    }

    /**
     * Method under test: {@link ManagerController#getJobOfferByEmploye(Long)}
     */
    @Test
    void testGetJobOfferByEmploye7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/1': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        EmployerService employerService = mock(EmployerService.class);
        when(employerService.getJobOffersDTOByEmployerId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        ResponseEntity<List<JobOfferDTO>> actualJobOfferByEmploye = (new ManagerController(
                new ManagerService(mock(ManagerRepository.class), mock(StudentRepository.class),
                        mock(JobOfferRepository.class), mock(CvFileRepository.class)),
                employerService)).getJobOfferByEmploye(1L);
        assertTrue(actualJobOfferByEmploye.hasBody());
        assertEquals(200, actualJobOfferByEmploye.getStatusCodeValue());
        assertTrue(actualJobOfferByEmploye.getHeaders().isEmpty());
        verify(employerService).getJobOffersDTOByEmployerId(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ManagerController#getJobOfferByState(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetJobOfferByState() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/Job%20Offer%20State': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/Job%20Offer%20State': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        ManagerService managerService = new ManagerService(mock(ManagerRepository.class), mock(StudentRepository.class),
                mock(JobOfferRepository.class), mock(CvFileRepository.class));

        (new ManagerController(managerService,
                new EmployerService(mock(EmployerRepository.class), mock(JobOfferRepository.class))))
                .getJobOfferByState("Job Offer State");
    }

    /**
     * Method under test: {@link ManagerController#getJobOfferByState(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetJobOfferByState2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/Job%20Offer%20State': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.IllegalStateException: Ambiguous handler methods mapped for '/manager/jobOffers/Job%20Offer%20State': {public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByState(java.lang.String), public org.springframework.http.ResponseEntity cal.projeteq3.glucose.controller.ManagerController.getJobOfferByEmploye(java.lang.Long)}
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        ManagerService managerService = new ManagerService(mock(ManagerRepository.class), mock(StudentRepository.class),
                mock(JobOfferRepository.class), mock(CvFileRepository.class));

        (new ManagerController(managerService,
                new EmployerService(mock(EmployerRepository.class), mock(JobOfferRepository.class))))
                .getJobOfferByState("foo");
    }
}

