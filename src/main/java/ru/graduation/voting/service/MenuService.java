package ru.graduation.voting.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.graduation.voting.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

import static ru.graduation.voting.util.DateTimeUtil.ifNullThenNow;

@Service
public class MenuService {
    private final DishService service;

    public MenuService(DishService service) {
        this.service = service;
    }

    public List<MenuTo> getMenu(@Nullable LocalDate date) {
        return service.getMenuByDate(ifNullThenNow(date));
    }
}