package org.example.backend.service;

import org.example.backend.dto.CustomerDTO;
import org.example.backend.model.Customer;
import org.example.backend.model.User;
import org.example.backend.repository.CustomerRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer insertCustomer(CustomerDTO dto){
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString().substring(0, 10));
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("CUSTOMER");
        user.setProvider("LOCAL");
        user.setProviderId(null); // hoặc ""
        userRepository.save(user);

        Customer customer = new Customer();
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll() ;
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
    }

    public Customer updateCustomer(Long id, CustomerDTO dto, String userIdFromToken){
        Customer customer = getCustomerById(id);
        if (!customer.getUser().getId().equals(userIdFromToken)) {
            throw new AccessDeniedException("Bạn không có quyền sửa thông tin người khác");
        }
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
        userRepository.delete(customer.getUser());
    }
}
