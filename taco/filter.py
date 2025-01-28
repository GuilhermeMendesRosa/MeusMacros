import json

# Nome do arquivo de entrada e saída
input_file = "openfoodfacts_brazil.json"
output_file = "filtered_products.json"

# Função principal
def filter_products(input_file, output_file):
    # Carregar o arquivo JSON
    with open(input_file, 'r', encoding='utf-8') as f:
        data = json.load(f)

    filtered_products = []

    # Percorrer a lista de produtos
    for product in data:
        try:
            # Verificar se todas as propriedades necessárias estão presentes, exceto a marca
            if (
                "product_name" in product and
                "nutriments" in product and
                "energy-kcal_100g" in product["nutriments"] and
                "proteins_100g" in product["nutriments"] and
                "carbohydrates_100g" in product["nutriments"] and
                "fat_100g" in product["nutriments"] and
                "serving_size" in product
            ):
                # Definir unidade de medida
                serving_size = product["serving_size"]
                unit = "ml" if "ml" in serving_size.lower() else "g"

                # Pegar o nome do produto e a marca (se existir)
                product_name = product["product_name"]
                brand = product.get("brands", "")  # Marca pode ser ausente

                # Se a marca contiver vírgulas, pegar o primeiro valor
                if brand:
                    brand = brand.split(",")[0]

                # Concatenar nome e marca
                name_and_brand = f"{product_name} - {brand}" if brand else product_name

                # Extrair os campos desejados e dividir por 100
                filtered_products.append({
                    "nome_e_marca": name_and_brand,
                    "calorias": product["nutriments"]["energy-kcal_100g"] / 100,
                    "proteinas": product["nutriments"]["proteins_100g"] / 100,
                    "carboidratos": product["nutriments"]["carbohydrates_100g"] / 100,
                    "gorduras": product["nutriments"]["fat_100g"] / 100,
                    "unidade": unit
                })
        except KeyError:
            # Ignorar itens que não têm todos os campos necessários
            continue

    # Salvar os produtos filtrados no arquivo de saída
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(filtered_products, f, ensure_ascii=False, indent=4)

    print(f"Produtos válidos processados: {len(filtered_products)}")
    print(f"Arquivo gerado: {output_file}")

# Executar a função
filter_products(input_file, output_file)
