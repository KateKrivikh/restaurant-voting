package ru.voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Vote;

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
}