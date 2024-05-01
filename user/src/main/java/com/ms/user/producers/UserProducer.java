package com.ms.user.producers;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(UserModel userModel) {
        for(int i=0; i< 10; i++) {
            var emailDto = new EmailDto();
            emailDto.setUserId(userModel.getId());
            //emailDto.setEmailTo(userModel.getEmail());
            emailDto.setEmailTo(userModel.getEmail());
            emailDto.setSubject(i+1 + "° Cadastro realizado com sucesso!");
            emailDto.setText(userModel.getName() + ", seja bem vindo(a)! \n" +
                    "Agradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma!");
            rabbitTemplate.convertAndSend("", routingKey, emailDto);
        }
    }
}
