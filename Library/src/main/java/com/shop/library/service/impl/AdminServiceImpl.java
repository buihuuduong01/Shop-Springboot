package com.shop.library.service.impl;

import com.shop.library.dto.AdminDto;
import com.shop.library.model.Admin;
import com.shop.library.repository.AdminRepository;
import com.shop.library.repository.RoleRepository;
import com.shop.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
//chú thích từ thư viện Lombok.
// Nó được sử dụng để tạo một constructor chứa tất cả các trường (fields) được đánh dấu bởi final.
public class AdminServiceImpl implements AdminService {
    //Đây là lớp dịch vụ (Service) cụ thể. Nó triển khai giao diện AdminService.
    // sử dụng để thao tác với cơ sở dữ liệu cho thực thể Admin.
    private final AdminRepository adminRepository;
    //thao tác với cơ sở dữ liệu cho thực thể Role.
    private final RoleRepository roleRepository;


    @Override
    //để tạo và lưu thông tin người dùng.
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    // tìm kiếm một người dùng cụ thể bằng tên người dùng của họ.
    public Admin findByUsername(String username) {

        return adminRepository.findByUsername(username);
    }

}
