package ru.graduation.voting.service;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.repository.CrudDishRepository;
import ru.graduation.voting.to.MenuTo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.graduation.voting.util.DateTimeUtil.ifNullThenNow;

@Service
public class MenuService {
    private final Logger log = getLogger(MenuService.class);

    private final CrudDishRepository dishRepository;

    @Autowired
    private MenuService service;

    public MenuService(CrudDishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<MenuTo> getMenu(@Nullable LocalDate date) {
        return service.getMenuByDate(ifNullThenNow(date));
    }

    @Cacheable(value = "menu", key = "#date")
    public List<MenuTo> getMenuByDate(@NotNull LocalDate date) {
        log.info("get menu by date {}", date);
        List<Dish> dishes = dishRepository.getAllByDate(date);
        return dishes.stream()
                .collect(Collectors.groupingBy(Dish::getRestaurant, Collectors.toList()))
                .entrySet().stream()
                .map(e -> new MenuTo(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}