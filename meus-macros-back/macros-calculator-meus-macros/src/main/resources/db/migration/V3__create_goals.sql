CREATE TABLE goals
(
    id                       BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_uuid                CHAR(36) NOT NULL,
    date                     DATETIME NOT NULL,
    calories                 INT      NOT NULL,
    protein_percentage       INT      NOT NULL,
    carbohydrates_percentage INT      NOT NULL,
    fat_percentage           INT      NOT NULL
);
