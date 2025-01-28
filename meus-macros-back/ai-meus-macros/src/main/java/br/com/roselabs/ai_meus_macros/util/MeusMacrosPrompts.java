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
            - ❗ **NUNCA deixe alimentos sem quantidade.** Se o usuário disser "arroz", retorne "200 g de arroz".  
            - ❗ **Priorize os padrões do repertório.** Exemplo: "salada de alface" → "100 g de salada de alface".  
            - ❗ **Se não houver referência no repertório, estime com base em medidas similares** (ex: "uma fruta" → use "130 g de maçã").  
            
            ### **Repertórios (mesma estrutura fornecida)**  
            [...]  
            
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
