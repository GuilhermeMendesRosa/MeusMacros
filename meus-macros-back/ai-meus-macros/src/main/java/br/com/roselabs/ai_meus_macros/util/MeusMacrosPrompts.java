package br.com.roselabs.ai_meus_macros.util;

public class MeusMacrosPrompts {

    public static final String FIRST_STEP = """
            **Prompt para Substituição de Medidas Abstratas em Registros Alimentares** 
            
            **Instruções:** 
            Ao receber a transcrição do que o usuário comeu, siga estas etapas: 
            
            1. **Identificar Medidas Abstratas:** 
               Verifique se o texto contém termos subjetivos de quantidade (ex: "uma colher", "um punhado", "uma xícara", "uma tigela", "um copo", "uma fatia"). 
            
            2. **Consultar Repertório de Equivalências:** 
               Utilize a tabela abaixo para substituir medidas abstratas por valores aproximados em **gramas (g)** ou **mililitros (ml)**. 
               - **Bebidas (líquidos):** Sempre use **ml**. 
               - **Alimentos sólidos/semilíquidos:** Sempre use **g**. 
            
               | Medida Abstrata      | Equivalente Realista (g/ml) | 
               |-----------------------|------------------------------| 
               | Colher (sopa)         | 15g (sólidos) ou 15ml (líquidos) | 
               | Colher (chá)          | 5g (sólidos) ou 5ml (líquidos)  | 
               | Xícara (chá)          | 240ml (líquidos) ou 200g (sólidos) | 
               | Tigela pequena         | 150g (sólidos) ou 300ml (líquidos) | 
               | Tigela média           | 300g (sólidos) ou 500ml (líquidos) | 
               | Copo (padrão)         | 200ml (líquidos)              | 
               | Punhado               | 30g (sólidos)                | 
               | Fatia (fina)          | 25g (ex: pão, queijo)        | 
               | Porção (genérica)     | 100g (sólidos)               | 
               | Garfada               | 10g (sólidos)                | 
            
            3. **Aplicar Substituições:** 
               - **Exemplo de Entrada:** 
                 *"Comi um punhado de amêndoas e tomei uma xícara de leite."* 
               - **Saída:** 
                 *"Comi 30g de amêndoas e tomei 240ml de leite."* 
            
            4. **Regras Adicionais:** 
               - **Não altere** medidas já em **g/ml** (ex: "200g de frango"). 
               - Mantenha itens sem medida (ex: "1 maçã"). 
               - Em caso de ambiguidade (ex: "uma tigela de sopa"), priorize **ml para líquidos**. 
            
            **Objetivo Final:** 
            Garantir que todas as quantidades subjetivas sejam traduzidas para valores concretos (g/ml), facilitando análises nutricionais precisas sem alterar dados já estruturados. 
            
            **Exemplo Completo:** 
            - **Entrada do Usuário:** 
              *"Café da manhã: 2 colheres de aveia, uma fatia de queijo e um copo de suco."* 
            - **Saída Processada:** 
              *"Café da manhã: 30g de aveia, 25g de queijo e 200ml de suco."* 
            
            Use este padrão para garantir consistência e clareza! 🍎⚖️
            
            Não forneça explicações ou texto adicional. O retorno deve ser **somente** o texto aprimorado.
            
            E abaixo está o JSON gerado a partir da transcrição da fala:
                        %s
            """;

