package com.ambashtalk.devops.cron.tasks;

import com.ambashtalk.devops.repository.RefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Service
@Transactional
public class RefreshTokenPurgeTask {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenPurgeTask(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Async
    @Scheduled(cron = "${devops.cron.tasks.refresh-token-purge-task.schedule}")
    public void purgeExpired() {
        log.debug("Running task: {}", RefreshTokenPurgeTask.class);
        refreshTokenRepository.deleteAllExpiredSince(Timestamp.from(Instant.now()));
    }
}
