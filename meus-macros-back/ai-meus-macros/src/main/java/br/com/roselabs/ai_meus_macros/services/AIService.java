package br.com.roselabs.ai_meus_macros.services;

import br.com.roselabs.ai_meus_macros.data.Food;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIService {

    private final OpenAiChatModel chatModel;
    private final OpenAiEmbeddingModel embeddingModel;

    public AIService(@Value("${spring.ai.openai.api-key}") String openAiApiKey) {
        if (openAiApiKey == null || openAiApiKey.isBlank()) {
            throw new IllegalArgumentException("OpenAI API Key não configurada ou está vazia.");
        }

        OpenAiApi openAiApi = new OpenAiApi(openAiApiKey);
        this.chatModel = new OpenAiChatModel(openAiApi, buildChatOptions());
        this.embeddingModel = new OpenAiEmbeddingModel(
                openAiApi,
                MetadataMode.EMBED,
                OpenAiEmbeddingOptions.builder()
                        .model("text-embedding-ada-002")
                        .user("user-6")
                        .build(),
                RetryUtils.DEFAULT_RETRY_TEMPLATE);
    }

    public List<Food> convertTranscriptToList(String transcript) {
        String promptStr = """
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
        ChatResponse response = chatModel.call(new Prompt(promptStr));
        String result = response.getResult().getOutput().getContent();

        return parseFoodListFromJson(result);
    }

    private OpenAiChatOptions buildChatOptions() {
        return OpenAiChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(0.4)
                .maxTokens(200)
                .build();
    }

    private List<Food> parseFoodListFromJson(String json) {
        return new Gson().fromJson(json, new TypeToken<List<Food>>() {
        }.getType());
    }

    private float[] getEmbeddingsByString(String str) {
        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(str));
        float[] output = embeddingResponse.getResult().getOutput();
        return output;
    }

}
