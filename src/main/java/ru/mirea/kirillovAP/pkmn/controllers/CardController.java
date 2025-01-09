package ru.mirea.kirillovAP.pkmn.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.kirillovAP.pkmn.dtos.CardDTO;
import ru.mirea.kirillovAP.pkmn.models.card.Card;
import ru.mirea.kirillovAP.pkmn.models.student.Student;
import ru.mirea.kirillovAP.pkmn.services.CardService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.mirea.kirillovAP.pkmn.dtos.CardDTO.fromModel;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("")
    public ResponseEntity<List<CardDTO>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards.stream().map(card -> fromModel(card, cardService)).collect(Collectors.toList()));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable UUID id) {
        Card card = cardService.getCardById(id);
        return ResponseEntity.ok(fromModel(card, cardService));
    }

    @GetMapping("/{name}")
    public ResponseEntity<CardDTO> getCardByCardName(@PathVariable String name) {
        Card card = cardService.getCardByCardName(name);
        return ResponseEntity.ok(fromModel(card, cardService));
    }

    @GetMapping("/owner")
    public ResponseEntity<CardDTO> getCardByOwnerId(@RequestBody Student ownerRequest) {
        Card card = cardService.getCardByOwnerName(ownerRequest.getFamilyName(), ownerRequest.getFirstName(), ownerRequest.getSurName());
        return ResponseEntity.ok(fromModel(card, cardService));
    }

    @PostMapping("")
    public ResponseEntity<CardDTO> saveCard(@RequestBody Card card) {
        Card savedCard = Card.fromEntity(cardService.saveCard(card));
        return ResponseEntity.ok(fromModel(savedCard, cardService));
    }

}
