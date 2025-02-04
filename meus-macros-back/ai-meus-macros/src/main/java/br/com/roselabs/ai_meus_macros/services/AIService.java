package br.com.roselabs.ai_meus_macros.services;

import br.com.roselabs.ai_meus_macros.data.Transcript;
import br.com.roselabs.ai_meus_macros.dtos.FoodItemDTO;
import br.com.roselabs.ai_meus_macros.util.MeusMacrosPrompts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.MetadataMode;
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

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);
    private static final Gson gson = new Gson();

    private final OpenAiChatModel chatModel;

    public AIService(@Value("${spring.ai.openai.api-key}") String openAiApiKey) {
        OpenAiApi openAiApi = new OpenAiApi(openAiApiKey);
        this.chatModel = buildChatModel(openAiApi);
    }

    public List<FoodItemDTO> convertTranscriptToList(Transcript transcript) {
        logger.info("Buscando itens de alimentos com base nos dados fornecidos...");

        String calculatePrompt = String.format(MeusMacrosPrompts.CALCULATE, transcript.getTranscriptFood());
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

    private OpenAiChatModel buildChatModel(OpenAiApi openAiApi) {
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(0.1)
                .build();

        logger.info("Modelo de chat OpenAI configurado com sucesso.");
        return new OpenAiChatModel(openAiApi, chatOptions);
    }

}
