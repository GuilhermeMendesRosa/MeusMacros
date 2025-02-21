package br.com.roselabs.gateway_meus_macros.aws;

public class Health {

    private String status;

    private String statusSince;

    private String output;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusSince() {
        return statusSince;
    }

    public void setStatusSince(String statusSince) {
        this.statusSince = statusSince;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}