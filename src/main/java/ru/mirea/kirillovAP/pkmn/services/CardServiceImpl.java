package ru.mirea.kirillovAP.pkmn.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.kirillovAP.pkmn.daos.CardDAO;
import ru.mirea.kirillovAP.pkmn.models.card.Card;
import ru.mirea.kirillovAP.pkmn.models.card.CardEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{
    private final CardDAO cardDAO;
    private final StudentService studentService;
    private final PokemonTcgAPIService pokemonTcgAPIService;

    @Override
    public Card getCardById(UUID id) {
        return Card.fromEntity(cardDAO.getCardById(id));
    }

    @Override
    public Card getCardByCardName(String cardName) {
        return Card.fromEntity(cardDAO.getCardByCardName(cardName));
    }

    @Override
    public Card getCardByOwnerId(String ownerId) {
        return Card.fromEntity(cardDAO.getCardByOwnerId(UUID.fromString(ownerId)));
    }

    @Override
    public Card getCardByOwnerName(String ownerName) {
        String[] arr = ownerName.split(" ");
        if (arr.length < 3){
            throw new RuntimeException("Invalid owner name");
        }
        return getCardByOwnerName(arr[0], arr[1], arr[2]);
    }

    @Override
    public Card getCardByOwnerName(String ownerFamilyName, String ownerFirstName, String ownerSurName) {
        return Card.fromEntity(cardDAO.getCardByOwnerName(ownerFamilyName, ownerFirstName, ownerSurName));
    }

    @Override
    public List<Card> getAllCards() {
        List<CardEntity> entities = cardDAO.getAllCards();
        if (entities.isEmpty()){
            throw new RuntimeException("No cards found");
        }
        return entities.stream().map(Card::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CardEntity saveCard(Card card) {
        cardDAO.getExactCard(card.getName(), card.getGameSet(), card.getNumber(), card.getPokemonStage())
                .ifPresent(existingCard -> {
                    throw new RuntimeException("Card already exists");
                });

        CardEntity entity = CardEntity.fromModel(card);

        if (card.getPokemonOwner() != null) {
            entity.setPokemonOwner(
                    studentService.getExactStudent(
                            card.getPokemonOwner().getFirstName(),
                            card.getPokemonOwner().getFamilyName(),
                            card.getPokemonOwner().getSurName(),
                            card.getPokemonOwner().getGroup()
                    ).orElseGet(() -> studentService.saveStudent(card.getPokemonOwner()))
            );
        }

        if (card.getEvolvesFrom() != null) {
            entity.setEvolvesFrom(saveCard(card.getEvolvesFrom()));
        }

        return cardDAO.saveCard(entity);
    }

    @Transactional
    @Override
    public void deleteCard(Card card) {
        CardEntity entity = cardDAO.getExactCard(
                        card.getName(),
                        card.getGameSet(),
                        card.getNumber(),
                        card.getPokemonStage()
                )
                .orElseThrow(
                        () -> new RuntimeException("No such card"));
        cardDAO.deleteCard(entity);
    }

    @Override
    public String getCardImageLink(Card card) {
        try {
            return pokemonTcgAPIService.getPokemonCardURL(card.getName(), card.getNumber());
        } catch (IOException e) {
            throw new RuntimeException("Error while getting card image");
        }
    }
}
