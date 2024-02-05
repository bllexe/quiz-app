package dev.tigris.quizapp.utils;

import org.hibernate.dialect.unique.CreateTableUniqueDelegate;

public class ApiPath {
    public  static final String BASE_PATH = "/api";
    public static final class QuestionCtrl{
        public static final String CTRL = BASE_PATH + "/question";
    }

}