    public static final String SECOND_STEP = """
            "Você receberá uma transcrição dos alimentos consumidos pelo usuário. Siga estas instruções rigorosamente: 
            
            1. **Identificação de Medidas:** 
               - Analise cada item da lista para verificar se possui medidas (ex: *"100g de arroz"*, *"1 copo de leite"*). 
               - **Se não houver nenhuma medida**, adicione uma estimativa realista em **gramas (g)** para sólidos ou **mililitros (ml)** para líquidos, baseando-se em porções habituais (ex: *"arroz"* → *"150g de arroz"*; *"suco de laranja"* → *"200ml de suco de laranja"*). 
            
            2. **Preservação de Medidas Existentes:** 
               - **Se o item já tiver uma medida** (mesmo em unidades diferentes, como *"1 xícara"* ou *"2 colheres"*), **converta-a para g ou ml** antes de incluí-la na lista final (ex: *"1 xícara de leite"* → *"240ml de leite"*). 
            
            3. **Evidenciar forma de preparo:** 
               - **Se o item já tiver uma forma de preparo informado pelo usuário, pode esar essa. Caso não tenha, suponha a forma de preparo mais comum (grelhado, cozido, etc). Se o usuário falar que é cru, informe que é cru**. 
             
            4. **Tente seguir o padrão da tabela TACO:** 
               - **Na tabela TACO os itens normalmente utilizam virgula no seu nome, tente segui esse padrão**. 
               - Exemplo:
                    Frango, peito, com pele, assado
                    Arroz, tipo 1, cozido
                    Carne, bovina, acém, sem gordura, cru
                    Carne, bovina, capa de contra-filé, com gordura, crua
                    Carne, bovina, capa de contra-filé, sem gordura, grelhada
                    Limão, galego, suco
                    Pescada, filé, cru
                    Pescada, filé, frito 
            
            5. **Formato da Resposta:** 
               - Retorne **apenas a lista padronizada**, com cada item no formato: 
                 `- [Quantidade em g/ml] de [alimento]`. 
               - Exemplo: 
                 `- 150g de arroz` 
                 `- 240ml de leite` 
            
            **Regras Obrigatórias:** 
            - Nunca altere o nome do alimento ou adicione comentários. 
            - Se a transcrição original já estiver em g/ml, mantenha os valores originais. 
            - Garanta que **todos os itens** tenham medidas em **g ou ml** na resposta final." 
            
            ---
            
            **Exemplo de Uso:** 
            *Transcrição do usuário:* 
            "Arroz, feijão, 1 filé de frango, suco de laranja e 2 colheres de sopa de azeite." 
            
            *Resposta gerada:* 
            - 150g de arroz 
            - 120g de feijão 
            - 130g de filé de frango 
            - 200ml de suco de laranja 
            - 30ml de azeite 
            
            Isso garante clareza e padronização, mesmo em casos com unidades não métricas ou ausência de medidas.\
            
            Não forneça explicações ou texto adicional. O retorno deve ser **somente** o texto aprimorado.
            
            E abaixo está o JSON gerado a partir da transcrição da fala:
                        %s
            """;

    public static final String THIRD_STEP = """
            Você vai receber uma string com a transcrição de uma pessoa falando sobre o que ela comeu. A partir dessa transcrição, transforme as informações em um **JSON** no seguinte formato:
            
            [
              {
                "name": "Nome do alimento",
                "unit": "g" ou "ml",
                "inNatura": true ou false,
                "portions": Quantidade do alimento em unidades inteiras
              }
            ]
            
            **Exemplo**
            Transcrição: "Hoje, a pessoa preparou um filé de peixe grelhado (180 g de filé de tilápia), acompanhado de 200 g de arroz e 100 g de salada de alface. A salada foi temperada com 15 ml de azeite e 15 ml de suco de limão.
            De sobremesa comi 100g de chocolate"
            Resposta:
            [
               {
                 "name": "Filé de tilápia grelhado",
                 "unit": "g",
                 "inNatura":true,
                 "portions": 180
               },
               {
                 "name": "Arroz",
                 "unit": "g",
                 "inNatura":true,
                 "portions": 200
               },
               {
                 "name": "Salada de alface",
                 "unit": "g",
                 "inNatura":true,
                 "portions": 100
               },
               {
                 "name": "Azeite",
                 "unit": "ml",
                 "inNatura":true,
                 "portions": 15
               },
               {
                 "name": "Suco de limão",
                 "unit": "ml",
                 "inNatura":true,
                 "portions": 15
               },
               {
                 "name": "Chocolate",
                 "unit": "g",
                 "inNatura":false,
                 "portions": 15
               }
             ]
            
            Não forneça explicações ou texto adicional. O retorno deve ser **somente** o JSON no formato especificado acima.
            
            Transcrição recebida: %s
            """;

    public static final String CALCULATE = """
            Atue como um nutricionista especializado em análise de alimentos. Você receberá uma lista de alimentos no formato JSON com nome, unidade (g/ml) e quantidade de porções.\s
            
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
            

            Não forneça explicações ou texto adicional. O retorno deve ser **somente** o json no formato esperado. Não coloque nenhum "```json" no retorno
                        JSON BASE: %s
            """;

    public static final String CHOOSE = """
            Com base no userInput, qual é o o foodItem mais coerente?
            ME RETORNE SOMENTE O ID e nada mais.
            
            %s
            """;


}
