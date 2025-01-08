package ru.mirea.kirillovAP.pkmn.daos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.kirillovAP.pkmn.models.card.CardEntity;
import ru.mirea.kirillovAP.pkmn.models.card.PokemonStage;
import ru.mirea.kirillovAP.pkmn.repositories.CardRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CardDAOImpl implements CardDAO {
    private final CardRepository cardRepository;

    @Override
    public CardEntity getCardById(UUID id) {
        return cardRepository.findCardEntityById(id).orElseThrow(
                () -> new RuntimeException("карта с айди " + id + " не найдена")
        );
    }

    @Override
    public CardEntity getCardByCardName(String cardName) {
        return cardRepository.findCardEntityByName(cardName).orElseThrow(
                () -> new RuntimeException("карта с именем " + cardName + " не найдена")
        );
    }

    @Override
    public CardEntity getCardByOwnerId(UUID ownerId) {
        return cardRepository.findCardEntityByPokemonOwnerId(ownerId).orElseThrow(
                () -> new RuntimeException("карта владельца " + ownerId + " не найдена")
        );
    }

    @Override
    public CardEntity getCardByOwnerName(String ownerFamilyName, String ownerFirstName, String ownerSurName) {
        return cardRepository.findCardEntityByPokemonOwnerFullName(ownerFamilyName, ownerFirstName, ownerSurName).orElseThrow(
                () -> new RuntimeException("карта полного имени " + ownerFamilyName + " " + ownerFirstName + " " + ownerSurName + " не найдена")
        );
    }

    @Override
    public Optional<CardEntity> getExactCard(String name, String gameSet, String number, PokemonStage stage) {
        return cardRepository.findExactCardEntity(name, gameSet, number, stage);
    }

    @Override
    public List<CardEntity> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public CardEntity saveCard(CardEntity card) {
        return cardRepository.save(card);
    }

    @Override
    public void deleteCard(CardEntity card) {
        cardRepository.delete(card);
    }
}

