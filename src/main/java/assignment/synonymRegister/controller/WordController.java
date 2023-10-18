package assignment.synonymRegister.controller;

import assignment.synonymRegister.model.Word;
import assignment.synonymRegister.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/words")
public class WordController {
    @Autowired
    private WordService wordService;

    // To save new word
    @PostMapping
    public ResponseEntity<Word> addWord(@RequestBody Word word) {
        try {
            Word savedWord = wordService.addWord(word);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedWord);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get synonyms by word
    @GetMapping("/get-synonyms")
    public ResponseEntity<Set<String>> getSynonyms(@RequestParam String word) {
        try {
            Set<String> synonyms = wordService.getSynonyms(word);
            return ResponseEntity.ok(synonyms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{wordId}")
    public ResponseEntity<Word> editWord(@PathVariable Long wordId, @RequestBody Word editedWord) {
        try {
            Word edited = wordService.editWord(wordId, editedWord);
            return ResponseEntity.ok(edited);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{wordId}")
    public ResponseEntity<String> deleteWord(@PathVariable Long wordId) {
        try {
            wordService.deleteWord(wordId);
            return ResponseEntity.ok("Word and its synonyms deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the word: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Word>> getAllWords() {
        try {
            List<Word> words = wordService.getAllWords();
            return ResponseEntity.ok(words);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/assign-synonym/{wordId}")
    public ResponseEntity<Word> assignSynonym(@PathVariable Long wordId, @RequestParam String synonym) {
        try {
            Word word = wordService.assignSynonym(wordId, synonym);
            return ResponseEntity.ok(word);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/deassign-synonym/{wordId}")
    public ResponseEntity<Word> deAssignSynonym(@PathVariable Long wordId, @RequestParam String synonym) {
        try {
            Word word = wordService.deAssignSynonym(wordId, synonym);
            return ResponseEntity.ok(word);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllWord() {
        try {
            wordService.deleteAllWord();
            return ResponseEntity.ok("Words and their synonyms deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the word: " + e.getMessage());
        }
    }
}
