package br.com.roselabs.ai_meus_macros.services;

import br.com.roselabs.ai_meus_macros.data.Food;
import br.com.roselabs.ai_meus_macros.util.MeusMacrosPrompts;
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
        String prompt = MeusMacrosPrompts.CONVERT_TRANSCRIPT_TO_LIST + transcript;
        ChatResponse response = chatModel.call(new Prompt(prompt));
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
