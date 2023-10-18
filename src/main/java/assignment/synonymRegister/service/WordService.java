package assignment.synonymRegister.service;

import assignment.synonymRegister.model.Word;
import java.util.List;
import java.util.Set;

public interface WordService {

     Word addWord(Word word);
     Word editWord(Long wordId, Word editedWord);

     void deleteWord(Long wordId);
     Set<String> getSynonyms(String word);

     Word assignSynonym(Long wordId, String synonym);

     Word deAssignSynonym(Long wordId, String synonym);
    List<Word> getAllWords();

    void deleteAllWord();

}
