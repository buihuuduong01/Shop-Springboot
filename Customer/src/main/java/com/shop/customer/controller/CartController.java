package com.shop.customer.controller;

import com.shop.library.dto.ProductDto;
import com.shop.library.model.Customer;
import com.shop.library.model.Product;
import com.shop.library.model.ShoppingCart;
import com.shop.library.service.CustomerService;
import com.shop.library.service.ProductService;
import com.shop.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@Controller
@RequiredArgsConstructor
public class CartController {
    //    @Autowired
//    private CustomerService customerService;
//    @Autowired
//    private ShoppingCartService cartService;
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping("/cart")
//    public String cart(Model model, Principal principal, HttpSession session) {
//        if (principal == null) {
//            return "redirect:/login";
//        }
//
//        Customer customer = customerService.findByUsername(principal.getName());
//        ShoppingCart cart = customer.getCart();
//
//        // Kiểm tra xem cart có null không trước khi gọi phương thức getTotalItems()
//        if (cart != null) {
//            model.addAttribute("grandTotal", cart.getTotalPrices());
//            model.addAttribute("totalItems", cart.getTotalItems());
//        } else {
//            model.addAttribute("check", "không có sản phẩm");
//        }
//
//        model.addAttribute("shoppingCart", cart);
//        model.addAttribute("title", "Cart");
//        return "cart";
//
//    }
//
//    @PostMapping("/add-to-cart")
//    public String addItemToCart(
//            @RequestParam("id") Long productId,
//            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
//            Principal principal,
//            HttpServletRequest request) {
//
//        if (principal == null) {
//            return "redirect:/login";
//        }
//        Product product = productService.getProductById(productId);
//        String username = principal.getName();
//        Customer customer = customerService.findByUsername(username);
//
//        ShoppingCart cart = cartService.addItemToCart(product, quantity, customer);
//        return "redirect:" + request.getHeader("Referer");
//    }
//
//    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
//    public String updateCart(@RequestParam("quantity") int quantity,
//                             @RequestParam("id") Long productId,
//                             Model model,
//                             Principal principal
//    ){
//
//        if(principal == null){
//            return "redirect:/login";
//        }else{
//            String username = principal.getName();
//            Customer customer = customerService.findByUsername(username);
//            Product product = productService.getProductById(productId);
//            ShoppingCart cart = cartService.updateCart(product, quantity, customer);
//
//            model.addAttribute("shoppingCart", cart);
//            return "redirect:/cart";
//        }
//
//    }
//
//
//    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
//    public String deleteItemFromCart(@RequestParam("id") Long productId,
//                                     Model model,
//                                     Principal principal){
//        if(principal == null){
//            return "redirect:/login";
//        }else{
//            String username = principal.getName();
//            Customer customer = customerService.findByUsername(username);
//            Product product = productService.getProductById(productId);
//            ShoppingCart cart = cartService.deleteItemFromCart(product, customer);
//            model.addAttribute("shoppingCart", cart);
//            return "redirect:/cart";
//        }
//
//    }
    private final ShoppingCartService cartService;
    private final ProductService productService;
    private final CustomerService customerService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart cart = customer.getCart();
        if (cart == null) {
            model.addAttribute("check");

        }
        if (cart != null) {
            model.addAttribute("grandTotal", cart.getTotalPrice());
        }
        model.addAttribute("shoppingCart", cart);
        model.addAttribute("title", "Cart");
        session.setAttribute("totalItems", cart.getTotalItems());
        return "cart";





}

    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                HttpServletRequest request,
                                Model model,
                                Principal principal,
                                HttpSession session) {


        ProductDto productDto = productService.getById(id);
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("shoppingCart", shoppingCart);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Long id,
                             @RequestParam("quantity") int quantity,
                             Model model,
                             Principal principal,
                             HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        ProductDto productDto = productService.getById(id);
        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.updateCart(productDto, quantity, username);
        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:/cart";

    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Long id,
                             Model model,
                             Principal principal,
                             HttpSession session
    ) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            ProductDto productDto = productService.getById(id);
            String username = principal.getName();
            ShoppingCart shoppingCart = cartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            return "redirect:/cart";
        }
    }
}