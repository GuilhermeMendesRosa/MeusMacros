CREATE TABLE meals (
    id BIGSERIAL PRIMARY KEY,
    user_uuid UUID NOT NULL,
    date TIMESTAMP WITH TIME ZONE NOT NULL,
    calories INT NOT NULL,
    protein INT NOT NULL,
    carbohydrates INT NOT NULL,
    fat INT NOT NULL
);

CREATE TABLE meal_items (
    id BIGSERIAL PRIMARY KEY,
    meal_id BIGINT NOT NULL REFERENCES meals(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    quantity VARCHAR(50) NOT NULL,
    calories INT NOT NULL,
    protein INT NOT NULL,
    carbohydrates INT NOT NULL,
    fat INT NOT NULL
);

CREATE INDEX idx_meals_user_uuid ON meals(user_uuid);
CREATE INDEX idx_meal_items_meal_id ON meal_items(meal_id);