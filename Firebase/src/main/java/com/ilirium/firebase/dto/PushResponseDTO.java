package com.ilirium.firebase.dto;

import java.util.List;

/**
 *
 * @author dpoljak
 */
public class PushResponseDTO {

    private Long failure;
    private Long multicast_id;
    private Long success;
    private Long canonical_ids;
    private List<Results> results;

    public Long getFailure() {
        return failure;
    }

    public void setFailure(Long failure) {
        this.failure = failure;
    }

    public Long getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(Long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public Long getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(Long canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "PushResponseDTO{" + "failure=" + failure + ", multicast_id=" + multicast_id + ", success=" + success + ", canonical_ids=" + canonical_ids + ", results_list_size=" + (results != null ? String.valueOf(results.size()) : "null") + '}';
    }

    public static class Results {

        private String message_id;
        private String registration_id;
        private String error;

        public String getMessage_id() {
            return message_id;
        }

        public void setMessage_id(String message_id) {
            this.message_id = message_id;
        }

        public String getRegistration_id() {
            return registration_id;
        }

        public void setRegistration_id(String registration_id) {
            this.registration_id = registration_id;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
