package ma.dnaengineering.backend;


import ma.dnaengineering.backend.DAO.Repository.EmployeRepository;
import ma.dnaengineering.backend.DAO.model.Employe;
import ma.dnaengineering.backend.DTO.EmployeDTO;
import ma.dnaengineering.backend.DTO.JobTitleAVG;
import ma.dnaengineering.backend.Service.EmployeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BackendApplicationTests {

	private EmployeService employeService;

	@Mock
	private EmployeRepository employeRepository;

	@Test
	public void testProcessEmployeeFile() throws IOException {
		List<Employe> employees = new ArrayList<>();
		Employe employe1 = new Employe("1", "John Doe", "Software Engineer", "70000");
		Employe employe2 = new Employe("2", "Jane Doe", "Project Manager", "90000");
		employees.add(employe1);
		employees.add(employe2);

		when(employeRepository.findAll()).thenReturn(employees);

		List<JobTitleAVG> jobTitleAVGs = employeService.calculateAverageSalaries();

		assertEquals(2, jobTitleAVGs.size());
		assertEquals("Software Engineer", jobTitleAVGs.get(0).getTitle());
		assertEquals(70000, jobTitleAVGs.get(0).getAvgSalary());
		assertEquals("Project Manager", jobTitleAVGs.get(1).getTitle());
		assertEquals(90000, jobTitleAVGs.get(1).getAvgSalary());
	}
}



