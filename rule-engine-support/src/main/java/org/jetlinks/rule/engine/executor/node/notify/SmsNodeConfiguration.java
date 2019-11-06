package org.jetlinks.rule.engine.executor.node.notify;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.rule.engine.api.model.NodeType;
import org.jetlinks.rule.engine.executor.node.RuleNodeConfig;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SmsNodeConfiguration implements RuleNodeConfig {

    private String senderId;

    private String templateId;

    private String text;

    private List<String> sendTo;

    @Override
    public NodeType getNodeType() {
        return NodeType.PEEK;
    }

    @Override
    public void setNodeType(NodeType nodeType) {

    }

    public Mono<Boolean> send(SmsSender emailSender, Map<String, Object> context) {
        if (StringUtils.hasText(templateId)) {
            return emailSender.sendTemplate(templateId, context, sendTo);
        }
        return emailSender.send(text, context, sendTo);
    }

    @Override
    public void validate() {
        Assert.hasText(senderId, "senderId can not be empty");
        Assert.notEmpty(sendTo, "sendTo can not be empty");
        Assert.isTrue(!(StringUtils.isEmpty(templateId) && StringUtils.isEmpty(text)), "text or templateId can not be empty");
    }
}
