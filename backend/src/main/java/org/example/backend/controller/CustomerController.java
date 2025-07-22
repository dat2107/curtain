package org.example.backend.controller;


import org.example.backend.dto.CustomerDTO;
import org.example.backend.model.Customer;
import org.example.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> insertCustomer(@RequestBody CustomerDTO dto){
        try {
            Customer created = customerService.insertCustomer(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi tạo khách hàng: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomer(){
        try {
            List<Customer> customers = customerService.getAllCustomer();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể lấy danh sách khách hàng");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        try {
            Customer customer = customerService.getCustomerById(id);
            if (customer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khách hàng với ID: " + id);
            }
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi truy vấn khách hàng");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == principal.id or hasRole('CUSTOMER')")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO dto, String userIdFromToken){
        return ResponseEntity.ok(customerService.updateCustomer(id,dto,userIdFromToken));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Đã xóa thông tin khách hàng");
    }
}
