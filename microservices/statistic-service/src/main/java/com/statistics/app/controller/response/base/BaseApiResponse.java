package com.statistics.app.controller.response.base;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.StringJoiner;

@Getter
@Setter
public class BaseApiResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final class Builder {
        private String id;

        private Long timestamp;

        private boolean success = true;

        private String responseCode;

        private String responseMessage;

        private String correlationId;

        public BaseApiResponse build() {
            BaseApiResponse response = new BaseApiResponse();

            response.id = this.id;
            response.timestamp = this.timestamp;
            response.success = this.success;
            response.responseCode = this.responseCode;
            response.responseMessage = this.responseMessage;
            response.correlationId = this.correlationId;

            return response;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withResponseCode(String responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder withResponseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
            return this;
        }

        public Builder withSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder withTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withCorrelationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }
    }

    @NotEmpty
    private String id;

    @NotNull
    private Long timestamp = System.currentTimeMillis();

    private boolean success = true;

    private String responseCode;

    private String responseMessage;

    private String correlationId = MDC.get("CID");

    public BaseApiResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public BaseApiResponse(String id, boolean success) {

        this(id, success, null);
    }

    public BaseApiResponse(String id, boolean success, String responseCode) {
        this(id, success, responseCode, null);
    }

    public BaseApiResponse(String id, boolean success, String responseCode, String responseMessage) {
        this.id = id;
        this.success = success;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;

    }

    @Override
    public String toString() {
        //@formatter:off
        return new StringJoiner(", ", BaseApiResponse.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("success=" + success)
                .add("responseCode='" + responseCode + "'")
                .add("responseMessage='" + responseMessage + "'")
                .add("correlationId='" + correlationId + "'")
                .toString();
        //@formatter:on
    }
}
