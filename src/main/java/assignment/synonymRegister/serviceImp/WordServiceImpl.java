package assignment.synonymRegister.serviceImp;
import assignment.synonymRegister.exceptions.WordNotFoundException;
import assignment.synonymRegister.helper.HelperFunction;
import assignment.synonymRegister.model.Word;
import assignment.synonymRegister.repository.WordRepository;
import assignment.synonymRegister.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WordServiceImpl implements WordService {
   @Autowired
   private WordRepository wordRepository;
   @Autowired
   private HelperFunction helperFunction;

   //   To add new word with synonyms or assign synonyms to existing word;
   @Override
   public Word addWord(Word word) {
      Optional<Word> optionalWord = wordRepository.findByWordIgnoreCase(word.getWord());
      try {
         if (optionalWord.isPresent()) {
            // Word with the same word already exists
            Word existingWord = optionalWord.get();

            for (String synonym : word.getSynonyms()) {
               // Check if the synonym is not the same as the existing word
               if (!synonym.equalsIgnoreCase(existingWord.getWord()) && !existingWord.getSynonyms().contains(synonym)) {
                  helperFunction.assignSynonyms(existingWord, synonym);
               }
            }
         } else {
            Word newWord = new Word();
            newWord.setWord(word.getWord());
            wordRepository.save(newWord);

            for (String synonym : word.getSynonyms()) {
               // Check if the synonym is not the same as the existing word
               if (!synonym.equalsIgnoreCase(newWord.getWord()) && !newWord.getSynonyms().contains(synonym)) {
                  helperFunction.assignSynonyms(newWord, synonym);
               }
            }
         }
         return word;
      } catch (Exception e) {
         throw e;
      }
   }


   //   To edit word with synonyms
   @Override
   public Word editWord(Long wordId, Word editedWord) {
      // Find the word by its ID
      Optional<Word> optionalWord = wordRepository.findById(wordId);

      if (optionalWord.isPresent()) {
         Word word = optionalWord.get();

         // Get the existing synonyms before the update
         Set<String> oldSynonyms = new HashSet<>(word.getSynonyms());
         Set<String> newSynonyms = new HashSet<>(editedWord.getSynonyms());

         // Update the word properties
         word.setWord(editedWord.getWord());
         word.setSynonyms(newSynonyms);
         wordRepository.save(word);

         // Remove the edited word from the synonyms of any synonyms that were removed
         for (String synonym : oldSynonyms) {
            if (!newSynonyms.contains(synonym)) {
               helperFunction.deAssignSynonyms(word, synonym);
            }
         }

         // Add new synonyms which were not present initially
         for (String synonym : newSynonyms) {
            if (!oldSynonyms.contains(synonym)) {
               helperFunction.assignSynonyms(word, synonym);
            }
         }

         return word;
      } else {
         // Word with the given ID is not found
         throw new WordNotFoundException("Word with ID " + wordId + " not found");
      }
   }



   //   To delete word
   @Override
   public void deleteWord(Long wordId) {
      try {
         Optional<Word> optionalWord = wordRepository.findById(wordId);
         if (optionalWord.isPresent()) {
            Word word = optionalWord.get();

            for (String synonym : word.getSynonyms()) {
               helperFunction.deAssignSynonyms(word, synonym);
            }

            wordRepository.delete(word);
         } else {
            // Word with the given ID is not found
            throw new WordNotFoundException("Word with ID " + wordId + " not found");
         }
      } catch (Exception e) {
         throw e;
      }
   }


   //   To get synonyms of particular word
   @Override
   public Set<String> getSynonyms(String word) {
      Set<String> result = new HashSet<>();
      Word initialWord = wordRepository.findByWordIgnoreCase(word).orElse(null);

      if (initialWord == null) {
         // Handle the case when the initial word is not found
         throw new WordNotFoundException("Word not found: " + word);
      }

      Set<String> visited = new HashSet<>();
      try {
         helperFunction.getSynonymsRecursive(initialWord, result, visited);
      } catch (Exception e) {
         // Handle any exception that might occur during synonym retrieval
         throw new WordNotFoundException("Error retrieving synonyms for word: " + word);
      }

      return result;
   }


   //   To assign synonyms
   @Override
   public Word assignSynonym(Long wordId, String synonym) {
      try {
         Word word = wordRepository.findById(wordId)
                 .orElseThrow(() -> new WordNotFoundException("Word not found with ID: " + wordId));

         helperFunction.assignSynonyms(word, synonym);
         return word;
      } catch (Exception e) {
         throw new RuntimeException("An unexpected error occurred.", e);
      }
   }


   //   To deAssign synonyms
   @Override
   public Word deAssignSynonym(Long wordId, String synonym) {
      try {
         Word word = wordRepository.findById(wordId)
                 .orElseThrow(() -> new WordNotFoundException("Word not found with ID: " + wordId));

         helperFunction.deAssignSynonyms(word, synonym);
         return word;
      } catch (Exception e) {
         throw new RuntimeException("An unexpected error occurred.", e);
      }
   }

//   To get all word with their direct synonyms
   @Override
   public List<Word> getAllWords() {
      return wordRepository.findAll();
   }

   @Override
   public void deleteAllWord() {
      wordRepository.deleteAll();
   }
}
