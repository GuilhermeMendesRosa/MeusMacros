-- Criação da tabela food_item
CREATE TABLE food_item (
                           id SERIAL PRIMARY KEY,
                           name TEXT NOT NULL,
                           calories DOUBLE PRECISION NOT NULL,
                           protein DOUBLE PRECISION NOT NULL,
                           carbohydrates DOUBLE PRECISION NOT NULL,
                           fat DOUBLE PRECISION NOT NULL,
                           unit VARCHAR(3) NOT NULL CHECK (unit IN ('g', 'ml'))
);
