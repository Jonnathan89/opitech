-- script para creacion de la tabla
CREATE TABLE hero (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(100) NOT NULL,
    nick_name VARCHAR(100),

    universe VARCHAR(20) NOT NULL,
    power_level INTEGER NOT NULL,

    active BOOLEAN DEFAULT TRUE NOT NULL,

    created_at DATE  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at DATE  DEFAULT CURRENT_TIMESTAMP 
);

-- script para creacion del index en la tabla con en nombre 
CREATE UNIQUE INDEX ui_hero_name ON hero (name);

-- script para limitar valores numericos del campo
ALTER TABLE hero
    ADD CONSTRAINT ch_hero_power_level
    CHECK (power_level BETWEEN 1 AND 100);