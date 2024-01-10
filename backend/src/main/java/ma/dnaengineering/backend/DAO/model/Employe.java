package ma.dnaengineering.backend.DAO.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employe {
    @Id
    private String id;
    private String name;
    private String jobTitle;
    private String salary;

    public Employe() {
    }

    public Employe(String s, String john, String manager, String s1) {
    }
}

