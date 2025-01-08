CREATE TABLE card (
  id UUID NOT NULL,
   stage VARCHAR(255),
   name VARCHAR(255),
   hp INTEGER NOT NULL,
   evolves_from UUID,
   attack_skills JSON,
   weakness_type VARCHAR(255),
   resistance_type VARCHAR(255),
   retreat_cost VARCHAR(255),
   game_set VARCHAR(255),
   pokemon_type VARCHAR(255),
   regulation_mark CHAR NOT NULL,
   pokemon_owner UUID,
   card_number VARCHAR(255),
   CONSTRAINT pk_cards PRIMARY KEY (id)
);

CREATE TABLE student (
  id UUID NOT NULL,
   "first_name" VARCHAR(255),
   "patronic_name" VARCHAR(255),
   "family_name" VARCHAR(255),
   "group" VARCHAR(255),
   CONSTRAINT pk_students PRIMARY KEY (id)
);

ALTER TABLE card ADD CONSTRAINT FK_CARDS_ON_EVOLVESFROM FOREIGN KEY (evolves_from) REFERENCES card (id);

ALTER TABLE card ADD CONSTRAINT FK_CARDS_ON_POKEMONOWNER FOREIGN KEY (pokemon_owner) REFERENCES student (id);