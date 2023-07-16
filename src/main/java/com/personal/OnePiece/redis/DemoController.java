package com.personal.OnePiece.redis;

import com.personal.OnePiece.redis.data.Student;
import com.personal.OnePiece.redis.pubsub.MessagePublisher;
import com.personal.OnePiece.redis.repo.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MessagePublisher messagePublisher;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Student> fetchStudentFromCache(@PathVariable String studentId) {
        return ResponseEntity.of(studentRepository.findById(studentId));
    }

    @PostMapping("/student")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return ResponseEntity.ok("YAY");
    }

    @GetMapping("/publish")
    public void publishMessageToQueue() {
        messagePublisher.publish("HELLO WORLD");
    }
}
