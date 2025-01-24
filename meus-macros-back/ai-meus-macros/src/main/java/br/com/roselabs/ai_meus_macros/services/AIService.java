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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class AIService {

    private final OpenAiChatModel chatModel;
    private final OpenAiEmbeddingModel embeddingModel;

    public AIService(@Value("${spring.ai.openai.api-key}") String openAiApiKey) {
        OpenAiApi openAiApi = new OpenAiApi(openAiApiKey);
        this.chatModel = this.buildChatModel(openAiApi);
        this.embeddingModel = this.buildEmbeddingModel(openAiApi);
    }

    public List<Food> convertTranscriptToList(String transcript) throws InterruptedException {
        String prompt = MeusMacrosPrompts.CONVERT_TRANSCRIPT_TO_LIST + transcript;
        ChatResponse response = chatModel.call(new Prompt(prompt));
        String result = response.getResult().getOutput().getContent();

        List<Food> foods = new Gson().fromJson(
                result,
                new TypeToken<List<Food>>() {
                }.getType()
        );

        ExecutorService threadPool = Executors.newCachedThreadPool();

        for (Food food : foods) {
            threadPool.execute(() -> {
                List<Double> embedding = this.generateEmbedding(food.getName());
                food.setEmbedding(embedding);
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

        return foods;
    }

    public List<Double> generateEmbedding(String foodName) {
        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(foodName));
        float[] output = embeddingResponse.getResult().getOutput();
        List<Double> embeddings = new ArrayList<>();
        for (float value : output) {
            embeddings.add((double) value);
        }
        return embeddings;
    }

    private OpenAiChatModel buildChatModel(OpenAiApi openAiApi) {
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(0.1)
                .maxTokens(200)
                .build();

        return new OpenAiChatModel(openAiApi, chatOptions);
    }

    private OpenAiEmbeddingModel buildEmbeddingModel(OpenAiApi openAiApi) {
        OpenAiEmbeddingOptions embeddingOptions = OpenAiEmbeddingOptions.builder()
                .model("text-embedding-ada-002")
                .user("user-6")
                .build();

        return new OpenAiEmbeddingModel(
                openAiApi,
                MetadataMode.EMBED,
                embeddingOptions,
                RetryUtils.DEFAULT_RETRY_TEMPLATE);
    }

}
