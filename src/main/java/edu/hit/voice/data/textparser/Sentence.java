package edu.hit.voice.data.textparser;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Sentence {
    private String dialogId;
    private String newReply;
    private String newClient;
    private LocalDateTime replyTime;
    private LocalDateTime queryTime;

    @Override
    public String toString() {
        return "Sentence{" +
                "dialogId='" + dialogId + '\'' +
                ", newReply='" + newReply + '\'' +
                ", newClient='" + newClient + '\'' +
                ", replyTime=" + replyTime +
                ", queryTime=" + queryTime +
                '}';
    }
}
