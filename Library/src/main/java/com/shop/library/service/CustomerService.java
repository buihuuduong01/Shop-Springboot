package com.shop.library.service;

import com.shop.library.dto.CustomerDto;
import com.shop.library.model.Customer;

public interface CustomerService {
    Customer save(CustomerDto customerDto);

    Customer findByUsername(String username);

    Customer update(CustomerDto customerDto);

    Customer changePass(CustomerDto customerDto);

    CustomerDto getCustomer(String username);
}
