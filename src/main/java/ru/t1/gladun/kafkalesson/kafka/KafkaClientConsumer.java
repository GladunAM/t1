package ru.t1.gladun.kafkalesson.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.gladun.kafkalesson.dto.ClientDto;
import ru.t1.gladun.kafkalesson.entity.Client;
import ru.t1.gladun.kafkalesson.service.ClientService;
import ru.t1.gladun.kafkalesson.util.ClientMapper;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaClientConsumer {
    private final ClientService clientService;

    @KafkaListener(id = "${kafka.groupId}",
            topics = "${kafka.clientRegistrationTopic}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload List<ClientDto> messageList, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.debug("Client consumer: Обработка новых сообщений");
        try {
            List<Client> clients = messageList.stream().map(dto -> {
                dto.setFullName(dto.getLastName() + " " + dto.getFirstName());
                return ClientMapper.toEntity(dto);
            }).toList();
            clientService.registerClients(clients);
        } finally {
            ack.acknowledge();
        }

        log.debug("Client consumer: записи обработаны");
    }
}
