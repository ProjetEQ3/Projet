package cal.projeteq3.glucose.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.ManagerDTO;
import cal.projeteq3.glucose.model.CvFile;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Manager;
import cal.projeteq3.glucose.model.State;
import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.repository.CvRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ManagerService.class})
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
    @Test
    void testCreateGestionnaire() {
        Manager manager = new Manager();
        manager.setEmail("jane.doe@example.org");
        manager.setFirstName("Jane");
        manager.setLastName("Doe");
        manager.setMatricule("Matricule");
        manager.setPassword("iloveyou");
        manager.setPhoneNumber("6625550144");
        manager.setUserID(1L);
        when(managerRepository.save(Mockito.<Manager>any())).thenReturn(manager);

        Manager manager2 = new Manager();
        manager2.setEmail("jane.doe@example.org");
        manager2.setFirstName("Jane");
        manager2.setLastName("Doe");
        manager2.setMatricule("Matricule");
        manager2.setPassword("iloveyou");
        manager2.setPhoneNumber("6625550144");
        manager2.setUserID(1L);
        ManagerDTO actualCreateGestionnaireResult = managerService.createGestionnaire(manager2);
        assertEquals("jane.doe@example.org", actualCreateGestionnaireResult.getEmail());
        assertEquals("6625550144", actualCreateGestionnaireResult.getPhoneNumber());
        assertEquals("Matricule", actualCreateGestionnaireResult.getMatricule());
        assertEquals("Doe", actualCreateGestionnaireResult.getLastName());
        assertEquals(1L, actualCreateGestionnaireResult.getId().longValue());
        assertEquals("Jane", actualCreateGestionnaireResult.getFirstName());
        verify(managerRepository).save(Mockito.<Manager>any());
    }

    /**
     * Method under test: {@link ManagerService#createGestionnaire(Manager)}
     */
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
        manager.setUserID(1L);
        assertThrows(IllegalArgumentException.class, () -> managerService.createGestionnaire(manager));
        verify(managerRepository).save(Mockito.<Manager>any());
    }

    /**
     * Method under test: {@link ManagerService#deleteGestionnaire(Long)}
     */
    @Test
    void testDeleteGestionnaire() {
        doNothing().when(managerRepository).deleteById(Mockito.<Long>any());
        managerService.deleteGestionnaire(1L);
        verify(managerRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ManagerService#deleteGestionnaire(Long)}
     */
    @Test
    void testDeleteGestionnaire2() {
        doThrow(new IllegalArgumentException("foo")).when(managerRepository).deleteById(Mockito.<Long>any());
        assertThrows(IllegalArgumentException.class, () -> managerService.deleteGestionnaire(1L));
        verify(managerRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ManagerService#getAllCv()}
     */
    @Test
    void testGetAllCv() {
        when(cvRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(managerService.getAllCv().isEmpty());
        verify(cvRepository).findAll();
    }

    /**
     * Method under test: {@link ManagerService#getAllCv()}
     */
    @Test
    void testGetAllCv2() throws UnsupportedEncodingException {
        Student student = new Student();
        student.setCv(new CvFile());
        student.setDepartment(Department._410B0);
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setMatricule("Matricule");
        student.setPassword("iloveyou");
        student.setUserID(1L);

        CvFile cv = new CvFile();
        cv.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv.setFileName("foo.txt");
        cv.setId(1L);
        cv.setState(State.SUBMITTED);
        cv.setStudent(student);

        Student student2 = new Student();
        student2.setCv(cv);
        student2.setDepartment(Department._410B0);
        student2.setEmail("jane.doe@example.org");
        student2.setFirstName("Jane");
        student2.setLastName("Doe");
        student2.setMatricule("Matricule");
        student2.setPassword("iloveyou");
        student2.setUserID(1L);

        CvFile cvFile = new CvFile();
        cvFile.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cvFile.setFileName("foo.txt");
        cvFile.setId(1L);
        cvFile.setState(State.SUBMITTED);
        cvFile.setStudent(student2);

        ArrayList<CvFile> cvFileList = new ArrayList<>();
        cvFileList.add(cvFile);
        when(cvRepository.findAll()).thenReturn(cvFileList);
        List<CvFileDTO> actualAllCv = managerService.getAllCv();
        assertEquals(1, actualAllCv.size());
        CvFileDTO getResult = actualAllCv.get(0);
        assertEquals(8, getResult.getFileData().length);
        assertEquals(1L, getResult.getId().longValue());
        assertEquals("foo.txt", getResult.getFileName());
        verify(cvRepository).findAll();
    }

    /**
     * Method under test: {@link ManagerService#getAllCv()}
     */
    @Test
    void testGetAllCv3() throws UnsupportedEncodingException {
        Student student = new Student();
        student.setCv(new CvFile());
        student.setDepartment(Department._410B0);
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setMatricule("Matricule");
        student.setPassword("iloveyou");
        student.setUserID(1L);

        CvFile cv = new CvFile();
        cv.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv.setFileName("foo.txt");
        cv.setId(1L);
        cv.setState(State.SUBMITTED);
        cv.setStudent(student);

        Student student2 = new Student();
        student2.setCv(cv);
        student2.setDepartment(Department._410B0);
        student2.setEmail("jane.doe@example.org");
        student2.setFirstName("Jane");
        student2.setLastName("Doe");
        student2.setMatricule("Matricule");
        student2.setPassword("iloveyou");
        student2.setUserID(1L);

        CvFile cvFile = new CvFile();
        cvFile.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cvFile.setFileName("foo.txt");
        cvFile.setId(1L);
        cvFile.setState(State.SUBMITTED);
        cvFile.setStudent(student2);

        Student student3 = new Student();
        student3.setCv(new CvFile());
        student3.setDepartment(Department._241A1);
        student3.setEmail("john.smith@example.org");
        student3.setFirstName("John");
        student3.setLastName("Smith");
        student3.setMatricule("cal.projeteq3.glucose.model.Student");
        student3.setPassword("Password");
        student3.setUserID(2L);

        CvFile cv2 = new CvFile();
        cv2.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv2.setFileName("File Name");
        cv2.setId(2L);
        cv2.setState(State.PENDING);
        cv2.setStudent(student3);

        Student student4 = new Student();
        student4.setCv(cv2);
        student4.setDepartment(Department._241A1);
        student4.setEmail("john.smith@example.org");
        student4.setFirstName("John");
        student4.setLastName("Smith");
        student4.setMatricule("cal.projeteq3.glucose.model.Student");
        student4.setPassword("Password");
        student4.setUserID(2L);

        CvFile cvFile2 = new CvFile();
        cvFile2.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cvFile2.setFileName("File Name");
        cvFile2.setId(2L);
        cvFile2.setState(State.PENDING);
        cvFile2.setStudent(student4);

        ArrayList<CvFile> cvFileList = new ArrayList<>();
        cvFileList.add(cvFile2);
        cvFileList.add(cvFile);
        when(cvRepository.findAll()).thenReturn(cvFileList);
        List<CvFileDTO> actualAllCv = managerService.getAllCv();
        assertEquals(2, actualAllCv.size());
        CvFileDTO getResult = actualAllCv.get(0);
        assertEquals(2L, getResult.getId().longValue());
        CvFileDTO getResult2 = actualAllCv.get(1);
        assertEquals(1L, getResult2.getId().longValue());
        assertEquals("foo.txt", getResult2.getFileName());
        assertEquals(8, getResult2.getFileData().length);
        assertEquals("File Name", getResult.getFileName());
        assertEquals(8, getResult.getFileData().length);
        verify(cvRepository).findAll();
    }

    /**
     * Method under test: {@link ManagerService#getAllCv()}
     */
    @Test
    void testGetAllCv4() {
        when(cvRepository.findAll()).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> managerService.getAllCv());
        verify(cvRepository).findAll();
    }

    /**
     * Method under test: {@link ManagerService#createCvFile(CvFile)}
     */
    @Test
    void testCreateCvFile() throws UnsupportedEncodingException {
        CvFile cv = new CvFile();
        cv.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv.setFileName("foo.txt");
        cv.setId(1L);
        cv.setState(State.SUBMITTED);
        cv.setStudent(new Student());

        Student student = new Student();
        student.setCv(cv);
        student.setDepartment(Department._410B0);
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setMatricule("Matricule");
        student.setPassword("iloveyou");
        student.setUserID(1L);

        CvFile cv2 = new CvFile();
        cv2.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv2.setFileName("foo.txt");
        cv2.setId(1L);
        cv2.setState(State.SUBMITTED);
        cv2.setStudent(student);

        Student student2 = new Student();
        student2.setCv(cv2);
        student2.setDepartment(Department._410B0);
        student2.setEmail("jane.doe@example.org");
        student2.setFirstName("Jane");
        student2.setLastName("Doe");
        student2.setMatricule("Matricule");
        student2.setPassword("iloveyou");
        student2.setUserID(1L);

        CvFile cvFile = new CvFile();
        cvFile.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cvFile.setFileName("foo.txt");
        cvFile.setId(1L);
        cvFile.setState(State.SUBMITTED);
        cvFile.setStudent(student2);
        when(cvRepository.save(Mockito.<CvFile>any())).thenReturn(cvFile);

        Student student3 = new Student();
        student3.setCv(new CvFile());
        student3.setDepartment(Department._410B0);
        student3.setEmail("jane.doe@example.org");
        student3.setFirstName("Jane");
        student3.setLastName("Doe");
        student3.setMatricule("Matricule");
        student3.setPassword("iloveyou");
        student3.setUserID(1L);

        CvFile cv3 = new CvFile();
        cv3.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv3.setFileName("foo.txt");
        cv3.setId(1L);
        cv3.setState(State.SUBMITTED);
        cv3.setStudent(student3);

        Student student4 = new Student();
        student4.setCv(cv3);
        student4.setDepartment(Department._410B0);
        student4.setEmail("jane.doe@example.org");
        student4.setFirstName("Jane");
        student4.setLastName("Doe");
        student4.setMatricule("Matricule");
        student4.setPassword("iloveyou");
        student4.setUserID(1L);

        CvFile cvFile2 = new CvFile();
        cvFile2.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cvFile2.setFileName("foo.txt");
        cvFile2.setId(1L);
        cvFile2.setState(State.SUBMITTED);
        cvFile2.setStudent(student4);
        CvFileDTO actualCreateCvFileResult = managerService.createCvFile(cvFile2);
        assertEquals(8, actualCreateCvFileResult.getFileData().length);
        assertEquals(1L, actualCreateCvFileResult.getId().longValue());
        assertEquals("foo.txt", actualCreateCvFileResult.getFileName());
        verify(cvRepository).save(Mockito.<CvFile>any());
    }

    /**
     * Method under test: {@link ManagerService#createCvFile(String, byte[], Student)}
     */
    @Test
    void testCreateCvFile2() throws UnsupportedEncodingException {
        CvFile cv = new CvFile();
        cv.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv.setFileName("foo.txt");
        cv.setId(1L);
        cv.setState(State.SUBMITTED);
        cv.setStudent(new Student());

        Student student = new Student();
        student.setCv(cv);
        student.setDepartment(Department._410B0);
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setMatricule("Matricule");
        student.setPassword("iloveyou");
        student.setUserID(1L);

        CvFile cv2 = new CvFile();
        cv2.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv2.setFileName("foo.txt");
        cv2.setId(1L);
        cv2.setState(State.SUBMITTED);
        cv2.setStudent(student);

        Student student2 = new Student();
        student2.setCv(cv2);
        student2.setDepartment(Department._410B0);
        student2.setEmail("jane.doe@example.org");
        student2.setFirstName("Jane");
        student2.setLastName("Doe");
        student2.setMatricule("Matricule");
        student2.setPassword("iloveyou");
        student2.setUserID(1L);

        CvFile cvFile = new CvFile();
        cvFile.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cvFile.setFileName("foo.txt");
        cvFile.setId(1L);
        cvFile.setState(State.SUBMITTED);
        cvFile.setStudent(student2);
        when(cvRepository.save(Mockito.<CvFile>any())).thenReturn(cvFile);
        byte[] fileData = "AXAXAXAX".getBytes("UTF-8");

        CvFile cv3 = new CvFile();
        cv3.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv3.setFileName("foo.txt");
        cv3.setId(1L);
        cv3.setState(State.SUBMITTED);
        cv3.setStudent(new Student());

        Student student3 = new Student();
        student3.setCv(cv3);
        student3.setDepartment(Department._410B0);
        student3.setEmail("jane.doe@example.org");
        student3.setFirstName("Jane");
        student3.setLastName("Doe");
        student3.setMatricule("Matricule");
        student3.setPassword("iloveyou");
        student3.setUserID(1L);

        CvFile cv4 = new CvFile();
        cv4.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv4.setFileName("foo.txt");
        cv4.setId(1L);
        cv4.setState(State.SUBMITTED);
        cv4.setStudent(student3);

        Student student4 = new Student();
        student4.setCv(cv4);
        student4.setDepartment(Department._410B0);
        student4.setEmail("jane.doe@example.org");
        student4.setFirstName("Jane");
        student4.setLastName("Doe");
        student4.setMatricule("Matricule");
        student4.setPassword("iloveyou");
        student4.setUserID(1L);
        CvFileDTO actualCreateCvFileResult = managerService.createCvFile("foo.txt", fileData, student4);
        assertEquals(8, actualCreateCvFileResult.getFileData().length);
        assertEquals(1L, actualCreateCvFileResult.getId().longValue());
        assertEquals("foo.txt", actualCreateCvFileResult.getFileName());
        verify(cvRepository).save(Mockito.<CvFile>any());
    }

    /**
     * Method under test: {@link ManagerService#createCvFile(String, byte[], String)}
     */
    @Test
    void testCreateCvFile3() throws UnsupportedEncodingException {
        Student student = new Student();
        student.setCv(new CvFile());
        student.setDepartment(Department._410B0);
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setMatricule("Matricule");
        student.setPassword("iloveyou");
        student.setUserID(1L);

        CvFile cv = new CvFile();
        cv.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv.setFileName("foo.txt");
        cv.setId(1L);
        cv.setState(State.SUBMITTED);
        cv.setStudent(student);

        Student student2 = new Student();
        student2.setCv(cv);
        student2.setDepartment(Department._410B0);
        student2.setEmail("jane.doe@example.org");
        student2.setFirstName("Jane");
        student2.setLastName("Doe");
        student2.setMatricule("Matricule");
        student2.setPassword("iloveyou");
        student2.setUserID(1L);

        CvFile cv2 = new CvFile();
        cv2.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv2.setFileName("foo.txt");
        cv2.setId(1L);
        cv2.setState(State.SUBMITTED);
        cv2.setStudent(student2);

        Student student3 = new Student();
        student3.setCv(cv2);
        student3.setDepartment(Department._410B0);
        student3.setEmail("jane.doe@example.org");
        student3.setFirstName("Jane");
        student3.setLastName("Doe");
        student3.setMatricule("Matricule");
        student3.setPassword("iloveyou");
        student3.setUserID(1L);
        when(studentRepository.findByMatricule(Mockito.<String>any())).thenReturn(student3);

        CvFile cv3 = new CvFile();
        cv3.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv3.setFileName("foo.txt");
        cv3.setId(1L);
        cv3.setState(State.SUBMITTED);
        cv3.setStudent(new Student());

        Student student4 = new Student();
        student4.setCv(cv3);
        student4.setDepartment(Department._410B0);
        student4.setEmail("jane.doe@example.org");
        student4.setFirstName("Jane");
        student4.setLastName("Doe");
        student4.setMatricule("Matricule");
        student4.setPassword("iloveyou");
        student4.setUserID(1L);

        CvFile cv4 = new CvFile();
        cv4.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv4.setFileName("foo.txt");
        cv4.setId(1L);
        cv4.setState(State.SUBMITTED);
        cv4.setStudent(student4);

        Student student5 = new Student();
        student5.setCv(cv4);
        student5.setDepartment(Department._410B0);
        student5.setEmail("jane.doe@example.org");
        student5.setFirstName("Jane");
        student5.setLastName("Doe");
        student5.setMatricule("Matricule");
        student5.setPassword("iloveyou");
        student5.setUserID(1L);

        CvFile cvFile = new CvFile();
        cvFile.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cvFile.setFileName("foo.txt");
        cvFile.setId(1L);
        cvFile.setState(State.SUBMITTED);
        cvFile.setStudent(student5);
        when(cvRepository.save(Mockito.<CvFile>any())).thenReturn(cvFile);
        CvFileDTO actualCreateCvFileResult = managerService.createCvFile("foo.txt", "AXAXAXAX".getBytes("UTF-8"),
                "Matricule");
        assertEquals(8, actualCreateCvFileResult.getFileData().length);
        assertEquals(1L, actualCreateCvFileResult.getId().longValue());
        assertEquals("foo.txt", actualCreateCvFileResult.getFileName());
        verify(studentRepository).findByMatricule(Mockito.<String>any());
        verify(cvRepository).save(Mockito.<CvFile>any());
    }

    /**
     * Method under test: {@link ManagerService#createCvFile(String, byte[], String)}
     */
    @Test
    void testCreateCvFile4() throws UnsupportedEncodingException {
        Student student = new Student();
        student.setCv(new CvFile());
        student.setDepartment(Department._410B0);
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setMatricule("Matricule");
        student.setPassword("iloveyou");
        student.setUserID(1L);

        CvFile cv = new CvFile();
        cv.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv.setFileName("foo.txt");
        cv.setId(1L);
        cv.setState(State.SUBMITTED);
        cv.setStudent(student);

        Student student2 = new Student();
        student2.setCv(cv);
        student2.setDepartment(Department._410B0);
        student2.setEmail("jane.doe@example.org");
        student2.setFirstName("Jane");
        student2.setLastName("Doe");
        student2.setMatricule("Matricule");
        student2.setPassword("iloveyou");
        student2.setUserID(1L);

        CvFile cv2 = new CvFile();
        cv2.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv2.setFileName("foo.txt");
        cv2.setId(1L);
        cv2.setState(State.SUBMITTED);
        cv2.setStudent(student2);

        Student student3 = new Student();
        student3.setCv(cv2);
        student3.setDepartment(Department._410B0);
        student3.setEmail("jane.doe@example.org");
        student3.setFirstName("Jane");
        student3.setLastName("Doe");
        student3.setMatricule("Matricule");
        student3.setPassword("iloveyou");
        student3.setUserID(1L);
        when(studentRepository.findByMatricule(Mockito.<String>any())).thenReturn(student3);
        when(cvRepository.save(Mockito.<CvFile>any())).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class,
                () -> managerService.createCvFile("foo.txt", "AXAXAXAX".getBytes("UTF-8"), "Matricule"));
        verify(studentRepository).findByMatricule(Mockito.<String>any());
        verify(cvRepository).save(Mockito.<CvFile>any());
    }

    /**
     * Method under test: {@link ManagerService#deleteCvFile(Long)}
     */
    @Test
    void testDeleteCvFile() {
        doNothing().when(cvRepository).deleteById(Mockito.<Long>any());
        managerService.deleteCvFile(1L);
        verify(cvRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ManagerService#deleteCvFile(Long)}
     */
    @Test
    void testDeleteCvFile2() {
        doThrow(new IllegalArgumentException("foo")).when(cvRepository).deleteById(Mockito.<Long>any());
        assertThrows(IllegalArgumentException.class, () -> managerService.deleteCvFile(1L));
        verify(cvRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ManagerService#deleteAllCvFileByStudendMatricule(String)}
     */
    @Test
    void testDeleteAllCvFileByStudendMatricule() throws UnsupportedEncodingException {
        Student student = new Student();
        student.setCv(new CvFile());
        student.setDepartment(Department._410B0);
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setMatricule("Matricule");
        student.setPassword("iloveyou");
        student.setUserID(1L);

        CvFile cv = new CvFile();
        cv.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv.setFileName("foo.txt");
        cv.setId(1L);
        cv.setState(State.SUBMITTED);
        cv.setStudent(student);

        Student student2 = new Student();
        student2.setCv(cv);
        student2.setDepartment(Department._410B0);
        student2.setEmail("jane.doe@example.org");
        student2.setFirstName("Jane");
        student2.setLastName("Doe");
        student2.setMatricule("Matricule");
        student2.setPassword("iloveyou");
        student2.setUserID(1L);

        CvFile cv2 = new CvFile();
        cv2.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv2.setFileName("foo.txt");
        cv2.setId(1L);
        cv2.setState(State.SUBMITTED);
        cv2.setStudent(student2);

        Student student3 = new Student();
        student3.setCv(cv2);
        student3.setDepartment(Department._410B0);
        student3.setEmail("jane.doe@example.org");
        student3.setFirstName("Jane");
        student3.setLastName("Doe");
        student3.setMatricule("Matricule");
        student3.setPassword("iloveyou");
        student3.setUserID(1L);
        when(studentRepository.findByMatricule(Mockito.<String>any())).thenReturn(student3);
        doNothing().when(cvRepository).deleteAllByStudent(Mockito.<Student>any());
        managerService.deleteAllCvFileByStudendMatricule("Matricule");
        verify(studentRepository).findByMatricule(Mockito.<String>any());
        verify(cvRepository).deleteAllByStudent(Mockito.<Student>any());
    }

    /**
     * Method under test: {@link ManagerService#deleteAllCvFileByStudendMatricule(String)}
     */
    @Test
    void testDeleteAllCvFileByStudendMatricule2() throws UnsupportedEncodingException {
        Student student = new Student();
        student.setCv(new CvFile());
        student.setDepartment(Department._410B0);
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setMatricule("Matricule");
        student.setPassword("iloveyou");
        student.setUserID(1L);

        CvFile cv = new CvFile();
        cv.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv.setFileName("foo.txt");
        cv.setId(1L);
        cv.setState(State.SUBMITTED);
        cv.setStudent(student);

        Student student2 = new Student();
        student2.setCv(cv);
        student2.setDepartment(Department._410B0);
        student2.setEmail("jane.doe@example.org");
        student2.setFirstName("Jane");
        student2.setLastName("Doe");
        student2.setMatricule("Matricule");
        student2.setPassword("iloveyou");
        student2.setUserID(1L);

        CvFile cv2 = new CvFile();
        cv2.setFileData("AXAXAXAX".getBytes("UTF-8"));
        cv2.setFileName("foo.txt");
        cv2.setId(1L);
        cv2.setState(State.SUBMITTED);
        cv2.setStudent(student2);

        Student student3 = new Student();
        student3.setCv(cv2);
        student3.setDepartment(Department._410B0);
        student3.setEmail("jane.doe@example.org");
        student3.setFirstName("Jane");
        student3.setLastName("Doe");
        student3.setMatricule("Matricule");
        student3.setPassword("iloveyou");
        student3.setUserID(1L);
        when(studentRepository.findByMatricule(Mockito.<String>any())).thenReturn(student3);
        doThrow(new IllegalArgumentException("foo")).when(cvRepository).deleteAllByStudent(Mockito.<Student>any());
        assertThrows(IllegalArgumentException.class, () -> managerService.deleteAllCvFileByStudendMatricule("Matricule"));
        verify(studentRepository).findByMatricule(Mockito.<String>any());
        verify(cvRepository).deleteAllByStudent(Mockito.<Student>any());
    }
}

