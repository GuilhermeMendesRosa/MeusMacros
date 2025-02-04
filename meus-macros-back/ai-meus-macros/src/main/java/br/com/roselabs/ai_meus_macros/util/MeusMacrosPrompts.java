package br.com.roselabs.ai_meus_macros.util;

public class MeusMacrosPrompts {

    public static final String CALCULATE = """
            Atue como um nutricionista especializado em análise de alimentos. Você receberá a descrição de uma refeição mal formatada.
            
            Sua tarefa é:
            
            1. Para cada item da refeição, adicione os seguintes atributos nutricionais:
               - calories (kcal)
               - protein (g)
               - carbohydrates (g)
               - fat (g)
            
            2. Requisitos técnicos:
               - Os valores devem ser números inteiros.
               - Utilize como fontes primárias: Tabela TACO, USDA FoodData Central ou informações oficiais do fabricante.
               - Se os dados exatos não forem encontrados, utilize médias de alimentos similares.
               - Mantenha a estrutura original do JSON.
               - **Importante:** A unidade de medida deve ser sempre "g" (gramas) ou "ml" (mililitros), sem exceção.
            
            Exemplo de saída esperada (lista de JSON):
            [
              {
                "name": "Chocolate Milka",
                "unit": "g",
                "portions": 50,
                "calories": 5,
                "protein": 0.05,
                "carbohydrates": 0.6,
                "fat": 0.3
              },
              {
                "name": "Biscoito Recheado",
                "unit": "g",
                "portions": 50,
                "calories": 5,
                "protein": 0.05,
                "carbohydrates": 0.6,
                "fat": 0.3
              }
            ]
            
            Não forneça explicações ou texto adicional. O retorno deve ser **somente** o JSON no formato esperado. JAMAIS adicione nenhum "```json" no retorno, lembre-se que ele é uma lista.
                        REFEIÇÃO: %s
            """;
}
