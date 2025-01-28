package br.com.roselabs.ai_meus_macros.services;

import br.com.roselabs.ai_meus_macros.data.Food;
import br.com.roselabs.ai_meus_macros.util.MeusMacrosPrompts;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
        JsonObject body = new Gson().fromJson(transcript, JsonObject.class);
        JsonElement transcriptFood = body.get("transcriptFood");

        String toImprovePrompt = String.format(MeusMacrosPrompts.IMPROVE_TEXT, transcriptFood);
        ChatResponse toImproveResponse = chatModel.call(new Prompt(toImprovePrompt));
        String toImproveResult = toImproveResponse.getResult().getOutput().getContent();

        String toConvertPrompt = String.format(MeusMacrosPrompts.CONVERT_TRANSCRIPT_TO_LIST, toImproveResult);
        ChatResponse toConvertResponse = chatModel.call(new Prompt(toConvertPrompt));
        String toConvertResult = toConvertResponse.getResult().getOutput().getContent();

        List<Food> foods = new Gson().fromJson(
                toConvertResult,
                new TypeToken<List<Food>>() {
                }.getType()
        );

        for (Food food : foods) {
            List<Double> embedding = this.generateEmbedding(food.getName());
            food.setEmbedding(embedding);
        }

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
                .maxTokens(300)
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
