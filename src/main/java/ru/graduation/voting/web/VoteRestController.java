package ru.graduation.voting.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.graduation.voting.AuthorizedUser;
import ru.graduation.voting.service.VoteService;

@RestController
@RequestMapping(value = VoteRestController.REST_URL)
public class VoteRestController {
    static final String REST_URL = "/rest/votes";

    private final VoteService service;

    public VoteRestController(VoteService service) {
        this.service = service;
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestParam int restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        service.vote(authUser.getId(), restaurantId);
    }
}