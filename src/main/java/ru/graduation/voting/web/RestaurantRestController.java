package ru.graduation.voting.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.voting.View;
import ru.graduation.voting.model.Restaurant;
import ru.graduation.voting.service.RestaurantService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    static final String REST_URL = "/rest/admin/restaurants";

    private final RestaurantService service;

    public RestaurantRestController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Rest.class) @RequestBody Restaurant restaurant, @PathVariable int id) {
        service.update(restaurant, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated(View.Rest.class) @RequestBody Restaurant restaurant) {
        Restaurant created = service.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}