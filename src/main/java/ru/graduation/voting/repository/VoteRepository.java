package ru.graduation.voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.Vote;

import java.time.LocalDate;

@Repository
public class VoteRepository {
    private final CrudVoteRepository crudRepository;

    public VoteRepository(CrudVoteRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Vote get(int id) {
        return crudRepository.findById(id)
                .orElse(null);
    }

    @Transactional
    public Vote save(Vote vote) {
        return crudRepository.save(vote);
    }

    public Vote getByUserAndDate(int userId, LocalDate date) {
        return crudRepository.getByUserAndDate(userId, date);
    }
}