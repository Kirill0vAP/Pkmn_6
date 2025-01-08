package ru.mirea.kirillovAP.pkmn.daos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.kirillovAP.pkmn.models.student.StudentEntity;
import ru.mirea.kirillovAP.pkmn.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StudentDAOImpl implements StudentDAO{
    private final StudentRepository studentRepository;

    @Override
    public StudentEntity getStudentById(UUID id) {
        return studentRepository.findStudentEntityById(id).orElseThrow(
                () -> new RuntimeException("Студент с айди " + id + " не найден")
        );
    }

    @Override
    public List<StudentEntity> getStudentsByGroup(String group) {
        return studentRepository.findStudentEntitiesByGroup(group).orElseThrow(
                () -> new RuntimeException("Студент из группы " + group + " не найден")
        );
    }

    @Override
    public StudentEntity getStudentByFullName(String familyName, String firstName, String surName) {
        return studentRepository.findStudentEntityByFirstNameAndFamilyNameAndSurName(firstName, familyName, surName).orElseThrow(
                () -> new RuntimeException("Полное имя студента " + firstName + " " + familyName + " " + surName + " не найдено")
        );
    }

    @Override
    public Optional<StudentEntity> getExactStudent(String firstName, String familyName, String surName, String group) {
        return studentRepository.findStudentEntityByFirstNameAndFamilyNameAndSurNameAndGroup(firstName, familyName, surName, group);
    }

    @Override
    public StudentEntity saveStudent(StudentEntity student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(StudentEntity student) {
        studentRepository.delete(student);
    }

    @Override
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }
}