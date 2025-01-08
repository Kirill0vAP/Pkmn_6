package ru.mirea.kirillovAP.pkmn.models.student;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "student")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StudentEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "patronicName")
    private String surName;

    @Column(name = "familyName")
    private String familyName;

    @Column(name = "\"group\"")
    private String group;

    public static StudentEntity fromModel(Student student) {
        return StudentEntity.builder()
                .firstName(student.getFirstName())
                .familyName(student.getFamilyName())
                .surName(student.getSurName())
                .group(student.getGroup())
                .build();
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
