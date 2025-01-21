import csv

# Caminho do CSV de entrada
input_csv = r'C:\Code\MeusMacros\taco\alimentos.csv'

# Caminho do arquivo de saída SQL
output_sql = r'C:\Code\MeusMacros\taco\dump.sql'

# Função para tentar converter uma string para float, tratando erros como 'NA'
def safe_float(value):
    try:
        return float(value)
    except ValueError:
        return 0

# Função para gerar o insert SQL
def generate_insert(row):
    # Definindo as colunas relevantes para os inserts
    name = row['Descrição dos alimentos']
    
    # Ajustando os valores para 1g (dividindo por 100)
    calories = safe_float(row['Energia..kcal.']) / 100
    protein = safe_float(row['Proteína..g.']) / 100
    carbohydrates = safe_float(row['Carboidrato..g.']) / 100
    fat = safe_float(row['Lipídeos..g.']) / 100
    
    unit = 'g'  # Vamos assumir que todos os dados são em gramas, mas isso pode ser ajustado conforme necessário

    # Gerando o insert
    insert = f"INSERT INTO food_item (name, calories, protein, carbohydrates, fat, unit) VALUES ('{name}', {calories}, {protein}, {carbohydrates}, {fat}, '{unit}');\n"
    return insert

# Abrindo o arquivo CSV e lendo os dados
with open(input_csv, newline='', encoding='utf-8') as csvfile:
    reader = csv.DictReader(csvfile, delimiter=',')
    
    # Abrindo o arquivo de saída SQL
    with open(output_sql, 'w', encoding='utf-8') as sqlfile:
        # Escrevendo o cabeçalho de criação da tabela no arquivo SQL
        sqlfile.write("""
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

        """)
        
        # Gerando os inserts para cada alimento no CSV
        for row in reader:
            insert = generate_insert(row)
            sqlfile.write(insert)

print("Script executado com sucesso! O arquivo dump.sql foi gerado.")
