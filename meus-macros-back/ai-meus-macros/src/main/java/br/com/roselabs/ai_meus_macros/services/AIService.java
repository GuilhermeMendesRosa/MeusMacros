package br.com.roselabs.ai_meus_macros.services;

import br.com.roselabs.ai_meus_macros.data.Food;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AIService {

    private final OpenAiChatClient chatClient;

    public List<Food> convertTranscriptToList(String transcript) {
        // Montar o prompt para o ChatGPT
        String prompt = """
                **Prompt:**
                
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
                - O nome do alimento deve ser formatado de maneira padronizada e compatível com a **Tabela Brasileira de Composição de Alimentos (TACO)**, para facilitar a pesquisa nessa tabela. Use nomes descritivos e genéricos encontrados na tabela, como "Arroz branco cozido", "Peito de frango grelhado", etc.
                - A quantidade (portions) deve ser um número inteiro correspondente ao valor convertido para gramas ou mililitros.
                
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
                
                Transcrição recebida: """ + transcript + """
                """;

        // Chamar a API do ChatClient e obter a resposta
        String response = chatClient.call(prompt);

        // Converter a resposta para uma lista de objetos Food
        Gson gson = new Gson();
        return gson.fromJson(response, new TypeToken<List<Food>>() {
        });
    }

}
