package br.com.roselabs.ai_meus_macros.services;

import br.com.roselabs.ai_meus_macros.data.Food;
import br.com.roselabs.ai_meus_macros.dtos.FoodDTO;
import br.com.roselabs.ai_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.ai_meus_macros.util.MeusMacrosPrompts;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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

    //TODO refatorar classe

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

        String inicialProcessingPrompt = String.format(MeusMacrosPrompts.INICIAL_PROCESSING, transcriptFood);
        ChatResponse inicialProcessingResponse = chatModel.call(new Prompt(inicialProcessingPrompt));
        String inicialProcessingResult = inicialProcessingResponse.getResult().getOutput().getContent();
        JsonArray jsonElements = new Gson().fromJson(inicialProcessingResult, JsonArray.class);

        List<Food> foods = new ArrayList<>();

        for (JsonElement jsonElement : jsonElements) {
            String normalizationPrompt = String.format(MeusMacrosPrompts.NORMALIZATION_PROCESSING, new Gson().toJson(jsonElement));
            ChatResponse normalizationResponse = chatModel.call(new Prompt(normalizationPrompt));
            String normalizationResult = normalizationResponse.getResult().getOutput().getContent();

            Food food = new Gson().fromJson(normalizationResult, Food.class);
            foods.add(food);
        }

        for (Food food : foods) {
            if (food.getIsGenericFood()) {
                List<Double> embedding = this.generateEmbedding(food.getName());
                food.setEmbedding(embedding);
            }
        }

        return foods;
    }

    public List<FoodItemDTO> findFoodItems(List<FoodDTO> foodDTOs) {
        Gson gson = new Gson();


        String toImprovePrompt = String.format(MeusMacrosPrompts.CALCULATE, gson.toJson(foodDTOs));
        ChatResponse toImproveResponse = chatModel.call(new Prompt(toImprovePrompt));
        String toImproveResult = toImproveResponse.getResult().getOutput().getContent();

        List<FoodItemDTO> foodItemDTOS = new Gson().fromJson(
                toImproveResult,
                new TypeToken<List<FoodItemDTO>>() {
                }.getType()
        );

        return foodItemDTOS;
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

    public String choose(String json) {
        String toImprovePrompt = String.format(MeusMacrosPrompts.CHOOSE, json);
        ChatResponse toImproveResponse = chatModel.call(new Prompt(toImprovePrompt));
        String toImproveResult = toImproveResponse.getResult().getOutput().getContent();

        return toImproveResult;
    }
}
