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
            - A unidade de medida dos alimentos deve ser sempre convertida para **gramas (g)** ou **mililitros (ml)**.
            - Caso o alimento esteja descrito em medidas como "folhas", "fatias", "copos", "bifes", ou similares, você deve estimar e converter essas medidas para gramas (g) ou mililitros (ml).
            - **Sempre** leve em conta os cortes das carnes; se não for informado, escolha o corte mais provável para a receita, sem exceções.
            - O nome do alimento deve ser formatado de maneira padronizada e compatível com a **Tabela Brasileira de Composição de Alimentos (TACO)**, para facilitar a pesquisa nessa tabela.
            - A quantidade (portions) deve ser um número inteiro correspondente ao valor convertido para gramas ou mililitros.
            - Considere sempre molhos e formas de preparo; por exemplo, se eu usar manteiga para untar a frigideira, deve contar como alimento, assim como no caso de um bife acebolado, contando a cebola e o azeite.
            
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
            
            **Dicas para formatação do nome do alimento:**
            - Sempre utilize nomes que estejam de acordo com os padrões da tabela TACO. 
            - Evite marcas ou descrições subjetivas. 
            - Exemplo: use "Arroz branco cozido" ao invés de "Arroz da marca XYZ".
            
            Certifique-se de estimar medidas com base em quantidades comuns. Por exemplo:
            - Uma fatia de pão geralmente pesa 25g.
            - Um copo de água ou leite equivale a 250ml.
            - Uma folha de alface pesa cerca de 10g.
            
            Não forneça explicações ou texto adicional. O retorno deve ser **somente** o JSON no formato especificado acima.
            
            Transcrição recebida: 
            """;

}
