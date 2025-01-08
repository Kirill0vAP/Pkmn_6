package ru.mirea.kirillovAP.pkmn.models.card;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import ru.mirea.kirillovAP.pkmn.converters.SkillDeserializer;
import ru.mirea.kirillovAP.pkmn.models.student.StudentEntity;

@Entity
@Table(name = "card")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class CardEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name="name")
    private String name;
    @Column(columnDefinition = "smallint")
    private short hp;

    @Column(name="card_number")
    private String number;
    @Column(name="retreat_cost")
    private String retreatCost;

    @Enumerated(EnumType.STRING)
    @Column(name="stage")
    private PokemonStage pokemonStage;
    @Enumerated(EnumType.STRING)
    @Column(name="pokemon_type", nullable = true)
    private EnergyType pokemonType;
    @Enumerated(EnumType.STRING)
    @Column(name="weakness_type", nullable = true)
    private EnergyType weaknessType;
    @Enumerated(EnumType.STRING)
    @Column(name="resistance_type", nullable = true)
    private EnergyType resistanceType;

    @Column(name="game_set")
    private String gameSet;

    @Column(name="regulation_mark")
    private char regulationMark;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "pokemon_owner")
    private StudentEntity pokemonOwner;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attack_skills")
    @JsonDeserialize(using = SkillDeserializer.class)
    private List<AttackSkill> skills;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "evolves_from")
    private CardEntity evolvesFrom;

    public static CardEntity fromModel(Card card) {
        CardEntityBuilder builder = CardEntity.builder()
                .name(card.getName())
                .hp(card.getHp())
                .number(card.getNumber())
                .retreatCost(card.getRetreatCost())
                .pokemonStage(card.getPokemonStage())
                .pokemonType(card.getPokemonType())
                .weaknessType(card.getWeaknessType())
                .resistanceType(card.getResistanceType())
                .gameSet(card.getGameSet())
                .regulationMark(card.getRegulationMark())
                .skills(card.getSkills());
        if(card.getEvolvesFrom() != null) {
            builder.evolvesFrom(fromModel(card.getEvolvesFrom()));
        }
        if(card.getPokemonOwner() != null){
            builder.pokemonOwner(StudentEntity.fromModel(card.getPokemonOwner()));
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "Card{" +
                "pokemonStage=" + pokemonStage +
                ", name='" + name + '\'' +
                ", hp=" + hp +
                ", evolvesFrom=" + evolvesFrom +
                ", skills=" + skills +
                ", pokemonType=" + pokemonType +
                ", weaknessType=" + weaknessType +
                ", resistanceType=" + resistanceType +
                ", retreatCost='" + retreatCost + '\'' +
                ", gameSet='" + gameSet + '\'' +
                ", regulationMark=" + regulationMark +
                ", owner=" + pokemonOwner.toString() +
                '}';
    }
}
