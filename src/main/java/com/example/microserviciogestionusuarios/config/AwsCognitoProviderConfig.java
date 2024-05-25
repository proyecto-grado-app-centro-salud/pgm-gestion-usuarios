package com.example.microserviciogestionusuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class AwsCognitoProviderConfig {
    // @Value(value = "${aws.access.key}")
    private String accessKey="AKIA2UC277LJXGGEXZWJ";

    // @Value(value = "${aws.secret.key}")
    private String secretKey="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";

    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider(){
        BasicAWSCredentials awsCredentials= new BasicAWSCredentials(accessKey, secretKey);
        return AWSCognitoIdentityProviderClientBuilder
        .standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .withRegion("us-east-1")
        .build();
    }
}
