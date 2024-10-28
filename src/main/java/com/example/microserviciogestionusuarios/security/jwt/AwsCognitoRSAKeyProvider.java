package com.example.microserviciogestionusuarios.security.jwt;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.example.microserviciogestionusuarios.security.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class AwsCognitoRSAKeyProvider implements RSAKeyProvider {
    private final URL awsStoreUrl;
    private final JwkProvider provider;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${aws.cognito.jwk}")
    private String jwkUrl;
    public AwsCognitoRSAKeyProvider(String awsCognitoRegion, String identityPoolUrl) {
        String jwkUrl = this.jwkUrl;
        logger.info("AwsCognitoRSAKeyProvider jwkUrl="+jwkUrl);
        String url = String.format(jwkUrl, awsCognitoRegion, identityPoolUrl);
        try {
            awsStoreUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(String.format("Invalid URL provided, URL=%s", url));
        }
        provider = new JwkProviderBuilder(awsStoreUrl).build();
    }

    @Override
    public RSAPublicKey getPublicKeyById(String kid) {
        try {
            return (RSAPublicKey) provider.get(kid).getPublicKey();
        } catch (JwkException e) {
            throw new RuntimeException(String.format("Failed to get JWT kid=%s from aws_kid_store_url=%s", kid, awsStoreUrl));
        }
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return null;
    }

    @Override
    public String getPrivateKeyId() {
        return null;
    }
}