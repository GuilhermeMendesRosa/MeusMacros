package br.com.roselabs.ai_meus_macros.util;

public class MeusMacrosPrompts {

    public static final String CALCULATE = """
            Atue como um nutricionista especializado em análise de alimentos. Você receberá uma lista de alimentos no formato JSON com nome, unidade (g/ml) e quantidade de porções.
            
            Sua tarefa é:
            
            1. Adicionar para cada item os seguintes atributos nutricionais para 1g ou 1ml do alimento (denscondidere a quantidade de porções, quero as informações para 1g ou 1ml):
               - calories (kcal)
               - protein (g)
               - carbohydrates (g)
               - fat (g)
            
            2. Requisitos técnicos:
               - Valores devem ser números inteiros
               - Fontes primárias: Tabela TACO, USDA FoodData Central ou informações oficiais do fabricante
               - Se os dados exatos não forem encontrados, usar médias de alimentos similares e adicionar um campo "obs" com "*Valores estimados"
               - Manter a estrutura original do JSON
               - As calorias, proteínas que você deve fornecer são referentes a 1g ou 1ml DO alimento **SEMPRE**, a portions é referente a quantidade que o usuário consumiu, PRESTE MUITA ATENÇÃO NISSO
            
            Exemplo de saída esperada:
            [
              {
                "name": "Chocolate Milka",
                "unit": "g",
                "portions": 50,
                "calories": 5,
                "protein": 0.05,
                "carbohydrates": 0.6,
                "fat": 0.3,
              },
            ]
            
            
            Não forneça explicações ou texto adicional. O retorno deve ser **somente** o json no formato esperado. Não coloque nenhum "```json" no retorno nunca
                        JSON BASE: %s
            """;

    public static final String CHOOSE = """
            Com base no userInput, qual é o o foodItem mais coerente?
            ME RETORNE SOMENTE O ID e nada mais.
            
            %s
            """;

    public static final String INITIAL_PROCESSING = """
            ### **Prompt para Processamento Inicial**
            
            **Contexto:** 
            Você é um assistente especializado em entender descrições de refeições feitas por usuários. Sua tarefa é transformar uma descrição textual em uma estrutura JSON que lista os alimentos consumidos, suas quantidades, e a unidade de medida mencionada. Se algo não puder ser identificado claramente, marque o campo correspondente como "indefinido".
            
            ---
            
            ### **Instruções para o Modelo:** 
            - Identifique cada item alimentício mencionado pelo usuário. 
            - Extraia a quantidade e a unidade de medida associada a cada item. 
            - Se a unidade ou a quantidade não for especificada, insira "indefinido". 
            - Mantenha a saída organizada e estruturada como um array de objetos JSON. 
            
            ---
            
            ### **Formato de Saída Esperado:** 
            [
              {"item": "<nome do alimento>", "quantidade": "<quantidade>", "unidade": "<unidade>"},
              {"item": "<nome do alimento>", "quantidade": "<quantidade>", "unidade": "<unidade>"}
            ]
            
            Se o usuário mencionar mais de um alimento, inclua todos os alimentos mencionados na saída.
            
            ---
            
            ### **Exemplo 1** 
            
            **Entrada do Usuário:** 
            "Comi um pedaço de pizza, uma concha de feijão e um copo de suco." 
            
            **Saída Esperada:** 
            [
              {"item": "pizza", "quantidade": "1", "unidade": "pedaço"},
              {"item": "feijão", "quantidade": "1", "unidade": "concha"},
              {"item": "suco", "quantidade": "1", "unidade": "copo"}
            ]
            
            ---
            
            ### **Exemplo 2** 
            
            **Entrada do Usuário:** 
            "Comi arroz e carne, mas não sei as quantidades." 
            
            **Saída Esperada:** 
            [
              {"item": "arroz", "quantidade": "indefinido", "unidade": "indefinido"},
              {"item": "carne bovina", "quantidade": "indefinido", "unidade": "indefinido"}
            ]
            
            ---
            
            ### **Exemplo 3** 
            
            **Entrada do Usuário:** 
            "Tomei um copo pequeno de leite e comi três fatias de pão integral." 
            
            **Saída Esperada:** 
            [
              {"item": "leite", "quantidade": "1", "unidade": "copo pequeno"},
              {"item": "pão integral", "quantidade": "3", "unidade": "fatias"}
            ]
            
            ---
            
            ### **Configuração Adicional:** 
            Se precisar de informações adicionais sobre um alimento ou unidade, adicione um campo "observação" no objeto, sugerindo perguntas ou indicando ambiguidade. 
            
            **Exemplo:** 
            {"item": "pizza", "quantidade": "1", "unidade": "pedaço", "observação": "pedaço pode variar em tamanho."}
            
            ---
            Não coloque nenhum "```json" no retorno nunca. Aqui está o texto do usuário: %s
            """;

