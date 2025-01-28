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
            
            **Exemplo**
            Transcrição: "Hoje, a pessoa preparou um filé de peixe grelhado (180 g de filé de tilápia), acompanhado de 200 g de arroz e 100 g de salada de alface. A salada foi temperada com 15 ml de azeite e 15 ml de suco de limão."
            Resposta:
            [
               {
                 "name": "Filé de tilápia grelhado",
                 "unit": "g",
                 "portions": 180
               },
               {
                 "name": "Arroz",
                 "unit": "g",
                 "portions": 200
               },
               {
                 "name": "Salada de alface",
                 "unit": "g",
                 "portions": 100
               },
               {
                 "name": "Azeite",
                 "unit": "ml",
                 "portions": 15
               },
               {
                 "name": "Suco de limão",
                 "unit": "ml",
                 "portions": 15
               }
             ]
            
            Não forneça explicações ou texto adicional. O retorno deve ser **somente** o JSON no formato especificado acima.
            
            Transcrição recebida: %s
            """;

    public static final String IMPROVE_TEXT = """  
            Transforme o texto transcrito a seguir em um texto estruturado **garantindo que TODOS os alimentos, ingredientes e acompanhamentos tenham quantidades explícitas em g ou ml**, mesmo que o usuário não as mencione. Siga rigorosamente estas regras:  
            
            1. **Quantidade obrigatória:** Se a quantidade não for fornecida, utilize os valores padrão dos repertórios abaixo.  
            2. **Substituição de medidas:** Converta termos como "colher", "concha" ou "porção" para g/ml usando as tabelas de equivalência.  
            3. **Cortes de carne:** Se o corte não for especificado, escolha o mais comum para o tipo de preparo.  
            
            **Texto Transcrito:** %s  
            ---  
            
            ### **Regras críticas**  
            - ❗ **NUNCA deixe alimentos sem quantidade.** Se o usuário disser "arroz"".  
            - ❗ **Priorize os padrões do repertório.** Exemplo: "salada de alface".  
            - ❗ **Se não houver referência no repertório, estime com base em medidas similares**.  
            - ❗ **No caso de comidas in natura explicite se é cozido, grelhado, ect (a não ser que o usuário explicite que é cru)**.  
            - ❗ **No caso de comidas não processadas, deixe tente seguir o padrão de nomes da tabela TACO**.  
            
            ### **Repertório de cortes de carne**
   
            #### **Bovinos:**
            - **Para grelhar ou fritar:** Contrafilé (150 g), Alcatra (150 g), Picanha (150 g)
            - **Para cozidos ou ensopados:** Acém (200 g), Músculo (200 g), Coxão duro (200 g)
            - **Para churrasco:** Picanha (150 g), Maminha (200 g), Costela (300 g com osso)
            - **Para moer:** Acém (100 g por porção), Patinho (100 g por porção)
   
            #### **Frango:**
            - **Para grelhar ou assar:** Filé de peito (200 g), Coxa (120 g), Sobrecoxa (150 g)
            - **Para sopas ou caldos:** Carcaça (300 g), Coxinha da asa (80 g cada)
   
            #### **Peixes e frutos do mar:**
            - **Para grelhar ou assar:** Filé de salmão (180 g), Filé de tilápia (150 g)
            - **Para moqueca ou ensopados:** Posta de peixe (200 g), Camarões médios (100 g)
   
            ---
   
            ### **Repertório de ingredientes comuns**
   
            #### **Óleos e gorduras:**
            - Óleo de soja, canola ou girassol: 10 ml (1 colher de sopa) por porção
            - Azeite de oliva: 15 ml (1 colher de sopa) por porção
            - Manteiga: 10 g (1 colher de sopa) por porção
   
            #### **Temperos básicos:**
            - Sal: 5 g (1 colher de chá) por porção
            - Pimenta-do-reino: 1 g (uma pitada) por porção
            - Alho: 5 g (1 dente médio) por porção
            - Cebola: 50 g (meia cebola média) por porção
            - Limão: 15 ml (suco de meio limão) por porção
   
            #### **Ervas e especiarias:**
            - Salsinha ou coentro: 5 g (1 colher de sopa picada)
            - Orégano: 1 g (uma pitada)
            - Cominho: 1 g (uma pitada)
            - Pimentão: 50 g (meio pimentão pequeno)
   
            #### **Ingredientes líquidos:**
            - Água: 240 ml (1 xícara de chá)
            - Vinagre: 10 ml (1 colher de sopa)
            - Leite: 240 ml (1 xícara de chá)
            - Molho de soja: 15 ml (1 colher de sopa)
   
            #### **Acompanhamentos comuns:**
            - Arroz: 200 g (1 prato cheio)
            - Feijão: 150 g (1 concha média)
            - Purê de batata: 200 g
            - Farofa: 80 g (4 colheres de sopa)
   
            ---
   
            ### **Repertório de medidas padrão**
   
            #### **Utensílios e porções comuns:**
            - **Colher de sopa:** 15 g (se sólido) ou 15 ml (se líquido)
            - **Colher de chá:** 5 g (se sólido) ou 5 ml (se líquido)
            - **Concha:** 100 ml (se líquido) ou 80 g (se sólido)
            - **Xícara de chá:** 240 ml
            - **Copo americano:** 200 ml
            - **Fatias de pão:** 30 g cada
            - **Pedaço de bolo:** 80 g
            - **Porção de salada (folhas e vegetais):** 100 g
            - **Prato de sopa:** 300 ml
   
            #### **Proteínas e carnes:**
            - **Bife:** 150 g
            - **Peito de frango:** 200 g
            - **Coxa de frango:** 120 g
            - **Filé de peixe:** 180 g
            - **Ovo:** 50 g cada
            - **Hambúrguer:** 120 g
            - **Fatias de presunto ou queijo:** 20 g cada
   
            #### **Frutas (unidade):**
            - **Maçã ou pera:** 150 g cada
            - **Banana:** 120 g cada
            - **Laranja:** 130 g cada
            - **Manga (pequena):** 200 g cada
            - **Uvas (um cacho pequeno):** 100 g
            - **Abacate (metade):** 200 g
            - **Fatias de melancia ou melão:** 200 g cada
   
            #### **Legumes e tubérculos (unidade ou porção):**
            - **Batata (pequena):** 100 g cada
            - **Cenoura:** 80 g cada
            - **Abobrinha ou berinjela:** 200 g cada
            - **Tomate:** 120 g cada
            - **Milho (espiga):** 150 g cada
   
            #### **Grãos e cereais:**
            - **Arroz (cozido):** 200 g (1 prato cheio)
            - **Feijão (cozido):** 150 g (1 concha média)
            - **Macarrão (cozido):** 200 g (1 prato cheio)
            - **Pipoca (estourada):** 20 g (1 xícara cheia)
            - **Aveia (em flocos):** 30 g (2 colheres de sopa)
   
            #### **Líquidos e bebidas:**
            - **Café ou chá:** 150 ml (uma xícara pequena)
            - **Suco natural:** 250 ml (1 copo grande)
            - **Leite:** 240 ml (1 xícara de chá)
            - **Água:** 250 ml (1 copo comum)
            - **Refrigerante:** 350 ml (1 lata)
   
            #### **Sobremesas e doces:**
            - **Pedaço de torta:** 100 g
            - **Bola de sorvete:** 50 g cada
            - **Barra de chocolate:** 25 g cada
            - **Brigadeiro:** 15 g cada
            - **Fatias de pudim:** 80 g
   
            #### **Lanches e snacks:**
            - **Bolacha (tipo água e sal):** 7 g cada
            - **Bolacha recheada:** 15 g cada
            - **Salgadinho (tipo chips):** 20 g (1 porção pequena)
            - **Sanduíche (com pão e recheio):** 150 g
   
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
           12g

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
            - **Açúcar (1 colher de sopa):** 
            
            **Entrada (exemplo):**  
            "Comi um bife com batata cozida e salada."  
            
            **Saída esperada:**  
            A pessoa consumiu um bife de contra-filé (150 g), 200 g de batata cozida e 100 g de salada de folhas verdes.  
            
            **LEMBRE-SE:**  
            - **Valores padrão são obrigatórios.**  
            - **NÃO use termos vagos como "um pouco" ou "porção"** – sempre converta para g/ml.  
            - **Se houver dúvida, opte pelo valor mais comum do repertório.**  
            
            Retorne **apenas** o texto estruturado, sem comentários ou markdown.  
            """;

    public static final String REFINEMENT = """
            Usando uma LLM, transcrevi a fala do usuário para um formato JSON estruturado.
            
            Segue o texto original da fala do usuário:
            
            %s
            
            E abaixo está o JSON gerado a partir da transcrição da fala:
            
            %s
            
            Por favor, revise minuciosamente o JSON, ajustando-o para corrigir quaisquer inconsistências. Em especial, preste atenção aos seguintes pontos:
            
            Quantidades extremas: Verifique valores que estejam muito altos ou baixos, garantindo que façam sentido dentro do contexto.
            Títulos ou categorias: Revise títulos ou chaves que possam não refletir com precisão o que foi dito pelo usuário. Ajuste se necessário, mas mantenha a estrutura original.
            Formato e estrutura: Certifique-se de que o formato do JSON e a indentação estejam corretos, mantendo a estrutura dada, sem adicionar texto ou explicações.
            
            O retorno deve ser **apenas** o JSON revisado, sem qualquer outra explicação ou alteração no formato.
            """;

}
