package br.com.roselabs.gateway_meus_macros.aws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Configuration
public class AwsEcsFargateConfig {

    private static final Logger log = LoggerFactory.getLogger(AwsEcsFargateConfig.class);

    private static final String AWS_API_VERSION = "v2";
    private static final String AWS_METADATA_URL = "http://169.254.170.2/" + AWS_API_VERSION + "/metadata";

    @Value("${server.port}")
    private int port;

    @Value("${spring.application.name}")
    private String appName;

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) throws JsonProcessingException {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        config.setNonSecurePort(port);
        config.setAppname(appName);
        config.setVirtualHostName(appName);
        config.setSecureVirtualHostName(appName);

        try {
            String ecsFargateMetadata = readEcsMetadata();
            EcsTaskMetadata ecsTaskMetadata = mapper.readValue(ecsFargateMetadata, EcsTaskMetadata.class);
            String ipAddress = findContainerPrivateIP(ecsTaskMetadata);


            log.info("OVERRIDE IP ADDRESS TO:  {}", ipAddress);
            config.setIpAddress(ipAddress);
            config.setHostname(ipAddress);
            config.setInstanceId(ipAddress + ":" + config.getSecureVirtualHostName() + ":" + config.getNonSecurePort());
            log.info("ECS FARGATE METADATA:  {}", ecsFargateMetadata);

        } catch (Exception e) {
            log.error("Something went wrong when reading ECS metadata: {}", e.getMessage());

        }

        log.info("EurekaInstanceConfigBean:  {}", mapper.writeValueAsString(config));
        log.info("EurekaInstanceConfigBean.getVirtualHostName():  {}", mapper.writeValueAsString(config.getVirtualHostName()));

        return config;
    }

    private String readEcsMetadata() throws Exception {
        URL obj = new URL(AWS_METADATA_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        StringBuilder response = new StringBuilder();
        try {
            con.setRequestMethod("GET");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
        } finally {
            con.disconnect();
        }
        return response.toString();
    }

    private String findContainerPrivateIP(EcsTaskMetadata metadata) {
        if (null != metadata) {
            for (Container container : metadata.getContainers()) {
                boolean found = container.getName().toLowerCase().contains(appName);
                if (found) {
                    Network network = container.getNetworks()[0];
                    return network.getIPv4Addresses()[0];
                }
            }
        }
        return "";
    }
}