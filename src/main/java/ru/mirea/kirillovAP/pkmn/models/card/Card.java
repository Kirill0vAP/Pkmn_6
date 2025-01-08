package ru.mirea.kirillovAP.pkmn.models.card;

import lombok.*;
import ru.mirea.kirillovAP.pkmn.models.student.Student;

import java.util.List;

@Data
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String name;
    private short hp;

    private String number;
    private String retreatCost;
    private String gameSet;

    private PokemonStage pokemonStage;

    private EnergyType pokemonType;
    private EnergyType weaknessType;
    private EnergyType resistanceType;

    private char regulationMark;

    private Student pokemonOwner;

    private List<AttackSkill> skills;

    private Card evolvesFrom;

    public static Card fromEntity(CardEntity entity)
    {
        CardBuilder cardBuilder = Card.builder()
                .name(entity.getName())
                .hp(entity.getHp())
                .number(entity.getNumber())
                .retreatCost(entity.getRetreatCost())
                .gameSet(entity.getGameSet())
                .pokemonStage(entity.getPokemonStage())
                .pokemonType(entity.getPokemonType())
                .weaknessType(entity.getWeaknessType())
                .resistanceType(entity.getResistanceType())
                .regulationMark(entity.getRegulationMark())
                .skills(entity.getSkills());
        if (entity.getEvolvesFrom() != null)
        {
            cardBuilder.evolvesFrom(fromEntity(entity.getEvolvesFrom()));
        }
        if(entity.getPokemonOwner() != null)
        {
            cardBuilder.pokemonOwner(Student.fromEntity(entity.getPokemonOwner()));
        }
        return cardBuilder.build();
    }

}
