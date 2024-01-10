package ma.dnaengineering.backend.Service;


import ma.dnaengineering.backend.DAO.Repository.EmployeRepository;
import ma.dnaengineering.backend.DAO.model.Employe;
import ma.dnaengineering.backend.DTO.EmployeDTO;
import ma.dnaengineering.backend.DTO.JobTitleAVG;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;


    public EmployeDTO mapToDto(Employe employe) {
        EmployeDTO employeDTO = new EmployeDTO();
        employeDTO.setId(employe.getId());
        employeDTO.setName(employe.getName());
        employeDTO.setJobTitle(employe.getJobTitle());
        employeDTO.setSalary(employe.getSalary());
        return employeDTO;
    }

    public List<EmployeDTO> processEmployeeFile(String csvFilePath) throws IOException {
        Reader reader = new FileReader(csvFilePath);
        List<Employe> employees = new ArrayList<>();
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("id", "name", "jobTitle", "salary").withSkipHeaderRecord());
        for (CSVRecord record : csvParser) {
            Employe employee = new Employe();
            employee.setId(record.get("id"));
            employee.setName(record.get("name"));
            employee.setJobTitle(record.get("jobTitle"));
            employee.setSalary(record.get("salary"));
            employees.add(employee);
        }
        employeRepository.saveAll(employees);

        return employees.stream().map(this::mapToDto).collect(Collectors.toList());

    }

    public List<JobTitleAVG> calculateAverageSalaries(List<EmployeDTO> employees) {
        Map<String, Double> averageSalaries = employees.stream()
                .collect(Collectors.groupingBy(
                        EmployeDTO::getJobTitle,
                        Collectors.averagingDouble(emp -> Double.parseDouble(emp.getSalary()))
                ));
        return averageSalaries.entrySet().stream()
                .map(entry -> new JobTitleAVG(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }



    public List<JobTitleAVG> calculateAverageSalaries() {
        List<Employe> employees = employeRepository.findAll();

    /*    Map<String, Double> averageSalaries = employees.stream()
                .collect(Collectors.groupingBy(
                        Employe::getJobTitle,
                        Collectors.averagingDouble(emp -> Double.parseDouble(emp.getSalary()))
                ));
        return averageSalaries.entrySet().stream()
                .map(entry -> new JobTitleAVG(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    */
        Map<String, Double> averageSalaries = employees.stream()
                .filter(emp -> emp.getJobTitle() != null && emp.getSalary() != null) // Filtre les employés avec jobTitle et salary non null
                .collect(Collectors.groupingBy(
                        Employe::getJobTitle,
                        Collectors.averagingDouble(emp -> {
                            try {
                                return Double.parseDouble(emp.getSalary());
                            } catch (NumberFormatException e) {
                                // Gérer les cas où le salaire n'est pas un nombre valide
                                return 0.0; // Par exemple, retourner 0.0
                            }
                        })
                ));

        return averageSalaries.entrySet().stream()
                .map(entry -> new JobTitleAVG(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}