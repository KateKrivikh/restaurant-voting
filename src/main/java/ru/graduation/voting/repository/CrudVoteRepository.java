package ru.graduation.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.Vote;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    Vote getById(int id);

    @Query("SELECT v FROM Vote v WHERE v.userId = :userId AND v.date = :date")
    Vote getByUserAndDate(@Param("userId") int userId, @Param("date") LocalDate date);
}