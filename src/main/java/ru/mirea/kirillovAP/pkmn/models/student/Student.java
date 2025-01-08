package ru.mirea.kirillovAP.pkmn.models.student;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String firstName;
    private String familyName;
    private String surName;
    private String group;

    public static Student fromEntity(StudentEntity entity)
    {
        return Student.builder()
                .firstName(entity.getFirstName())
                .familyName(entity.getFamilyName())
                .surName(entity.getSurName())
                .group(entity.getGroup())
                .build();
    }
}