package ru.mirea.kirillovAP.pkmn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.kirillovAP.pkmn.models.student.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    Optional<StudentEntity> findStudentEntityById(UUID studentId);
    Optional<List<StudentEntity>> findStudentEntitiesByGroup(String group);
    Optional<StudentEntity> findStudentEntityByFirstNameAndFamilyNameAndSurName(String firstName, String familyName, String surName);
    Optional<StudentEntity> findStudentEntityByFirstNameAndFamilyNameAndSurNameAndGroup(String firstName, String familyName, String surName, String group);
}

