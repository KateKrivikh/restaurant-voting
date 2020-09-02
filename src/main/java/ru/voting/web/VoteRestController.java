package ru.voting.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.AuthorizedUser;
import ru.voting.model.Vote;
import ru.voting.service.VoteService;

import java.net.URI;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest/votes";

    private final VoteService service;

    public VoteRestController(VoteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        Vote created = service.vote(authUser.getId(), restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}