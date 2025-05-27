package ai.spring.springaiclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping
public class OpenAiChatClientController {

    private final ChatClient openAiChatClient;

    public OpenAiChatClientController(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory,
            ToolCallbackProvider tool) {

        this.openAiChatClient = chatClientBuilder
                .defaultSystem(
                        """
                                你是一个客服
                                
                                """
                )
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory)
                )
                .defaultTools(
                        tool.getToolCallbacks()
                )
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .topP(0.7)
                                .build()
                ).build();


    }

    /**
     * ChatClient 流式响应
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestParam("message") String message) {
        return openAiChatClient.prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .stream()
                .content();
    }

    /**
     * 非流式响应
     */
    @GetMapping("/chat-sync")
    public String chat(@RequestParam("message") String message) {
        return openAiChatClient.prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .call()
                .content();
    }
}