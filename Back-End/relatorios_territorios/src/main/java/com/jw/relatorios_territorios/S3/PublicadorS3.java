package com.jw.relatorios_territorios.S3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class PublicadorS3 {

    private static final S3Client S3_CLIENT = S3Client.builder().build();

    @Value("${BUCKET_NAME}")
    private String bucketName;

    public void salvarFotoPerfil(String objetoParaSalvar, String email, UUID id){
        if(this.bucketName.isEmpty()){
            throw new RuntimeException("Nome do bucket S3 vazio");
        }

        var key = "Imagens/Perfil/perfil_"+email+"_"+id+".txt";

        try{
            PutObjectRequest putObjectRequest = PutObjectRequest
                    .builder()
                    .bucket(this.bucketName)
                    .key(key)
                    .build();

            S3_CLIENT.putObject(putObjectRequest, RequestBody.fromBytes(objetoParaSalvar.getBytes(StandardCharsets.UTF_8)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getFotoPerfil(String email, UUID id){
        try{
            var key = "Imagens/Perfil/perfil_"+email+"_"+id+".txt";

            GetObjectRequest getObjectRequest = GetObjectRequest
                    .builder()
                    .bucket(this.bucketName)
                    .key(key)
                    .build();

            var objs = S3_CLIENT.getObjectAsBytes(getObjectRequest).asByteArray();
            return new String(objs, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
