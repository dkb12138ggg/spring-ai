package ai.spring.springaiserver.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Tool(description = "推荐的测试网站",name = "getTest")
    public String getTest() {
        return "www.test.com,www.test2.com,www,test3.com";

    }
}
