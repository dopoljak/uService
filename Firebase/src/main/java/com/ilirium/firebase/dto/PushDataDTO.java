package com.ilirium.firebase.dto;

import java.util.HashMap;
import java.util.Map;

public class PushDataDTO {

    private String to;
    private Priority priority;
    private Map<String, String> data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public enum Priority {
        normal,
        high
    }

    public static final class Builder {

        private String to;
        private Priority priority;
        private Map<String, String> data = new HashMap<>();

        public Builder() {
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder addData(String key, String value) {
            this.data.put(key, value);
            return this;
        }

        public PushDataDTO build() {
            PushDataDTO dto = new PushDataDTO();
            dto.setData(data);
            dto.setTo(to);
            dto.setPriority(priority);
            return dto;
        }

    }
}
