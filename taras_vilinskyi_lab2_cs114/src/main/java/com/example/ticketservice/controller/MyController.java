package com.example.ticketservice.controller;

import com.example.ticketservice.configuration.Config;
import com.example.ticketservice.model.entity.Ticket;
import com.example.ticketservice.model.entity.User;
import com.example.ticketservice.model.helper.Type;
import com.example.ticketservice.service.TicketService;
import com.example.ticketservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.stream.StreamSupport;

@Controller
public class MyController {

    // при переході на localhost:8080 відбудеться редірект на головну сторінку
    @RequestMapping("/")
    public String showmain() {
        return "redirect:/main.html";
    }

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    private final ApplicationContext factory = new AnnotationConfigApplicationContext(Config.class);

    @PostMapping("/register")
    public String register(@RequestParam("name") String name, @RequestParam("password") String password,
                           HttpSession httpSession) {
        if(name.length()>=3 && password.length()>=3 && userService.findByName(name)==null){
            userService.createAndSaveUser(name,password, (User)factory.getBean("user"));
            User user = userService.findByName(name);
            httpSession.setAttribute("id",user.getId());
        }
        return "redirect:/main.html";
    }

    @PostMapping("/logIn")
    public String logIn(@RequestParam("name") String name, @RequestParam("password") String password,
                        HttpSession httpSession) {
        User user = userService.findByNameAndPassword(name, password);
        if(user!=null) {
            httpSession.setAttribute("id",user.getId());
        }
        return "redirect:/main.html";
    }

    @GetMapping("/account")
    @ResponseBody
    public User account(HttpSession httpSession){
        Long id = (Long)httpSession.getAttribute("id");
        return id!=null ? userService.findById(id) : null;
    }

    @GetMapping("/tickets")
    @ResponseBody
    public Iterable<Ticket> tickets(){
        Iterable<Ticket> tickets = ticketService.findAllByUserId(null);
        if(StreamSupport.stream(tickets.spliterator(), false).count()==0) {
            ticketService.createRandomTickets(factory);
            tickets = ticketService.findAllByUserId(null);
        }
        return tickets;
    }

    @GetMapping("/ticketsByType")
    @ResponseBody
    public Iterable<Ticket> ticketsByType(@RequestParam("type") String typeString) {
        Type type = Type.fromString(typeString);
        return ticketService.findAllByTypeAndUserId(type, null);
    }

    @PostMapping("/buyTicket")
    public String buyTicket(HttpSession httpSession, Long id) {
        Long userId = (Long) httpSession.getAttribute("id");
        if(userId!=null) {
            User user = userService.findById(userId);
            Ticket ticket = ticketService.findById(id);
            if(ticket!=null && ticket.getUserId()==null) {
                user.getTicketList().add(ticket);
            }
            userService.save(user);
        }
        return "redirect:/main.html";
    }

    @PostMapping("/sellTicket")
    public String sellTicket(HttpSession httpSession, Long id) {
        Long userId = (Long) httpSession.getAttribute("id");
        if(userId!=null) {
            User user = userService.findById(userId);
            Ticket ticket = ticketService.findById(id);
            if(ticket!=null && ticket.getUserId().equals(user.getId())) {
                user.getTicketList().remove(ticket);
            }
            userService.save(user);
        }
        return "redirect:/main.html";
    }
}
