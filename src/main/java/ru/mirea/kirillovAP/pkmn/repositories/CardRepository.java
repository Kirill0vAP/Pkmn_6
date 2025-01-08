package ru.mirea.kirillovAP.pkmn.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.kirillovAP.pkmn.models.card.CardEntity;
import ru.mirea.kirillovAP.pkmn.models.card.PokemonStage;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<CardEntity, UUID> {
    Optional<CardEntity> findCardEntityByName(String name);
    Optional<CardEntity> findCardEntityById(UUID cardId);
    Optional<CardEntity> findCardEntityByPokemonOwnerId(UUID pokemonOwnerId);
    @Query("SELECT c FROM CardEntity c WHERE c.pokemonOwner.familyName = :familyName AND c.pokemonOwner.firstName = :firstName AND c.pokemonOwner.surName = :surName")
    Optional<CardEntity> findCardEntityByPokemonOwnerFullName(String familyName, String firstName, String surName);
    @Query("SELECT c FROM CardEntity c WHERE c.name = :name AND c.gameSet = :gameSet AND c.number = :number AND c.pokemonStage = :stage")
    Optional<CardEntity> findExactCardEntity(String name, String gameSet, String number, PokemonStage stage);
}

