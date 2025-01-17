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
                    },
                    "portions": Quantidade do alimento em unidades inteiras
                  }
                ]
                
                - A unidade de medida dos alimentos será sempre em **gramas (g)** ou **mililitros (ml)**.
                - O nome do alimento deve ser uma descrição detalhada do que foi consumido.
                - A quantidade (portions) será um número inteiro, correspondente à porção informada.
                
                **Exemplo:**
                
                Se a pessoa disser: "Eu comi 150 gramas de arroz, 200 gramas de frango grelhado, uma banana de 120 gramas e bebi 200ml de leite", a resposta deve ser:
                
                [
                  {
                    "name": "Arroz branco cozido",
                    "unit": "g",
                    "portions": 150
                  },
                  {
                    "name": "Peito de frango grelhado",
                    "unit": "g",
                    "portions": 200
                  },
                  {
                    "name": "Banana prata",
                    "unit": "g",
                    "portions": 120
                  },
                  {
                    "name": "Leite integral",
                    "unit": "ml",
                    "portions": 200
                  }
                ]
                
                Não forneça explicações ou texto adicional. O retorno deve ser **somente** o JSON.
                A abbreviation deve ser **somente** g ou ml.
                
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
