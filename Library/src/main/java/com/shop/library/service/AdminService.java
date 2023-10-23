package com.shop.library.service;

import com.shop.library.dto.AdminDto;
import com.shop.library.model.Admin;

public interface AdminService {
    Admin save(AdminDto adminDto);

    Admin findByUsername(String username);
}
