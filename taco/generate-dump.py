import json

# Ler o arquivo JSON
with open('filtered_products.json', 'r', encoding='utf-8') as f:
    products = json.load(f)

# Gerar o arquivo SQL
with open('dump.sql', 'w', encoding='utf-8') as sql_file:
    # Escrever o comando CREATE TABLE
    sql_file.write("""CREATE TABLE food_item (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    calories DOUBLE PRECISION NOT NULL,
    protein DOUBLE PRECISION NOT NULL,
    carbohydrates DOUBLE PRECISION NOT NULL,
    fat DOUBLE PRECISION NOT NULL,
    unit VARCHAR(3) NOT NULL CHECK (unit IN ('g', 'ml'))
);

""")

    # Gerar comandos INSERT para cada produto
    for product in products:
        # Escapar aspas simples nos nomes
        name = product['nome_e_marca'].replace("'", "''")
        
        sql = f"""INSERT INTO food_item (name, calories, protein, carbohydrates, fat, unit)
VALUES ('{name}', {product['calorias']}, {product['proteinas']}, {product['carboidratos']}, {product['gorduras']}, '{product['unidade']}');\n"""
        
        sql_file.write(sql)

print("Arquivo dump.sql gerado com sucesso!")