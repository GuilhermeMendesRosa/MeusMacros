package br.com.roselabs.ai_meus_macros.util;

public class MeusMacrosPrompts {

    public static final String CONVERT_TRANSCRIPT_TO_LIST = """
            Você vai receber uma string com a transcrição de uma pessoa falando sobre o que ela comeu. A partir dessa transcrição, transforme as informações em um **JSON** no seguinte formato:
            
            [
              {
                "name": "Nome do alimento",
                "unit": "g" ou "ml",
                "portions": Quantidade do alimento em unidades inteiras
              }
            ]
            
            **Instruções:**
            1. **Unidades de medida**
               - Converta todas as unidades de medida para **gramas (g)** ou **mililitros (ml)**, sempre que possível.
            
            2. **Medidas estimadas**
               - Para alimentos descritos em medidas como "folhas", "fatias", "copos", "rodelas", "colheres", "xícaras", "bifes", "grãos", "punhados", "dentes", "porções", "pitadas", "tabletes", "gomos", "cubos", "lascas", "filés", "gotas", "pedaços", "toras", "bolas", "fios", "lâminas", "costelas", "quadrados", "galhos", "talos", "pacotes", "poucas" ou similares, estime e converta com base nos valores de referência abaixo:
               - No caso de vegetais mais pesados (acima de 80g), o usuário provavelmente não irá falar a medida, nesse não considere que ele irá comer inteiro, considere que ele comeu alguma fração.
            ---
            
            **MEDIDAS COMUNS PARA ALIMENTOS**
            
            **Grãos, cereais e massas**
            - **Arroz cru (1 colher de sopa):** 20g
            - **Arroz cozido (1 colher de sopa):** 25g
            - **Arroz cozido (1 colher de restaurante):** 60g
            - **Arroz cozido (1 colher de grande):** 60g
            - **Arroz cozido (1 xícara):** 150g
            - **Feijão cozido (1 concha média):** 60g
            - **Macarrão cru (1 xícara):** 80g
            - **Macarrão cozido (1 concha média):** 90g
            - **Aveia em flocos (1 colher de sopa):** 10g
            - **Farinha de trigo (1 colher de sopa):** 10g
            - **Cuscuz cozido (1 colher de sopa):** 20g
            - **Quinoa cozida (1 xícara):** 185g
            
            **Carnes e proteínas**
            - **Bife médio (patinho ou alcatra):** 120g
            - **Peito de frango grelhado (1 filé):** 100g
            - **Carne moída (1 porção padrão):** 100g
            - **Filé de peixe grelhado (tilápia ou similar):** 120g
            - **Linguiça toscana (1 unidade):** 100g
            - **Ovo médio (com casca):** 50g
            - **Ovo grande (sem casca):** 60g
            - **Hambúrguer bovino (1 unidade padrão):** 120g
            
            **Laticínios e derivados**
            - **Queijo muçarela (1 fatia):** 20g
            - **Queijo parmesão ralado (1 colher de sopa):** 10g
            - **Queijo cottage (1 colher de sopa):** 25g
            - **Leite integral (1 copo):** 200ml
            - **Iogurte natural (1 copo pequeno):** 170g
            - **Creme de leite (1 colher de sopa):** 15g
            - **Requeijão (1 colher de sopa):** 20g
            
            **Pães e massas**
            - **Pão francês (1 unidade média):** 50g
            - **Pão de forma (1 fatia):** 25g
            - **Pão integral (1 fatia):** 30g
            - **Massa de pizza crua (1 pedaço médio):** 100g
            - **Torrada (1 unidade):** 7g
            
            **Frutas e vegetais**
            - **Banana média (1 unidade):** 120g
              - Metade de uma banana: **60g**
            
            - **Maçã média (1 unidade):** 130g
              - Um quarto da maçã: **32g**
            
            - **Abacate (1 colher de sopa):** 15g
              - Meio abacate médio: **200g**
            
            - **Tomate médio (1 unidade):** 120g
              - Meio tomate médio: **60g**
              - Um quarto de tomate: **30g**
            
            - **Cenoura média (1 unidade):** 80g
              - Meia cenoura média: **40g**
              - Ralada (1 colher de sopa): **10g**
            
            - **Batata inglesa (1 unidade média):** 120g
              - Meia batata inglesa: **60g**
            
            - **Cebola média (1 unidade):** 70g
              - Meia cebola média: **35g**
              - Um quarto de cebola média: **17g**
              - Fatiada (1 colher de sopa): **7g**
            
            - **Alface (1 folha):** 10g
              - Duas folhas de alface: **20g**
            
            - **Brócolis cozido (1 xícara):** 90g
              - Metade de uma xícara: **45g**
              - Pequeno buquê de brócolis: **15g**
            
            - **Espinafre cozido (1 colher de sopa):** 20g
              - Meio maço de espinafre cru: **150g**
            
            - **Milho verde (1 colher de sopa):** 15g
              - Uma espiga média de milho: **100g**
            
            - **Abóbora cozida (1 xícara):** 205g
              - Meia xícara de abóbora: **102g**
            **Oleaginosas e sementes**
            - **Castanha-do-pará (1 unidade):** 5g
            - **Amêndoas (1 colher de sopa):** 10g
            - **Pasta de amendoim (1 colher de sopa):** 15g
            - **Sementes de linhaça (1 colher de sopa):** 10g
            
            **Bebidas**
            - **Água (1 copo):** 250ml
            - **Suco natural (1 copo):** 200ml
            - **Café preto (1 xícara):** 50ml
            - **Refrigerante (1 lata):** 350ml
            - **Vinho tinto (1 taça):** 150ml
            
            **Doces e sobremesas**
            - **Chocolate ao leite (1 barra pequena):** 25g
            - **Brigadeiro (1 unidade):** 15g
            - **Sorvete (1 bola):** 60g
            - **Bolo de chocolate (1 fatia média):** 80g
            
            **Outros ingredientes e temperos**
            - **Azeite (1 colher de sopa):** 13g
            - **Manteiga (1 colher de sopa):** 10g
            - **Molho de tomate (1 colher de sopa):** 15g
            - **Ketchup (1 colher de sopa):** 12g
            - **Maionese (1 colher de sopa):** 15g
            - **Sal (1 colher de chá):** 5g
            - **Açúcar (1 colher de sopa):** 12g
            
            ---
            
            3. **Cortes de carne**
               - Identifique o corte da carne descrito na receita. Caso não seja informado, escolha o corte mais comum para a preparação mencionada:
                 - **Exemplo:** Para bifes, opte por alcatra ou patinho (120-150g cada). Para ensopados, utilize acém ou músculo.
            
            4. **Padronização dos nomes dos alimentos**
               - Use os nomes dos alimentos de acordo com a **Tabela Brasileira de Composição de Alimentos (TACO)**, para facilitar a pesquisa e a padronização.
            
            5. **Quantidades (portions)**
               - Registre as quantidades como números inteiros, já convertidos para gramas (g) ou mililitros (ml).
            
            6. **Molhos e preparos**
               - Inclua todos os ingredientes utilizados no preparo, incluindo temperos, óleos e acompanhamentos. Exemplos:
                 - **Manteiga para untar:** 5g por camada fina.
                 - **Cebola usada em um refogado:** 70g por unidade média.
                 - **Azeite para refogar:** 10-15g por colher de sopa.
            
            Certifique-se de utilizar os valores padrão para medidas e quantidades mais comuns, ajustando conforme necessário para o contexto da receita.
            
            **Exemplo 1:**
            Transcrição: "Comi 3 fatias de pão integral, uma folha de alface, 2 copos de água e um bife de 200 gramas."
            Resposta:
            [
              {
                "name": "Pão integral",
                "unit": "g",
                "portions": 75
              },
              {
                "name": "Alface crespa",
                "unit": "g",
                "portions": 10
              },
              {
                "name": "Água potável",
                "unit": "ml",
                "portions": 500
              },
              {
                "name": "Carne bovina grelhada",
                "unit": "g",
                "portions": 200
              }
            ]
            
            **Exemplo 2:**
            Transcrição: "Tomei um copo de leite e comi uma maçã."
            Resposta:
            [
              {
                "name": "Leite integral",
                "unit": "ml",
                "portions": 250
              },
              {
                "name": "Maçã vermelha",
                "unit": "g",
                "portions": 180
              }
            ]
            
            Não forneça explicações ou texto adicional. O retorno deve ser **somente** o JSON no formato especificado acima.
            
            Transcrição recebida: 
            """;

}
