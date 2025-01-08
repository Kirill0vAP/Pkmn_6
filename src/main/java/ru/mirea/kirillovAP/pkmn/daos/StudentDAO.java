package ru.mirea.kirillovAP.pkmn.daos;

import ru.mirea.kirillovAP.pkmn.models.student.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentDAO {
    public StudentEntity getStudentById(UUID id);
    public List<StudentEntity> getStudentsByGroup(String group);
    public StudentEntity getStudentByFullName(String familyName, String firstName, String surName);
    public Optional<StudentEntity> getExactStudent(String firstName, String familyName, String surName, String group);
    public StudentEntity saveStudent(StudentEntity student);
    public void deleteStudent(StudentEntity student);
    public List<StudentEntity> getAllStudents();
}
