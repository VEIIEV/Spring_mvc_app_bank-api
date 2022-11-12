package com.example.bank_api.controller;


import com.example.bank_api.entities.User;
import com.example.bank_api.services.BalanceOperation;
import com.example.bank_api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/")
@AllArgsConstructor
public class MainController {

    BalanceOperation balanceOperation;

    UserService userService;

    @GetMapping({"/greeting", "/"})
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

///////////////////////////////////////////////////////////////////////////////////////
    //получение баланса любого юзера через форму
    @GetMapping("/getBalance")
    public String getBalance(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "/userBalance";
    }

    //получение баланса текущего юзера через PathVariable
    @GetMapping("/getBalance/{id}")
    public String getCurrentUserBalance(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "/userBalance";
    }

    /////////////////////////////////////////////////////////////////////////////////////
    //работаем с формой
    @PostMapping("/takeMoney")
    public String takeMoney(@RequestParam("id") Long id, @RequestParam("value") double value, Model model) {
        try {
            balanceOperation.takeMoney(id, value);
        } catch (Exception e) {
            model.addAttribute("message", "insufficient fonds");
        }
        model.addAttribute("user", userService.getById(id));

        return "/userBalance";
    }


    //открываем форму
    @GetMapping("/takeMoneyForm")
    public String takeMoney(){
        return "/takeMoneyForm";
    }



    ///////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/putMoney")
    public String putMoney(@RequestParam("id") Long id, @RequestParam("value") double value, Model model) {
        try {
            balanceOperation.putMoney(id, value);
        } catch (Exception e) {
            model.addAttribute("message", "idk");
        }
        model.addAttribute("user", userService.getById(id));

        return "/userBalance";
    }

    //открываем форму
    @GetMapping("/putMoneyForm")
    public String putMoney(){
        return "/putMoneyForm";
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/transferMoney")
    public String transferMoney(@RequestParam("senderId") Long id1, @RequestParam("recipientId") Long id2
            , @RequestParam("value") double value, Model model) {
        balanceOperation.transferMoney(id1, id2, value);
        model.addAttribute("sender", userService.getById(id1));
        model.addAttribute("recipient", userService.getById(id2));

        return "/transferOperationResult";
    }


    //открываем форму
    @GetMapping("/transferMoneyForm")
    public String transferMoney(){
        return "/transferMoneyForm";
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/showAllUsers/createUser")
    public String createUser(@RequestParam("balance") double balance) {
        userService.create(balance);
        return "redirect:/showAllUsers";
    }

    @PostMapping("/showAllUsers/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId) {
        userService.delete(userId);
        return "redirect:/showAllUsers";
    }
    // если получает json
    //@PostMapping("/createUser")
    //    public String createUser(@RequestBody User user ){
    //        balanceOperation.create(user);
    //       return "greeting";
    //    }

    @GetMapping(value = "/showAllUsers", produces = "text/plain")
    public String showAllUsers(Model model) {

        List<User> users = balanceOperation.showAllUsers();

        model.addAttribute("users", users);


        return "/showAllUsers";
    }



    ///////////////////////////////////////////////////////////////////////////////////////
    //добавляет пару ключ значение во все модели текущего контроллера
    @ModelAttribute("HeaderMessage")
    public String populateHeaderMessage() {
        return "Welcome to our website";
    }


}
