package org.example.backend.service;

import org.example.backend.dto.StaffDTO;
import org.example.backend.model.Staff;
import org.example.backend.model.User;
import org.example.backend.repository.StaffRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StaffRepository staffRepository;

    public Staff createStaff(StaffDTO dto){
        User user = new User();
        user.setUsername(dto.getEmail());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole("STAFF");
        userRepository.save(user);

        Staff staff = new Staff();
        staff.setFullName(dto.getFullName());
        staff.setPhone(dto.getPhone());
        staff.setEmail(dto.getEmail());
        staff.setPosition(dto.getPosition());
        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }

    public Staff getStaff(Long id){
        return staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
    }

    public Staff updateStaff(Long id, StaffDTO dto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
        staff.setFullName(dto.getFullName());
        staff.setPhone(dto.getPhone());
        staff.setEmail(dto.getEmail());
        staff.setPosition(dto.getPosition());
        return staffRepository.save(staff);
    }

    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
