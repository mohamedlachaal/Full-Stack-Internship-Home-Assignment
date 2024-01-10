package ma.dnaengineering.backend.DAO.Repository;

import ma.dnaengineering.backend.DAO.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<Employe,Integer> {
}
