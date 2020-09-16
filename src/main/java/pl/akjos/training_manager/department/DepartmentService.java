package pl.akjos.training_manager.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.akjos.training_manager.domain.model.Department;
import pl.akjos.training_manager.domain.repositories.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<DepartmentDTO> getDepartmentList() {
        List<Department> departmentList = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        for (Department department : departmentList) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setName(department.getName());
            departmentDTO.setId(department.getId());
            departmentDTOList.add(departmentDTO);
        }
        return departmentDTOList;
    }

    public void addDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        departmentRepository.save(department);
    }
}
