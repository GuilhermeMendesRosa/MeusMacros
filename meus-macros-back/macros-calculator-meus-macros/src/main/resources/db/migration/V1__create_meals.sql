CREATE TABLE meals
(
    id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_uuid     CHAR(36) NOT NULL,
    date          DATETIME NOT NULL,
    calories      INT      NOT NULL,
    protein       INT      NOT NULL,
    carbohydrates INT      NOT NULL,
    fat           INT      NOT NULL
);

CREATE TABLE meal_items
(
    id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    meal_id       BIGINT UNSIGNED NOT NULL,
    name          VARCHAR(255) NOT NULL,
    quantity      VARCHAR(50)  NOT NULL,
    calories      INT          NOT NULL,
    protein       INT          NOT NULL,
    carbohydrates INT          NOT NULL,
    fat           INT          NOT NULL,
    FOREIGN KEY (meal_id) REFERENCES meals (id) ON DELETE CASCADE
);

CREATE INDEX idx_meals_user_uuid ON meals (user_uuid);
CREATE INDEX idx_meal_items_meal_id ON meal_items (meal_id);
