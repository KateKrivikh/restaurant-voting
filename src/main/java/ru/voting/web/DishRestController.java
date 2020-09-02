package ru.voting.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.View;
import ru.voting.model.Dish;
import ru.voting.service.DishService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    static final String REST_URL = "/rest/admin/restaurants/{restaurant_id}/dishes";

    private final DishService service;

    public DishRestController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dish> getAll(@PathVariable int restaurant_id,
                             @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getAll(restaurant_id, date);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int restaurant_id, @PathVariable int id) {
        return service.get(id, restaurant_id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurant_id, @PathVariable int id) {
        service.delete(id, restaurant_id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Rest.class) @RequestBody Dish dish, @PathVariable int restaurant_id, @PathVariable int id) {
        service.update(dish, id, restaurant_id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Validated(View.Rest.class) @RequestBody Dish dish, @PathVariable int restaurant_id) {
        Dish created = service.create(dish, restaurant_id);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getRestaurant().getId(), created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}