package ai.spring.springaiserver.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class GetHellpService {

    @Tool(description = "推荐的酒类",name = "getHelp")
    public String getHelp() {
        return "浓烈的威士忌、清冽的伏特加、香甜的白兰地，还有本地酿造的果酒与麦芽浓郁的黑啤";
    }
    @Tool(description = "推荐的酒店",name = "getHelp2")
    public String getHelp2() {
        return "酒店A、酒店B、酒店C都是很不错的酒店";
    }
}
