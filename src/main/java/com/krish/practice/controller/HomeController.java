package com.krish.practice.controller;

import com.krish.practice.bean.UserDto;
import com.krish.practice.entity.Role;
import com.krish.practice.entity.User;
import com.krish.practice.reposiroty.RoleRepository;
import com.krish.practice.reposiroty.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/viewUsers")
    public String viewUsers(Model model) {
        log.info("loading home page:::::");
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user -> UserDto.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).build()).collect(Collectors.toList());
        model.addAttribute("users", userDtos);
        return "view-users";
    }

    @GetMapping("/addUser")
    public String loadAddUserPage(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, Model model) {
        userRepository.findByEmail(user.getEmail()).ifPresent(user1 -> new Exception(user1.getEmail()+" is already exist."));
        List<Role> roles = roleRepository.findAllByName("ROLE_USER");
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:viewUsers";
    }
}
