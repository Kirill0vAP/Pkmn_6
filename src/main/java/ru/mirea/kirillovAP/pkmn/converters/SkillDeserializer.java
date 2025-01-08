package ru.mirea.kirillovAP.pkmn.converters;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.mirea.kirillovAP.pkmn.models.card.AttackSkill;

import java.io.IOException;
import java.util.Optional;

public class SkillDeserializer extends JsonDeserializer<AttackSkill> {
    @Override
    public AttackSkill deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        JsonNode skillNode = ctxt.readTree(p);

        AttackSkill skill = new AttackSkill();
        skill.setName(skillNode.get("Имя").asText(""));

        Optional.ofNullable(skillNode.get("Описание"))
                .ifPresent(
                        jsonNode -> skill.setDescription(jsonNode.asText(""))
                );

        skill.setCost(skillNode.get("Цена").asText(""));
        skill.setDamage(skillNode.get("Урон").asInt(0));

        return skill;
    }
}

