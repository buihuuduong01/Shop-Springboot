package com.shop.customer.controller;

import com.shop.library.dto.ProductDto;
import com.shop.library.model.Category;
import com.shop.library.model.Customer;
import com.shop.library.model.Product;
import com.shop.library.model.ShoppingCart;
import com.shop.library.service.CategoryService;
import com.shop.library.service.CustomerService;
import com.shop.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;


    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String index(Model model, Principal principal, HttpSession session) {
        if (principal != null) {
            session.setAttribute("username", principal.getName());
            Customer customer = customerService.findByUsername(principal.getName());
            if (customer != null && customer.getCart() != null) {
                ShoppingCart cart = customer.getCart();
                session.setAttribute("totalItems", cart.getTotalItems());
            } else {
                session.setAttribute("totalItems", 0); // hoặc giá trị mặc định tương ứng
            }
        } else {
            session.removeAttribute("username");
            session.setAttribute("totalItems", 0); // hoặc giá trị mặc định tương ứng
        }
        List<Category> categories = categoryService.findAll();
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtos);
        return "index";
    }

}
