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
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.repository.CvRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * Method under test: {@link ManagerService#createGestionnaire(Manager)}
     */
    /*@Test
    void testCreateGestionnaire() {
        Manager manager = new Manager();
        manager.setEmail("jane.doe@example.org");
        manager.setFirstName("Jane");
        manager.setLastName("Doe");
        manager.setMatricule("Matricule");
        manager.setPassword("iloveyou");
        manager.setPhoneNumber("6625550144");
        manager.setId(1L);
        when(managerRepository.save(Mockito.<Manager>any())).thenReturn(manager);

        Manager manager2 = new Manager();
        manager2.setEmail("jane.doe@example.org");
        manager2.setFirstName("Jane");
        manager2.setLastName("Doe");
        manager2.setMatricule("Matricule");
        manager2.setPassword("iloveyou");
        manager2.setPhoneNumber("6625550144");
        manager2.setId(1L);
        ManagerDTO actualCreateGestionnaireResult = managerService.createGestionnaire(manager2);
        assertEquals("jane.doe@example.org", actualCreateGestionnaireResult.getEmail());
        assertEquals("6625550144", actualCreateGestionnaireResult.getPhoneNumber());
        assertEquals("Matricule", actualCreateGestionnaireResult.getMatricule());
        assertEquals("Doe", actualCreateGestionnaireResult.getLastName());
        assertEquals(1L, actualCreateGestionnaireResult.getId().longValue());
        assertEquals("Jane", actualCreateGestionnaireResult.getFirstName());
        verify(managerRepository).save(Mockito.<Manager>any());
    }

    *//**
     * Method under test: {@link ManagerService#createGestionnaire(Manager)}
     *//*
    @Test
    void testCreateGestionnaire2() {
        when(managerRepository.save(Mockito.<Manager>any())).thenThrow(new IllegalArgumentException("foo"));

        Manager manager = new Manager();
        manager.setEmail("jane.doe@example.org");
        manager.setFirstName("Jane");
        manager.setLastName("Doe");
        manager.setMatricule("Matricule");
        manager.setPassword("iloveyou");
        manager.setPhoneNumber("6625550144");
        manager.setId(1L);
        assertThrows(IllegalArgumentException.class, () -> managerService.createGestionnaire(manager));
        verify(managerRepository).save(Mockito.<Manager>any());
    }

    *//**
     * Method under test: {@link ManagerService#createGestionnaire(Manager)}
     *//*
    @Test
    void testCreateGestionnaire3() {
        Manager manager = new Manager();
        manager.setEmail("jane.doe@example.org");
        manager.setFirstName("Jane");
        manager.setLastName("Doe");
        manager.setMatricule("Matricule");
        manager.setPassword("iloveyou");
        manager.setPhoneNumber("6625550144");
        manager.setId(1L);

        when(managerRepository.save(Mockito.<Manager>any())).thenReturn(manager);
        List<Manager> list = new ArrayList<>();
        list.add(manager);
        when(managerRepository.findAll()).thenReturn(list);

        ManagerDTO actualCreateGestionnaireResult = managerService.createGestionnaire(manager);
        assertEquals("jane.doe@example.org", actualCreateGestionnaireResult.getEmail());
        assertEquals("6625550144", actualCreateGestionnaireResult.getPhoneNumber());
        assertEquals("Matricule", actualCreateGestionnaireResult.getMatricule());
        assertEquals("Doe", actualCreateGestionnaireResult.getLastName());
        assertEquals(1L, actualCreateGestionnaireResult.getId().longValue());
        assertEquals("Jane", actualCreateGestionnaireResult.getFirstName());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    *//**
     * Method under test: {@link ManagerService#createGestionnaire(Manager)}
     *//*
    @Test
    void testCreateGestionnaire4() {
        Manager manager = Manager.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .matricule("Matricule")
                .password("iloveyou")
                .phoneNumber("6625550144")
                .id(1L)
                .build();

        when(managerRepository.save(Mockito.<Manager>any())).thenReturn(manager);
        List<Manager> list = new ArrayList<>();
        list.add(manager);
        when(managerRepository.findAll()).thenReturn(list);

        ManagerDTO actualCreateGestionnaireResult = managerService.createGestionnaire(manager);
        assertEquals("jane.doe@example.org", actualCreateGestionnaireResult.getEmail());
        assertEquals("6625550144", actualCreateGestionnaireResult.getPhoneNumber());
        assertEquals("Matricule", actualCreateGestionnaireResult.getMatricule());
        assertEquals("Doe", actualCreateGestionnaireResult.getLastName());
        assertEquals(1L, actualCreateGestionnaireResult.getId().longValue());
        assertEquals("Jane", actualCreateGestionnaireResult.getFirstName());
        assertTrue(managerService.getAllCv().isEmpty());
        assertEquals(1, managerService.getAllGestionnaires().size());
    }

    *//**
     * Method under test: {@link ManagerService#getAllGestionnaires()}
     *//*
    @Test
    void testGetAllGestionnaires() {
        ArrayList<Manager> managerList = new ArrayList<>();
        when(managerRepository.findAll()).thenReturn(managerList);
        List<Manager> actualAllGestionnaires = managerService.getAllGestionnaires();
        assertSame(managerList, actualAllGestionnaires);
        assertTrue(actualAllGestionnaires.isEmpty());
        verify(managerRepository).findAll();
    }

    *//**
     * Method under test: {@link ManagerService#getAllGestionnaires()}
     *//*
    @Test
    void testGetAllGestionnaires2() {
        when(managerRepository.findAll()).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> managerService.getAllGestionnaires());
        verify(managerRepository).findAll();
    }

    *//**
     * Method under test: {@link ManagerService#getGestionnaireByID(Long)}
     *//*
    @Test
    void testGetGestionnaireByID() {
        Manager manager = new Manager();
        manager.setEmail("jane.doe@example.org");
        manager.setFirstName("Jane");
        manager.setLastName("Doe");
        manager.setMatricule("Matricule");
        manager.setPassword("iloveyou");
        manager.setPhoneNumber("6625550144");
        manager.setId(1L);
        Optional<Manager> ofResult = Optional.of(manager);
        when(managerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Manager> actualGestionnaireByID = managerService.getGestionnaireByID(1L);
        assertSame(ofResult, actualGestionnaireByID);
        assertTrue(actualGestionnaireByID.isPresent());
        verify(managerRepository).findById(Mockito.<Long>any());
    }

    *//**
     * Method under test: {@link ManagerService#getGestionnaireByID(Long)}
     *//*
    @Test
    void testGetGestionnaireByID2() {
        when(managerRepository.findById(Mockito.<Long>any())).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> managerService.getGestionnaireByID(1L));
        verify(managerRepository).findById(Mockito.<Long>any());
    }

    *//**
     * Method under test: {@link ManagerService#getGestionnaireByID(Long)}
     *//*
    @Test
    void testGetGestionnaireByID3() {
        assertFalse(managerService.getGestionnaireByID(2L).isPresent());
        assertTrue(managerService.getAllCv().isEmpty());
    }

    *//**
     * Method under test: {@link ManagerService#updateGestionnaire(Long, Manager)}
     *//*
    @Test
    void testUpdateGestionnaire() {
        Manager manager = new Manager();
        manager.setEmail("jane.doe@example.org");
        manager.setFirstName("Jane");
        manager.setLastName("Doe");
        manager.setMatricule("Matricule");
        manager.setPassword("iloveyou");
        manager.setPhoneNumber("6625550144");
        manager.setId(1L);
        Optional<Manager> ofResult = Optional.of(manager);

        Manager manager2 = new Manager();
        manager2.setEmail("jane.doe@example.org");
        manager2.setFirstName("Jane");
        manager2.setLastName("Doe");
        manager2.setMatricule("Matricule");
        manager2.setPassword("iloveyou");
        manager2.setPhoneNumber("6625550144");
        manager2.setId(1L);
        when(managerRepository.save(Mockito.<Manager>any())).thenReturn(manager2);
        when(managerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Manager updatedManager = new Manager();
        updatedManager.setEmail("jane.doe@example.org");
        updatedManager.setFirstName("Jane");
        updatedManager.setLastName("Doe");
        updatedManager.setMatricule("Matricule");
        updatedManager.setPassword("iloveyou");
        updatedManager.setPhoneNumber("6625550144");
        updatedManager.setId(1L);
        assertSame(manager2, managerService.updateGestionnaire(1L, updatedManager));
        verify(managerRepository).save(Mockito.<Manager>any());
        verify(managerRepository).findById(Mockito.<Long>any());
    }

    *//**
     * Method under test: {@link ManagerService#updateGestionnaire(Long, Manager)}
     *//*
    @Test
    void testUpdateGestionnaire2() {
        Manager manager = new Manager();
        manager.setEmail("jane.doe@example.org");
        manager.setFirstName("Jane");
        manager.setLastName("Doe");
        manager.setMatricule("Matricule");
        manager.setPassword("iloveyou");
        manager.setPhoneNumber("6625550144");
        manager.setId(1L);
        Optional<Manager> ofResult = Optional.of(manager);
        when(managerRepository.save(Mockito.<Manager>any())).thenThrow(new IllegalArgumentException("foo"));
        when(managerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Manager updatedManager = new Manager();
        updatedManager.setEmail("jane.doe@example.org");
        updatedManager.setFirstName("Jane");
        updatedManager.setLastName("Doe");
        updatedManager.setMatricule("Matricule");
        updatedManager.setPassword("iloveyou");
        updatedManager.setPhoneNumber("6625550144");
        updatedManager.setId(1L);
        assertThrows(IllegalArgumentException.class, () -> managerService.updateGestionnaire(1L, updatedManager));
        verify(managerRepository).save(Mockito.<Manager>any());
        verify(managerRepository).findById(Mockito.<Long>any());
    }

    *//**
     * Method under test: {@link ManagerService#updateGestionnaire(Long, Manager)}
     *//*
    @Test
    void testUpdateGestionnaire3() {
        Optional<Manager> emptyResult = Optional.empty();
        when(managerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Manager updatedManager = new Manager();
        updatedManager.setEmail("jane.doe@example.org");
        updatedManager.setFirstName("Jane");
        updatedManager.setLastName("Doe");
        updatedManager.setMatricule("Matricule");
        updatedManager.setPassword("iloveyou");
        updatedManager.setPhoneNumber("6625550144");
        updatedManager.setId(1L);
        assertThrows(IllegalArgumentException.class, () -> managerService.updateGestionnaire(1L, updatedManager));
        verify(managerRepository).findById(Mockito.<Long>any());
    }

    *//**
     * Method under test: {@link ManagerService#updateGestionnaire(Long, Manager)}
     *//*
    @Test
    void testUpdateGestionnaire4() {
        Manager updatedManager = new Manager();
        updatedManager.setEmail("jane.doe@example.org");
        updatedManager.setFirstName("Jane");
        updatedManager.setLastName("Doe");
        updatedManager.setMatricule("Matricule");
        updatedManager.setPassword("iloveyou");
        updatedManager.setPhoneNumber("6625550144");
        updatedManager.setId(1L);
        assertThrows(IllegalArgumentException.class, () -> managerService.updateGestionnaire(1L, updatedManager));
    }

    *//**
     * Method under test: {@link ManagerService#updateGestionnaire(Long, Manager)}
     *//*
    @Test
    void testUpdateGestionnaire5() {
        Manager updatedManager = new Manager();
        updatedManager.setEmail("jane.doe@example.org");
        updatedManager.setFirstName("Jane");
        updatedManager.setLastName("Doe");
        updatedManager.setMatricule("Matricule");
        updatedManager.setPassword("iloveyou");
        updatedManager.setPhoneNumber("6625550144");
        updatedManager.setId(1L);
        assertThrows(IllegalArgumentException.class, () -> managerService.updateGestionnaire(2L, updatedManager));
    }

    *//**
     * Method under test: {@link ManagerService#deleteGestionnaire(Long)}
     *//*
    @Test
    void testDeleteGestionnaire() {
        doNothing().when(managerRepository).deleteById(Mockito.<Long>any());
        managerService.deleteGestionnaire(1L);
        verify(managerRepository).deleteById(Mockito.<Long>any());
    }

    *//**
     * Method under test: {@link ManagerService#deleteGestionnaire(Long)}
     *//*
    @Test
    void testDeleteGestionnaire2() {
        doThrow(new IllegalArgumentException("foo")).when(managerRepository).deleteById(Mockito.<Long>any());
        assertThrows(IllegalArgumentException.class, () -> managerService.deleteGestionnaire(1L));
        verify(managerRepository).deleteById(Mockito.<Long>any());
    }

    *//**
     * Method under test: {@link ManagerService#deleteGestionnaire(Long)}
     *//*
    @Test
    void testDeleteGestionnaire3() {
        managerService.deleteGestionnaire(1L);
        assertTrue(managerService.getAllCv().isEmpty());
        assertTrue(managerService.getAllGestionnaires().isEmpty());
    }

    *//**
     * Method under test: {@link ManagerService#getAllCv()}
     *//*
    @Test
    void testGetAllCv() {
        List<CvFileDTO> actualAllCv = managerService.getAllCv();
        assertTrue(actualAllCv.isEmpty());
        assertTrue(managerService.getAllGestionnaires().isEmpty());
        assertSame(actualAllCv, managerService.getPendingCv());
    }

    *//**
     * Method under test: {@link ManagerService#getPendingCv()}
     *//*
    @Test
    void testGetPendingCv() {
        List<CvFileDTO> actualPendingCv = managerService.getPendingCv();
        assertTrue(actualPendingCv.isEmpty());
        assertSame(actualPendingCv, managerService.getAllCv());
        assertTrue(managerService.getAllGestionnaires().isEmpty());
    }

    *//**
     * Method under test: {@link ManagerService#getAllCvFileByStudentMatricule(String)}
     *//*
    @Test
    void testGetAllCvFileByStudentMatricule() {
        List<CvFileDTO> actualAllCvFileByStudentMatricule = managerService.getAllCvFileByStudentMatricule("Matricule");
        assertTrue(actualAllCvFileByStudentMatricule.isEmpty());
        assertSame(actualAllCvFileByStudentMatricule, managerService.getAllCv());
        assertSame(actualAllCvFileByStudentMatricule, managerService.getPendingCv());
        assertTrue(managerService.getAllGestionnaires().isEmpty());
    }

    *//**
     * Method under test: {@link ManagerService#getAllCvFileByStudentMatricule(String)}
     *//*
    @Test
    void testGetAllCvFileByStudentMatricule2() {
        assertTrue(managerService.getAllCvFileByStudentMatricule("cal.projeteq3.glucose.model.cv.CvFile").isEmpty());
        assertTrue(managerService.getAllGestionnaires().isEmpty());
    }

    *//**
     * Method under test: {@link ManagerService#deleteCvFile(Long)}
     *//*
    @Test
    void testDeleteCvFile() {
        doNothing().when(cvRepository).deleteById(Mockito.<Long>any());
        managerService.deleteCvFile(1L);
        verify(cvRepository).deleteById(Mockito.<Long>any());
    }

    *//**
     * Method under test: {@link ManagerService#deleteCvFile(Long)}
     *//*
    @Test
    void testDeleteCvFile2() {
        doThrow(new IllegalArgumentException("foo")).when(cvRepository).deleteById(Mockito.<Long>any());
        assertThrows(IllegalArgumentException.class, () -> managerService.deleteCvFile(1L));
        verify(cvRepository).deleteById(Mockito.<Long>any());
    }

    *//**
     * Method under test: {@link ManagerService#deleteCvFile(Long)}
     *//*
    @Test
    void testDeleteCvFile3() {
        managerService.deleteCvFile(1L);
        assertTrue(managerService.getAllCv().isEmpty());
        assertTrue(managerService.getAllGestionnaires().isEmpty());
    }

    *//**
     * Method under test: {@link ManagerService#deleteAllCvFileByStudendMatricule(String)}
     *//*
    @Test
    void testDeleteAllCvFileByStudendMatricule() {
        managerService.deleteAllCvFileByStudendMatricule("Matricule");
        List<CvFileDTO> allCv = managerService.getAllCv();
        assertTrue(allCv.isEmpty());
        assertSame(allCv, managerService.getPendingCv());
        assertTrue(managerService.getAllGestionnaires().isEmpty());
    }

    *//**
     * Method under test: {@link ManagerService#deleteAllCvFileByStudendMatricule(String)}
     *//*
    @Test
    void testDeleteAllCvFileByStudendMatricule2() {
        managerService.deleteAllCvFileByStudendMatricule("cal.projeteq3.glucose.model.cv.CvFile");
        assertTrue(managerService.getAllCv().isEmpty());
    }*/
}

