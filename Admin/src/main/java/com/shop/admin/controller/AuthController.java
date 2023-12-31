package com.shop.admin.controller;

import com.shop.library.dto.AdminDto;
import com.shop.library.model.Admin;
import com.shop.library.service.impl.AdminServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
   // private final AdminService adminService;
//
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AdminServiceImpl adminService;



    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    @RequestMapping("/index")
    public String home (Model model) {
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";

        }
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }
    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model
                             ) {
        try {
            if (result.hasErrors()){
                model.addAttribute("adminDto",adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin;
            admin = adminService.findByUsername(username);
            if (admin != null){
                model.addAttribute("adminDto",adminDto);
                System.out.println("không có tài khoản này");
                model.addAttribute("emailErrors","Email này đã được đăng ký");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("success","Đăng ký thành công");
                model.addAttribute("adminDto",adminDto);

            }else {
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("passwordErrors","mật khẩu không đúng");
                System.out.println("mật khẩu không khớp");
                return "redirect:/register";
            }


        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors","lỗi server, thử lại sau");
        }

        return "register";
    }


}
