package assignment.synonymRegister.helper;
import assignment.synonymRegister.model.Word;
import assignment.synonymRegister.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.Set;

@Component
public class HelperFunction {
    @Autowired
    private WordRepository wordRepository;

    //    To assign synonyms
    public void assignSynonyms(Word word, String synonym) {
        if (!word.getSynonyms().contains(synonym)) {
            word.getSynonyms().add(synonym);
            wordRepository.save(word);
        }
        Optional<Word> optionalWord = wordRepository.findByWordIgnoreCase(synonym);
        if (optionalWord.isPresent()) {
            Word synonymExistAsWord = optionalWord.get();
            if (!synonymExistAsWord.getSynonyms().contains(word.getWord())) {
                synonymExistAsWord.getSynonyms().add(word.getWord());
                wordRepository.save(synonymExistAsWord);
            }
        } else {
            Word createSynonymsAsWord = new Word();
            createSynonymsAsWord.setWord(synonym);
            createSynonymsAsWord.getSynonyms().add(word.getWord());
            wordRepository.save(createSynonymsAsWord);
        }
    }

    //   Recursive function for transit rule
    public void getSynonymsRecursive(Word word, Set<String> result, Set<String> visited) {
        if (!visited.contains(word.getWord())) {
            visited.add(word.getWord());
            result.addAll(word.getSynonyms());
            for (String synonym : word.getSynonyms()) {
                Word synonymWord = wordRepository.findByWordIgnoreCase(synonym).orElse(null);
                if (synonymWord != null) {
                    getSynonymsRecursive(synonymWord, result, visited);
                }
            }
        }
    }

    //    To de-assign synonyms
    public void deAssignSynonyms(Word word, String synonym) {
        if (word.getSynonyms().contains(synonym)) {
            word.getSynonyms().remove(synonym);
            wordRepository.save(word);
        }
        Optional<Word> optionalWord = wordRepository.findByWordIgnoreCase(synonym);
        if (optionalWord.isPresent()) {
            Word synonymExistAsWord = optionalWord.get();
            if (synonymExistAsWord.getSynonyms().contains(word.getWord())) {
                synonymExistAsWord.getSynonyms().remove(word.getWord());
                wordRepository.save(synonymExistAsWord);
            }
        }
    }




}
