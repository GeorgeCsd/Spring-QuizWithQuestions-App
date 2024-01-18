package com.georgeCsd.quizapp;

import com.georgeCsd.quizapp.repository.QuestionRepo;
import com.georgeCsd.quizapp.model.Question;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuizappApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizappApplication.class, args);
    }

    @Bean
    CommandLineRunner run(QuestionRepo questionRepo) {
        return args -> {
            questionRepo.save(new Question("What is a class in Java?", "A function", "An object", "A data structure", "A loop", "An object", "Easy", "Java"));
            questionRepo.save(new Question("What does OOP stand for?", "Object-Oriented Programming", "Object Ordering Process", "Operating Overloaded Pointers", "Order of Operations", "Object-Oriented Programming", "Easy", "Java"));
            questionRepo.save(new Question("What is a list in Python?", "A type of loop", "A built-in function", "A data structure", "An object", "A data structure", "Easy", "Python"));
            questionRepo.save(new Question("Which data structure uses First-In-First-Out (FIFO) order?", "Stack", "Queue", "Array", "LinkedList", "Queue", "Medium", "Python"));
            questionRepo.save(new Question("What is a constructor?", "A member of a class", "A loop in Python", "A data type", "A special method", "A special method", "Medium", "Java"));
            questionRepo.save(new Question("In Java, what is used to create an instance of a class?", "Class", "Method", "Object", "Constructor", "Constructor", "Easy", "Java"));
            questionRepo.save(new Question("Which keyword is used to define a variable that won’t be reassigned?", "static", "final", "constant", "immutable", "final", "Easy", "Java"));
			questionRepo.save(new Question ("Which keyword is used to define a subclass in Java?", "child", "extends", "inherits", "subclass", "extends", "Easy", "java"));
			questionRepo.save(new Question("Which operator is used to concatenate strings in Python?", "&", "+", "*", "++", "+", "Easy", "Python"));
			questionRepo.save(new Question("What is a linked list?", "A type of array", "A linear data structure", "A collection of objects", "A group of classes", "A linear data structure", "Medium", "java"));
			questionRepo.save(new Question("What is a lambda function in Python?", "A function that uses the lambda keyword", "A function with multiple return values", "A function with no parameters", "An anonymous inline function", "An anonymous inline function", "Medium", "Python"));
			questionRepo.save(new Question ("In Java, which access modifier provides the widest visibility?", "public", "private", "protected", "package-private", "public", "Easy", "java"));
			questionRepo.save(new Question ("What is the purpose of import in Python?", "To export data", "To create a backup", "To include external modules", "To print output", "To include external modules", "Easy", "Python"));
			questionRepo.save(new Question("What keyword is used to inherit a class in Python?", "extends", "inherits", "super", "class", "class", "Easy", "Python"));
			questionRepo.save(new Question("What is a dictionary in Python?", "A sorted collection of elements", "A data structure used for searching", "An ordered sequence of elements", "A key-value store", "A key-value store", "Easy", "Python"));
			questionRepo.save(new Question("In Java, which access modifier provides the widest visibility?", "public", "private", "protected", "package-private", "public", "Easy", "java"));
        };
    }

}
