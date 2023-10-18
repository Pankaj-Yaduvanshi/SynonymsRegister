package assignment.synonymRegister.repository;
import assignment.synonymRegister.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {
    Optional<Word> findByWordIgnoreCase(String word);

}
