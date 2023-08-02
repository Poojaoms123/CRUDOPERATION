package com.anervea.company.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
    private static final Logger LOG = LoggerFactory.getLogger(com.anervea.company.config.AmazonS3Config.class);

//    @Resource
//    private Environment env;

    @Value("ap-south-1")
    private String awsRegion;

    @Value("tattvanbucket-staging")
    private String awsS3Bucket;

    @Bean(name = "awsRegion")
    public Region getAWSPollyRegion() {
        return Region.getRegion(Regions.fromName(awsRegion));
    }

    @Bean(name = "awsS3Bucket")
    public String getAWSS3Bucket() {
        return awsS3Bucket;
    }

    @Bean(name = "awsCredentialsProvider")
    public AWSCredentialsProvider getSecret() {
//
//        String secretName = env.getProperty("spring.aws.secretsmanager.s3SecretName");
//        String region = env.getProperty("spring.aws.secretsmanager.region");
//
//// Create a Secrets Manager client
//        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
//                .withRegion(region)
//                .build();
//        LOG.info(client.toString());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String secret;
//        JsonNode secretsJson = null, decodedBinarySecret;
//        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
//                .withSecretId(secretName);
//        GetSecretValueResult getSecretValueResult = null;
//
//        try {
//            LOG.info("In Get  getSecretValueResult For S3 Config Block For Client Result");
//
//            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
//        } catch (DecryptionFailureException e) {
//            LOG.info(e.getMessage());
//        } catch (InternalServiceErrorException e) {
//            LOG.info(e.getMessage());
//
//        } catch (InvalidParameterException e) {
//            LOG.info(e.getMessage());
//
//        } catch (InvalidRequestException e) {
//            LOG.info(e.getMessage());
//
//        } catch (ResourceNotFoundException e) {
//            LOG.info(e.getMessage());
//        } catch (Exception e) {
//            LOG.info(e.getMessage());
//        }
//
//       // Decrypts secret using the associated KMS CMK.
//       // Depending on whether the secret is a string or binary, one of these fields will be populated.
//        if (getSecretValueResult != null) {
//            secret = getSecretValueResult.getSecretString();
//            try {
//                secretsJson = objectMapper.readTree(secret);
//            } catch (IOException e) {
//                LOG.error("Exception while retreiving secret values: " + e.getMessage());
//            }
//            String id = secretsJson.get("AWS_ACCESS_KEY_ID").textValue();
//            String keySecret = secretsJson.get("AWS_SECRET_ACCESS_KEY").textValue();
//
//            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(id, keySecret);
//            return new AWSStaticCredentialsProvider(awsCredentials);
//        } else {
            LOG.info("Secret Key Is Null For S3 Config may Using Local Server");
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials("AKIA4JIUQU4RAKK2LCMU",
                    "Kq/PA6FbVTZAuzx1D9YczBY7MAGM8VGxFseaG3Ep");
            return new AWSStaticCredentialsProvider(awsCredentials);
//        }
    }
}
