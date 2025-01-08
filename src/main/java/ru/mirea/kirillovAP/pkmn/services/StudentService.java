package ru.mirea.kirillovAP.pkmn.services;

import ru.mirea.kirillovAP.pkmn.models.student.Student;
import ru.mirea.kirillovAP.pkmn.models.student.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentService {
    public Student getStudentById(UUID id);
    public List<Student> getStudentsByGroup(String group);
    public Student getStudentByFullName(String familyName, String firstName, String surName);
    public Student getStudentByFullName(String fullName);
    public Optional<StudentEntity> getExactStudent(String firstName, String familyName, String surName, String group);
    public StudentEntity saveStudent(Student student);
    public void deleteStudent(Student student);

    List<Student> getAllStudents();
}

