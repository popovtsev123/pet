package ru.offer.candidate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.offer.candidate.entity.CandidateEntity;
import ru.offer.candidate.entity.Level;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

    @Query("SELECT c FROM CandidateEntity c WHERE c.level IN :level")
    List<CandidateEntity> getCandidateEntitiesByLevel(@Param("levels") List<String> levels);

    @Query("SELECT c FROM CandidateEntity c WHERE c.experience IN :experiences")
    List<CandidateEntity> getCandidateEntitiesByExperiences(@Param("experiences") List<String> experiences);

    Optional<CandidateEntity> findById(Long id);
}
