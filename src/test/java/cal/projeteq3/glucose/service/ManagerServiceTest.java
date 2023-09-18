package cal.projeteq3.glucose.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.exception.request.JobOffreNotFoundException;
import cal.projeteq3.glucose.exception.request.ManagerNotFoundException;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.repository.CvRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ManagerServiceTest {
    @MockBean
    private CvRepository cvRepository;

    @MockBean
    private JobOfferRepository jobOfferRepository;

    @MockBean
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerService managerService;

    @MockBean
    private StudentRepository studentRepository;

    /**
     * Method under test: {@link ManagerService#updateGestionnaire(Long, ManagerDTO)}
     */
    @Test
    void testUpdateGestionnaire() {
        assertThrows(ManagerNotFoundException.class,
                () -> managerService.updateGestionnaire(1L, new ManagerDTO("Matricule", "6625550144")));
    }

    /**
     * Method under test: {@link ManagerService#deleteGestionnaire(Long)}
     */
    @Test
    void testDeleteGestionnaire() {
        managerService.deleteGestionnaire(1L);
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(8, managerService.getAllJobOffer().size());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#getAllCv()}
     */
    @Test
    void testGetAllCv() {
        List<CvFileDTO> actualAllCv = managerService.getAllCv();
        assertTrue(actualAllCv.isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
        assertSame(actualAllCv, managerService.getPendingCv());
    }

    /**
     * Method under test: {@link ManagerService#getPendingCv()}
     */
    @Test
    void testGetPendingCv() {
        List<CvFileDTO> actualPendingCv = managerService.getPendingCv();
        assertTrue(actualPendingCv.isEmpty());
        assertSame(actualPendingCv, managerService.getAllCv());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#getAllCvFileByStudent(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllCvFileByStudent() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.orElseThrow(Optional.java:377)
        //       at cal.projeteq3.glucose.service.ManagerService.getAllCvFileByStudent(ManagerService.java:91)
        //   See https://diff.blue/R013 to resolve this issue.

        managerService.getAllCvFileByStudent(1L);
    }

    /**
     * Method under test: {@link ManagerService#getAllCvFileByStudent(Long)}
     */
    @Test
    void testGetAllCvFileByStudent2() {
        List<CvFileDTO> actualAllCvFileByStudent = managerService.getAllCvFileByStudent(7L);
        assertTrue(actualAllCvFileByStudent.isEmpty());
        assertSame(actualAllCvFileByStudent, managerService.getPendingCv());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#getAllCvFileByStudent(Long)}
     */
    @Test
    void testGetAllCvFileByStudent3() {
        assertTrue(managerService.getAllCvFileByStudent(8L).isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#getAllCvFileByStudentMatricule(String)}
     */
    @Test
    void testGetAllCvFileByStudentMatricule() {
        List<CvFileDTO> actualAllCvFileByStudentMatricule = managerService.getAllCvFileByStudentMatricule("Matricule");
        assertTrue(actualAllCvFileByStudentMatricule.isEmpty());
        assertSame(actualAllCvFileByStudentMatricule, managerService.getAllCv());
        assertSame(actualAllCvFileByStudentMatricule, managerService.getPendingCv());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#deleteCvFile(Long)}
     */
    @Test
    void testDeleteCvFile() {
        managerService.deleteCvFile(1L);
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#deleteAllCvFileByStudendMatricule(String)}
     */
    @Test
    void testDeleteAllCvFileByStudendMatricule() {
        managerService.deleteAllCvFileByStudendMatricule("Matricule");
        List<CvFileDTO> allCv = managerService.getAllCv();
        assertTrue(allCv.isEmpty());
        assertSame(allCv, managerService.getPendingCv());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#deleteAllCvFileByStudendMatricule(String)}
     */
    @Test
    void testDeleteAllCvFileByStudendMatricule2() {
        managerService.deleteAllCvFileByStudendMatricule("cal.projeteq3.glucose.model.cvFile.CvFile");
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#getJobOfferByID(Long)}
     */
    @Test
    void testGetJobOfferByID() {
        JobOfferDTO actualJobOfferByID = managerService.getJobOfferByID(1L);
        assertEquals("_420B0", actualJobOfferByID.getDepartment());
        assertEquals("Front-end developer", actualJobOfferByID.getTitle());
        assertEquals(20.0f, actualJobOfferByID.getSalary());
        assertEquals("11:13:38.716623", actualJobOfferByID.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualJobOfferByID.getLocation());
        assertEquals(JobOfferState.SUBMITTED, actualJobOfferByID.getJobOfferState());
        assertEquals(1L, actualJobOfferByID.getId().longValue());
        assertEquals(15, actualJobOfferByID.getHoursPerWeek());
        assertEquals(7, actualJobOfferByID.getDuration());
        assertEquals("11:13:38.716623", actualJobOfferByID.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualJobOfferByID.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(8, managerService.getAllJobOffer().size());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.SUBMITTED);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.SUBMITTED, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(8, managerService.getAllJobOffer().size());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState2() {
        assertThrows(JobOffreNotFoundException.class,
                () -> managerService.updateJobOfferState(9L, JobOfferState.SUBMITTED));
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState3() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.OPEN);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.OPEN, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState4() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.PENDING);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.PENDING, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState5() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.EXPIRED);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.EXPIRED, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState6() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.TAKEN);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.TAKEN, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState7() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.REFUSED);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.REFUSED, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:13:53.481444", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState8() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.SUBMITTED);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.SUBMITTED, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(8, managerService.getAllJobOffer().size());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState9() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.OPEN);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.OPEN, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState10() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.PENDING);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.PENDING, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState11() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.EXPIRED);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.EXPIRED, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState12() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.TAKEN);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.TAKEN, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    /**
     * Method under test: {@link ManagerService#updateJobOfferState(Long, JobOfferState)}
     */
    @Test
    void testUpdateJobOfferState13() {
        JobOfferDTO actualUpdateJobOfferStateResult = managerService.updateJobOfferState(1L, JobOfferState.REFUSED);
        assertEquals("_420B0", actualUpdateJobOfferStateResult.getDepartment());
        assertEquals("Front-end developer", actualUpdateJobOfferStateResult.getTitle());
        assertEquals(20.0f, actualUpdateJobOfferStateResult.getSalary());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getStartDate().toLocalTime().toString());
        assertEquals("Montréal", actualUpdateJobOfferStateResult.getLocation());
        assertEquals(JobOfferState.REFUSED, actualUpdateJobOfferStateResult.getJobOfferState());
        assertEquals(1L, actualUpdateJobOfferStateResult.getId().longValue());
        assertEquals(15, actualUpdateJobOfferStateResult.getHoursPerWeek());
        assertEquals(7, actualUpdateJobOfferStateResult.getDuration());
        assertEquals("11:18:11.632352", actualUpdateJobOfferStateResult.getExpirationDate().toLocalTime().toString());
        assertEquals(
                "En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez l'opportunité"
                        + " de participer à des projets passionnants et innovants tout en acquérant une expérience précieuse dans"
                        + " le domaine du développement logiciel. Vous travaillerez en étroite collaboration avec notre équipe"
                        + " de développement pour contribuer au développement, à la maintenance et à l'amélioration de nos produits"
                        + " logiciels.",
                actualUpdateJobOfferStateResult.getDescription());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

}

