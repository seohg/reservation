package org.example.reservation.domain;


import org.example.reservation.api.dto.request.ApplyRequest;
import org.example.reservation.domain.domain.LectureSchedule;
import org.example.reservation.domain.repository.LectureHistoryRepository;
import org.example.reservation.domain.repository.LectureRepository;
import org.example.reservation.domain.repository.LectureScheduleRepository;
import org.example.reservation.domain.service.LectureService;
import org.example.reservation.infra.entity.LectureScheduleEntity;
import org.example.reservation.infra.jpaRepository.LectureHistoryJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("통합 테스트")
public class concurrencyTest {

    @Autowired
    private LectureService lectureService;
    @Autowired
    LectureHistoryJpaRepository lectureHistoryJpaRepository;
    private LectureHistoryRepository lectureHistoryRepository;

    private LectureRepository lectureRepository;
    private LectureScheduleRepository lectureScheduleRepository;
    public concurrencyTest(@Autowired LectureHistoryRepository lectureHistoryRepository,
                           @Autowired LectureRepository lectureRepository,
                           @Autowired LectureScheduleRepository lectureScheduleRepository) {
        this.lectureHistoryRepository = lectureHistoryRepository;
        this.lectureRepository = lectureRepository;
        this.lectureScheduleRepository = lectureScheduleRepository;
    }


    @Test
    @DisplayName("특강 예약 동시성 테스트")
    void concurrencyTest() throws InterruptedException {
        // Given
        int numThreads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        Long lectureId = 1L;
        String title = "title";

        // 특강 등록
        lectureScheduleRepository.addSpecialLecture(new LectureScheduleEntity(1L, 30L, LocalDateTime.of(2024, 6, 27, 0, 0, 0), "title", "tutor").toLectureSchedule());

        // 특강 신청 요청
        ApplyRequest request = new ApplyRequest(lectureId, title);

        // When
        for (int i = 0; i < numThreads; i += 1) {
            Long userId = i + 1L;
            executor.submit(() -> {
                try {
                    lectureService.reserve(userId, request);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        LectureSchedule lectureSchedule = lectureScheduleRepository.readById(lectureId);

        // Then
        assertThat(lectureSchedule.capacity()).isEqualTo(10);
    }

}

