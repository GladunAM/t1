package ru.t1.gladun.kafkalesson.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.gladun.kafkalesson.config.KafkaConfigProperties;
import ru.t1.gladun.kafkalesson.dto.ClientDto;
import ru.t1.gladun.kafkalesson.kafka.KafkaClientProducer;
import ru.t1.gladun.kafkalesson.service.ClientService;

import java.util.List;

@RestController
public class ClientController {
    private final ClientService clientService;
    private final KafkaClientProducer kafkaClientProducer;
    private String topic;

    public ClientController(KafkaConfigProperties kafkaConfigProperties, ClientService clientService, KafkaClientProducer kafkaClientProducer) {
        this.clientService = clientService;
        this.kafkaClientProducer = kafkaClientProducer;
        this.topic = kafkaConfigProperties.getClientRegistrationTopic();
    }


    @GetMapping(value = "/parse")
    public void parseSource() {
        List<ClientDto> clientDtos = clientService.parseJson();
        clientDtos.forEach(dto -> kafkaClientProducer.sendTo(topic, dto));
    }
}
