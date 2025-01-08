package ru.mirea.kirillovAP.pkmn.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.kirillovAP.pkmn.daos.StudentDAO;
import ru.mirea.kirillovAP.pkmn.models.student.Student;
import ru.mirea.kirillovAP.pkmn.models.student.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    private final StudentDAO studentDAO;

    @Override
    public Student getStudentById(UUID id) {
        return Student.fromEntity(studentDAO.getStudentById(id));
    }

    @Override
    public List<Student> getStudentsByGroup(String group) {
        List<StudentEntity> s = studentDAO.getStudentsByGroup(group);
        if (s.isEmpty()){
            throw new RuntimeException("Students with group " + group + " not found");
        }
        return s.stream().map(Student::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Student getStudentByFullName(String familyName, String firstName, String surName) {
        return Student.fromEntity(studentDAO.getStudentByFullName(familyName, firstName, surName));
    }

    @Override
    public Student getStudentByFullName(String fullName) {
        String[] arr = fullName.split(" ");
        if (arr.length < 3){
            throw new RuntimeException("Invalid full name");
        }
        return getStudentByFullName(arr[0], arr[1], arr[2]);
    }

    @Override
    public Optional<StudentEntity> getExactStudent(String firstName, String familyName, String surName, String group) {
        return studentDAO.getExactStudent(firstName, familyName, surName, group);
    }

    @Transactional
    @Override
    public StudentEntity saveStudent(Student student) {
        if (studentDAO.getExactStudent(
                student.getFirstName(),
                student.getFamilyName(),
                student.getSurName(),
                student.getGroup()
        ).isPresent()) {
            throw new RuntimeException("Student already exists");
        }

        StudentEntity studentEntity = StudentEntity.fromModel(student);
        return studentDAO.saveStudent(studentEntity);
    }

    @Override
    public void deleteStudent(Student student) {
        Optional<StudentEntity> entity = studentDAO.getExactStudent(student.getFamilyName(), student.getFirstName(), student.getSurName(), student.getGroup());
        if(entity.isEmpty()){
            throw new RuntimeException("Student not found");
        }
        studentDAO.deleteStudent(entity.get());
    }

    @Override
    public List<Student> getAllStudents() {
        List<StudentEntity> students = studentDAO.getAllStudents();
        if (!students.isEmpty()){
            return students.stream().map(Student::fromEntity).collect(Collectors.toList());
        }
        throw new RuntimeException("No students found");
    }
}