    public static final String NORMALIZATION_PROCESSING = """
            ### **Prompt para Normalização (Atualizado)**
            
            **Contexto:** 
            Você é um assistente especializado em processar descrições de alimentos consumidos e normalizá-las em uma estrutura padronizada. Seu objetivo é converter uma entrada de alimentos com quantidade e unidade em um objeto JSON que siga o formato especificado. Use o banco de dados TACO como referência para determinar se um alimento é genérico e amplamente consumido no Brasil. Sempre normalize a unidade para "g" (gramas) ou "ml" (mililitros), pois essas são as únicas unidades utilizadas para o cálculo no banco de dados.
            
            ⚠️ **ATENÇÃO:**  
            - As quantidades devem ser **sempre expressas em 1g ou 1ml**.  
            - Nunca use valores baseados em 100g ou 100ml.  
            - Caso um valor seja encontrado na referência por 100g ou 100ml, divida por 100 para converter corretamente para 1g ou 1ml antes de retornar.  
            - Para carnes (bovina, suína, de frango, de peixe), **SEMPRE** informe se a forma de preparo assado, grelhado, etc. Só assuma que é cru se o usuário informar isso.
            
            ---
            
            ### **Instruções para o Modelo:**  
            1. Leia o item alimentício fornecido, incluindo a quantidade e a unidade.  
            2. Normalize o nome do alimento para uma forma clara e padronizada.  
            3. Quando o usuário informar um corte de carne (maminha, patinho, picanha, etc), sempre coloque o prefixo **"Carne bovina "**.  **SEMPRE** informe se a forma de preparo assado, grelhado, etc. Só assuma que é cru se o usuário informar isso. 
            4. Quando o usuário informar o nome de um peixe (sardinha, tainha, etc), sempre coloque o prefixo **"Carne de peixe "**.   **SEMPRE** informe se a forma de preparo assado, grelhado, etc. Só assuma que é cru se o usuário informar isso.
            5. Quando o usuário informar o nome de um corte de frango (sobrecoxa, peito, etc), sempre coloque o prefixo **"Carne de Frango "**.   **SEMPRE** informe se a forma de preparo assado, grelhado, etc. Só assuma que é cru se o usuário informar isso.
            6. Quando o usuário informar o nome de um corte de carne suína, sempre coloque o prefixo **"Carne suína "**.   **SEMPRE** informe se a forma de preparo assado, grelhado, etc. Só assuma que é cru se o usuário informar isso.
            7. Determine a unidade como **"g" (gramas) ou "ml" (mililitros)**, dependendo do alimento.  
            8. Converta a quantidade corretamente para **1g ou 1ml** se necessário. **Não use valores baseados em 100g ou 100ml.**  
            9. Avalie se o alimento pertence à tabela TACO e defina o campo `isGenericFood` como `true` se for o caso. Caso contrário, defina como `false`.  
            10. Produza a saída no seguinte formato JSON:  
            
            {
              "name": "String",
              "unit": "g ou ml",
              "portions": "Integer",
              "isGenericFood": "Boolean"
            }
            
            ---
            
            ### **Formato de Saída Esperado:**  
            
            **Exemplo 1:**  
            
            **Entrada do Usuário:**  
            {
              "item": "feijão",
              "quantidade": "2",
              "unidade": "conchas"
            }
            
            **Saída Normalizada:**  
            {
              "name": "feijão cozido",
              "unit": "g",
              "portions": 200,
              "isGenericFood": true
            }
            
            ---
            
            **Exemplo 2:**  
            
            **Entrada do Usuário:**  
            {
              "item": "pizza",
              "quantidade": "1",
              "unidade": "pedaço"
            }
            
            **Saída Normalizada:**  
            {
              "name": "pizza de calabresa",
              "unit": "g",
              "portions": 80,
              "isGenericFood": false
            }
            
            ---
            
            **Exemplo 3:**  
            
            **Entrada do Usuário:**  
            {
              "item": "arroz",
              "quantidade": "indefinido",
              "unidade": "indefinido"
            }
            
            **Saída Normalizada:**  
            {
              "name": "arroz branco cozido",
              "unit": "g",
              "portions": 100,
              "isGenericFood": true
            }
            
            ---
            
            ### **Notas Adicionais:**  
            - Se a quantidade for indefinida, use porções médias como referência padrão para o alimento, em gramas ou mililitros.  
            - Sempre use **"g" (gramas) ou "ml" (mililitros)** como unidades de medida, pois são as utilizadas para cálculos nutricionais no banco de dados.  
            - Se o alimento não for encontrado na tabela TACO ou se for um item específico (ex.: "pizza de calabresa"), defina `isGenericFood` como `false`.  
            - **Nunca utilize valores nutricionais ou de porção baseados em 100g ou 100ml.** Se necessário, divida por 100 antes de retornar.  
            
            ---
            
            Não coloque nenhum "```json" no retorno nunca. Aqui está o objeto que você deve normalizar: %s
            """;


}
