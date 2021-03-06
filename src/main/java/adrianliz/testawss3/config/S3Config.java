package adrianliz.testawss3.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
  @Value("${aws.access_key_id}")
  private String accessKeyId;

  @Value("${aws.secret_access_key}")
  private String accessSecretKey;

  @Value("${aws.s3.region}")
  private String region;

  @Bean
  public AmazonS3 getMockClient() {
    BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, accessSecretKey);
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withEndpointConfiguration(
            new AwsClientBuilder.EndpointConfiguration(
                "http://localhost:9090/", Region.US_Standard.getFirstRegionId()))
        .build();
  }
}
