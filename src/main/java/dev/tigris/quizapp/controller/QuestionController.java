package dev.tigris.quizapp.controller;

import dev.tigris.quizapp.modal.Question;
import dev.tigris.quizapp.service.QuestionServiceImpl;
import dev.tigris.quizapp.utils.ApiPath;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiPath.QuestionCtrl.CTRL)
@CrossOrigin("http://localhost:4200")
public class QuestionController {

    private final QuestionServiceImpl questionService;

    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question){
        return new ResponseEntity<>(questionService.createQuestion(question), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return new ResponseEntity<>(questionService.getAllQuestions(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id){

        Optional<Question> questionById = questionService.getQuestionById(id);
        return questionById.map(question -> new ResponseEntity<>(question, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/subject")
    public ResponseEntity<List<String>> getAllSubjects(){
        return new ResponseEntity<>(questionService.getAllSubjects(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) throws ChangeSetPersister.NotFoundException {
        Optional<Question> questionInDb = questionService.getQuestionById(id);

        if(questionInDb.isPresent()){
            return new ResponseEntity<>(questionService.updateQuestion(id,question),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/random-question")
    public ResponseEntity<List<Question>> getQuestionsForUser(@RequestParam Integer numOfQuestion,@RequestParam String subject){

        List<Question> allQuestion = questionService.getQuestionsForUser(numOfQuestion, subject);
        List<Question> rndList=new ArrayList<>(allQuestion);
        Collections.shuffle(rndList);
        int questionCount = Math.min(numOfQuestion, rndList.size());
        List<Question> randomQuestions = rndList.subList(0, questionCount);
        return new ResponseEntity<>(randomQuestions,HttpStatus.OK);
    }
}
