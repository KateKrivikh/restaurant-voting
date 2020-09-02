package ru.graduation.voting.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.voting.service.MenuService;
import ru.graduation.voting.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    static final String REST_URL = "/rest/menu";

    private final MenuService service;

    public MenuRestController(MenuService service) {
        this.service = service;
    }

    @GetMapping
    public List<MenuTo> getAll(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getMenu(date);
    }
}