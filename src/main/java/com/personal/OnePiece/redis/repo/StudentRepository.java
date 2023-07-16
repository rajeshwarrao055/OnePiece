package com.personal.OnePiece.redis.repo;

import com.personal.OnePiece.redis.data.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {}
