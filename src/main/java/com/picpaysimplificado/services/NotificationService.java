package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message){
        String email = user.getEmail();
        NotificationDto notificationRequest = new NotificationDto(email, message);

        System.out.println("Notificação enviada");
//        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notificationRequest, String.class);
//
//        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
//            System.out.println("Erro ao enviar notificação");
//            throw new RuntimeException("Serviço de notificação está fora do ar");
//        }
    }
}
