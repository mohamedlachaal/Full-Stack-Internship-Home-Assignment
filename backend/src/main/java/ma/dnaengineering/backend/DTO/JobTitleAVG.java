package ma.dnaengineering.backend.DTO;

import lombok.Data;

@Data
public class JobTitleAVG {
    private String title;
    private Double avgSalary;

    public JobTitleAVG(String title, Double avgSalary) {
        this.title = title;
        this.avgSalary = avgSalary;
    }
}