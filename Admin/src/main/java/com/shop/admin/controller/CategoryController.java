package com.shop.admin.controller;

import com.shop.library.model.Category;
import com.shop.library.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title", "Category");
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    @PostMapping("/add-category")
    public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes){
        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Thêm thành công");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Trùng tên không thêm được");
        }
        catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Lỗi server");
        }
        return "redirect:/categories";

    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id){

        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes attributes){
        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success","Cập nhật thành công");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Trùng tên không cập nhật được");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Lỗi server");
        }
        return "redirect:/categories";
    }

//    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
//    public String delete(Long id, RedirectAttributes attributes){
//        try {
//            categoryService.deleteById(id);
//            attributes.addFlashAttribute("success", "Deleted successfully");
//        }catch (Exception e){
//            e.printStackTrace();
//            attributes.addFlashAttribute("failed", "Failed to deleted");
//        }
//        return "redirect:/categories";
//    }
@RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
public String delete(@RequestParam Long id, RedirectAttributes attributes) {
    try {
        categoryService.deleteById(id);
        attributes.addFlashAttribute("success", "Ẩn thành công");
    } catch (Exception e) {
        e.printStackTrace();
        attributes.addFlashAttribute("failed", "Ẩn không thành công");
    }
    return "redirect:/categories";
}


    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes attributes){
        try {
            categoryService.enabledById(id);
            attributes.addFlashAttribute("success", "Bật thành công");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Bật không thành công");
        }
        return "redirect:/categories";
    }


}
