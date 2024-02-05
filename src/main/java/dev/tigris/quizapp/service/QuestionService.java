package dev.tigris.quizapp.service;

import dev.tigris.quizapp.modal.Question;
import dev.tigris.quizapp.repository.QuestionRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService implements QuestionServiceImpl{

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDistinctSubject();
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException {
        Optional<Question> questionInDb = questionRepository.findById(id);
        if (questionInDb.isPresent()) {
            Question updatedQuestion=questionInDb.get();
            updatedQuestion.setQuestion(question.getQuestion());
            updatedQuestion.setSubject(question.getSubject());
            updatedQuestion.setQuestionType(question.getQuestionType());
            updatedQuestion.setChoices(question.getChoices());
            updatedQuestion.setCorrectAnswer(question.getCorrectAnswer());
            return questionRepository.save(question);
        }else {
            throw new  ChangeSetPersister.NotFoundException();
        }

    }

    @Override
    public void deleteQuestion(Long id) {
        Optional<Question> questionInDb = questionRepository.findById(id);
        questionInDb.ifPresent(questionRepository::delete);

    }

    @Override
    public List<Question> getQuestionsForUser(Integer numOfQuestion, String subject) {
        Pageable pageable= PageRequest.of(0,numOfQuestion);
        return questionRepository.findBySubject(subject,pageable).getContent();

    }
}
