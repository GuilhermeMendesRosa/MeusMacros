package br.com.roselabs.ai_meus_macros.services;

import br.com.roselabs.ai_meus_macros.data.Food;
import br.com.roselabs.ai_meus_macros.data.Transcript;
import br.com.roselabs.ai_meus_macros.dtos.ChooseDTO;
import br.com.roselabs.ai_meus_macros.dtos.FoodDTO;
import br.com.roselabs.ai_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.ai_meus_macros.util.MeusMacrosPrompts;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);
    private static final Gson gson = new Gson();

    private final OpenAiChatModel chatModel;
    private final OpenAiEmbeddingModel embeddingModel;

    public AIService(@Value("${spring.ai.openai.api-key}") String openAiApiKey) {
        OpenAiApi openAiApi = new OpenAiApi(openAiApiKey);
        this.chatModel = buildChatModel(openAiApi);
        this.embeddingModel = buildEmbeddingModel(openAiApi);
    }

    public List<Food> convertTranscriptToList(Transcript transcript) {
        logger.info("Convertendo transcrição em lista de alimentos...");

        String transcriptFood = transcript.getTranscriptFood();
        String initialProcessingPrompt = String.format(MeusMacrosPrompts.INITIAL_PROCESSING, transcriptFood);

        logger.debug("Enviando prompt de processamento inicial: {}", initialProcessingPrompt);
        ChatResponse initialProcessingResponse = chatModel.call(new Prompt(initialProcessingPrompt));
        String initialProcessingResult = initialProcessingResponse.getResult().getOutput().getContent();

        JsonArray jsonElements = gson.fromJson(initialProcessingResult, JsonArray.class);
        List<Food> foods = new ArrayList<>();

        for (JsonElement jsonElement : jsonElements) {
            String normalizationPrompt = String.format(MeusMacrosPrompts.NORMALIZATION_PROCESSING, gson.toJson(jsonElement));
            logger.debug("Enviando prompt de normalização: {}", normalizationPrompt);
            ChatResponse normalizationResponse = chatModel.call(new Prompt(normalizationPrompt));
            String normalizationResult = normalizationResponse.getResult().getOutput().getContent();

            Food food = gson.fromJson(normalizationResult, Food.class);
            foods.add(food);
        }

        for (Food food : foods) {
            if (!food.getIsGenericFood()) continue;

            logger.info("Gerando embedding para alimento genérico: {}", food.getName());
            food.setEmbedding(generateEmbedding(food.getName()));
        }

        logger.info("Processo de conversão concluído. {} alimentos identificados.", foods.size());
        return foods;
    }

    public List<FoodItemDTO> findFoodItems(List<FoodDTO> foodDTOs) {
        logger.info("Buscando itens de alimentos com base nos dados fornecidos...");

        String calculatePrompt = String.format(MeusMacrosPrompts.CALCULATE, gson.toJson(foodDTOs));
        logger.debug("Enviando prompt de cálculo: {}", calculatePrompt);

        ChatResponse calculateResponse = chatModel.call(new Prompt(calculatePrompt));
        String calculateResult = calculateResponse.getResult().getOutput().getContent();

        List<FoodItemDTO> foodItemDTOS = gson.fromJson(
                calculateResult,
                new TypeToken<List<FoodItemDTO>>() {
                }.getType()
        );

        logger.info("Busca concluída. {} itens de alimentos retornados.", foodItemDTOS.size());
        return foodItemDTOS;
    }

    public List<Double> generateEmbedding(String foodName) {
        logger.debug("Gerando embedding para: {}", foodName);
        EmbeddingResponse embeddingResponse = embeddingModel.embedForResponse(List.of(foodName));
        float[] output = embeddingResponse.getResult().getOutput();

        List<Double> embeddings = new ArrayList<>();
        for (float value : output) {
            embeddings.add((double) value);
        }

        logger.debug("Embedding gerado com sucesso para: {}", foodName);
        return embeddings;
    }

    public String choose(ChooseDTO chooseDTO) {
        logger.info("Realizando escolha baseada nas opções fornecidas...");

        String json = gson.toJson(chooseDTO);
        String choosePrompt = String.format(MeusMacrosPrompts.CHOOSE, json);
        logger.debug("Enviando prompt de escolha: {}", choosePrompt);

        ChatResponse chooseResponse = chatModel.call(new Prompt(choosePrompt));
        String chooseResult = chooseResponse.getResult().getOutput().getContent();

        logger.info("Escolha realizada com sucesso.");
        return chooseResult;
    }

    private OpenAiChatModel buildChatModel(OpenAiApi openAiApi) {
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(0.1)
                .maxTokens(300)
                .build();

        logger.info("Modelo de chat OpenAI configurado com sucesso.");
        return new OpenAiChatModel(openAiApi, chatOptions);
    }

    private OpenAiEmbeddingModel buildEmbeddingModel(OpenAiApi openAiApi) {
        OpenAiEmbeddingOptions embeddingOptions = OpenAiEmbeddingOptions.builder()
                .model("text-embedding-ada-002")
                .user("user-6")
                .build();

        logger.info("Modelo de embeddings OpenAI configurado com sucesso.");
        return new OpenAiEmbeddingModel(
                openAiApi,
                MetadataMode.EMBED,
                embeddingOptions,
                RetryUtils.DEFAULT_RETRY_TEMPLATE);
    }

}
