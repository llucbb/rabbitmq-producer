package org.llucbb.rabbitmqproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.llucbb.rabbitmqproducer.utility.RabbitmqQueue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitmqScheduler {

    private final RabbitmqProxyService rabbitmqProxyService;

    @Scheduled(fixedDelay = 90000)
    public void sweepDirtyQueues() {
        try {
            var dirtyQueues = rabbitmqProxyService.getAllQueues().stream()
                    .filter(RabbitmqQueue::isDirty)
                    .collect(Collectors.toList());
            dirtyQueues.forEach(q -> log.info("Queue {} has {} unprocessed messages", q.getName(), q.getMessages()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
