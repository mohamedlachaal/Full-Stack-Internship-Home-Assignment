package ma.dnaengineering.backend.Controller;

import ma.dnaengineering.backend.DTO.EmployeDTO;
import ma.dnaengineering.backend.DTO.JobTitleAVG;
import ma.dnaengineering.backend.Service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @GetMapping("/parseEmployees")
    public List<EmployeDTO> parseEmployees(@RequestParam("file") String filePath) throws IOException {
        return employeService.processEmployeeFile(filePath);
    }

    @GetMapping("/jobAndSalaryAvg")
    public List<JobTitleAVG> jobAndSalaryAvg() {
        return employeService.calculateAverageSalaries();
    }
}


