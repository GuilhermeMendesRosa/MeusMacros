SELECT * FROM food_item
WHERE to_tsvector('portuguese', name) @@ plainto_tsquery('portuguese', 'carne de patinho');
