package ru.t1.gladun.kafkalesson.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.gladun.kafkalesson.dto.ClientDto;
import ru.t1.gladun.kafkalesson.entity.Client;
import ru.t1.gladun.kafkalesson.kafka.KafkaClientProducer;
import ru.t1.gladun.kafkalesson.repository.ClientRepository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    private final KafkaClientProducer kafkaClientProducer;
    private final ClientRepository repository;

    @Transactional
    public void registerClients(List<Client> clients) {
        log.info("Registering clients... FullName of Client is {}", clients.get(0).getFullName());
        repository.saveAll(clients)
                .stream()
                .forEach(kafkaClientProducer::send);
    }


    public List<ClientDto> parseJson() {
        ObjectMapper mapper = new ObjectMapper();

        ClientDto[] clients;
        try {
            clients = mapper.readValue(new File("src/main/resources/MOCK_DATA.json"), ClientDto[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Arrays.asList(clients);
    }
}
