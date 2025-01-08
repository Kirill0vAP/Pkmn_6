package ru.mirea.kirillovAP.pkmn.daos;


import ru.mirea.kirillovAP.pkmn.models.card.CardEntity;
import ru.mirea.kirillovAP.pkmn.models.card.PokemonStage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardDAO {
    public CardEntity getCardById(UUID id);
    public CardEntity getCardByCardName(String cardName);
    public CardEntity getCardByOwnerId(UUID ownerId);
    public CardEntity getCardByOwnerName(String ownerFamilyName, String ownerFirstName, String ownerSurName);
    public Optional<CardEntity> getExactCard(String name, String gameSet, String number, PokemonStage stage);
    public List<CardEntity> getAllCards();
    public CardEntity saveCard(CardEntity card);
    public void deleteCard(CardEntity card);
}
