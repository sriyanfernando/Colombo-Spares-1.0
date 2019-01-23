package com.javasampleapproach.mysql.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.javasampleapproach.mysql.model.Customer;


@Controller
public class BaseController {
		
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        Customer customer = new Customer();
        modelAndView.addObject("Customer ", customer);
        modelAndView.setViewName("registration");
        return modelAndView;
    }
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }
	
	@RequestMapping(value =  "/customer/home", method = RequestMethod.GET)
    public String welcome(Model model) {
        return "home";
    }
	
	@RequestMapping(value = {"/customer/home/autoproducts"}, method = RequestMethod.GET)
	public String autoProducts(Model model) {
		return "autoproducts";
	}
	@RequestMapping(value = {"/customer/home/companyprofile"}, method = RequestMethod.GET)
	public String companyProfile(Model model) {
		return "companyprofile";
	}
	
	@RequestMapping(value = {"/customer/home/sitemap"}, method = RequestMethod.GET)
	public String siteMap(Model model) {
		return "sitemap";
	}
	
	@RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
	public String logoutDo(HttpServletRequest request,HttpServletResponse response){
	HttpSession session= request.getSession(false);
	    SecurityContextHolder.clearContext();
	         session= request.getSession(false);
	        if(session != null) {
	            session.invalidate();
	        }
	        for(Cookie cookie : request.getCookies()) {
	            cookie.setMaxAge(0);
	        }

	    return "redirect:/login?logout";
	}
	
	@RequestMapping(value = {"/customer/settings"}, method = RequestMethod.GET)
	public String settings(Model model) {
		return "settings";
	}

	
	
	
	@RequestMapping(value = {"/customer/videoroom"}, method = RequestMethod.GET)
	public String videoRoom(Model model) {
		return "videoroomtest";
	}
	
	@RequestMapping(value = {"/customer/test"}, method = RequestMethod.GET)
	public String test(Model model) {
		return "webview";
	}
	
	
}
