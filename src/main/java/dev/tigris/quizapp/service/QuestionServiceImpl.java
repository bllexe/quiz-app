package dev.tigris.quizapp.service;

import dev.tigris.quizapp.modal.Question;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public interface QuestionServiceImpl {

    Question createQuestion(Question question);
    List<Question> getAllQuestions();
    Optional<Question> getQuestionById(Long id);
    List<String> getAllSubjects();
    Question updateQuestion(Long id,Question question) throws ChangeSetPersister.NotFoundException;
    void deleteQuestion(Long id);

    List<Question> getQuestionsForUser(Integer numOfQuestion,String subject);

}
