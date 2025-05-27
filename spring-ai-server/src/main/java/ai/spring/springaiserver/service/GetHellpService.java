package ai.spring.springaiserver.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class GetHellpService {

    @Tool(description = "推荐的酒类",name = "getHelp")
    public String getHelp() {
        return "浓烈的威士忌、清冽的伏特加、香甜的白兰地，还有本地酿造的果酒与麦芽浓郁的黑啤";
    }
}
