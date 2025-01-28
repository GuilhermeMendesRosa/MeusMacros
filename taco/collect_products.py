import requests
import time
import json
import os

BASE_URL = "https://world.openfoodfacts.org/country/brazil.json"
MAX_PAGE_SIZE = 100000
OUTPUT_FILE = "openfoodfacts_brazil.json"

# Cabeçalhos para simular um navegador
HEADERS = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36",
    "Accept": "application/json",
}


def load_existing_data(filename):
    """Carrega os dados existentes do arquivo JSON, se disponível."""
    if os.path.exists(filename):
        with open(filename, "r", encoding="utf-8") as file:
            try:
                data = json.load(file)
                return data if isinstance(data, list) else []
            except json.JSONDecodeError:
                print(f"Erro ao carregar os dados existentes do arquivo {filename}.")
                return []
    return []


def fetch_products(existing_products):
    """Busca produtos na API e adiciona apenas os novos produtos."""
    all_products = existing_products.copy()  # Carregar produtos existentes
    existing_ids = {product.get("_id") for product in existing_products}  # IDs existentes
    current_page = 100
    while True:
        print(f"Fetching page {current_page}...")

        try:
            response = requests.get(
                BASE_URL,
                params={"page": current_page, "page_size": MAX_PAGE_SIZE},
                headers=HEADERS,
                timeout=30,
                verify=False,  # Ignorar verificação SSL
            )

            if response.status_code == 429:
                print("Muitas requisições. Aguardando 60 segundos...")
                time.sleep(60)  # Pausa mais longa em caso de bloqueio
                continue

            if response.status_code != 200:
                print(f"Erro HTTP: {response.status_code}")
                print(f"Resposta do servidor: {response.text}")
                break

            data = response.json()

            # Adicionar produtos novos à lista
            for product in data.get("products", []):
                if product.get("_id") not in existing_ids:
                    all_products.append(product)
                    existing_ids.add(product.get("_id"))

            if current_page >= 236:
                break

            current_page += 1
            time.sleep(5)  # Pausa de 5 segundos entre as requisições

        except ValueError as e:
            print(f"Erro ao converter resposta para JSON: {e}")
            print(f"Resposta bruta: {response.text}")
            break

        except requests.exceptions.RequestException as e:
            print(f"Erro ao buscar a página {current_page}: {e}")
            print("Tentando novamente em alguns segundos...")
            time.sleep(10)

    return all_products


def save_to_json_file(data, filename):
    """Salva os dados no arquivo JSON."""
    with open(filename, "w", encoding="utf-8") as file:
        json.dump(data, file, ensure_ascii=False, indent=4)


if __name__ == "__main__":
    print("Iniciando o processo de coleta de dados...")

    # Carregar dados existentes do arquivo
    existing_products = load_existing_data(OUTPUT_FILE)
    print(f"Produtos existentes carregados: {len(existing_products)}")

    # Buscar novos produtos
    products = fetch_products(existing_products)
    print(f"Coleta concluída. Total de produtos agora: {len(products)}")

    # Salvar os dados atualizados no arquivo
    print(f"Salvando os dados no arquivo '{OUTPUT_FILE}'...")
    save_to_json_file(products, OUTPUT_FILE)
    print("Arquivo salvo com sucesso!")
