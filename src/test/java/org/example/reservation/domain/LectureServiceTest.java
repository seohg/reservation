package org.example.reservation.domain;

import org.example.reservation.api.dto.request.ApplyRequest;
import org.example.reservation.domain.domain.LectureHistory;
import org.example.reservation.domain.domain.LectureSchedule;
import org.example.reservation.domain.repository.LectureHistoryRepository;
import org.example.reservation.domain.repository.LectureRepository;
import org.example.reservation.domain.repository.LectureScheduleRepository;
import org.example.reservation.domain.service.LectureService;
import org.example.reservation.infra.entity.LectureScheduleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class LectureServiceTest {
    @Autowired
    private LectureService lectureService;

    private LectureHistoryRepository lectureHistoryRepository;

    private LectureRepository lectureRepository;
    private LectureScheduleRepository lectureScheduleRepository;
    public LectureServiceTest(@Autowired LectureHistoryRepository lectureHistoryRepository,
                              @Autowired LectureRepository lectureRepository,
                              @Autowired LectureScheduleRepository lectureScheduleRepository) {
        this.lectureHistoryRepository = lectureHistoryRepository;
        this.lectureRepository = lectureRepository;
        this.lectureScheduleRepository = lectureScheduleRepository;
    }

    @Test
    @DisplayName("[성공] 모든 강의 조회")
    public void getAllLecturesSuccessTest() {
        // Given
        LectureSchedule lectureSchedule = lectureScheduleRepository.addSpecialLecture(new LectureScheduleEntity(1L, 30L, LocalDateTime.of(2024, 6, 27, 0, 0, 0), "title", "tutor").toLectureSchedule());

        // When
        List<LectureSchedule> lectureSchedules = lectureService.getLectures();

        // Then
        assertThat(lectureSchedules.get(0).lectureTitle()).isEqualTo(lectureSchedule.lectureTitle());
        assertThat(lectureSchedules.get(0).lectureTutor()).isEqualTo(lectureSchedule.lectureTutor());
    }

    @Test
    @DisplayName("[성공] 특강 신청 테스트")
    void reserveLectureSuccessTest() {

        lectureScheduleRepository.addSpecialLecture(new LectureScheduleEntity(1L, 30L, LocalDateTime.of(2024, 6, 27, 0, 0, 0), "title", "tutor").toLectureSchedule());
        // Given
        ApplyRequest applyRequest = new ApplyRequest(1L, "title");

        // When
        LectureHistory lectureHistory = lectureService.reserve(1L,applyRequest);

        // Then
        assertThat(applyRequest.lectureTitle()).isEqualTo(lectureHistory.lectureTitle());
    }
    @Test
    @DisplayName("[실패] 아직 오픈하지 않은 특강 테스트")
    void notOpenFailTest() {
        // Given
        LectureSchedule lectureSchedule = lectureScheduleRepository.addSpecialLecture(new LectureScheduleEntity(1L, 30L, LocalDateTime.of(2030, 6, 27, 0, 0, 0), "title", "tutor").toLectureSchedule());

        // When && Then
        assertThrows(RuntimeException.class, () -> {
            lectureScheduleRepository.applyLecture(lectureSchedule);
        });
    }

    @Test
    @DisplayName("[실패] 남은 좌석 부족")
    void outOfCapacityFailTest() {
        // Given
        LectureSchedule lectureSchedule = lectureScheduleRepository.addSpecialLecture(new LectureScheduleEntity(1L, 0L, LocalDateTime.of(2023, 6, 27, 0, 0, 0), "title", "tutor").toLectureSchedule());
        // When && Then
        assertThrows(RuntimeException.class, () -> {
            lectureScheduleRepository.applyLecture(lectureSchedule.enroll());
        });
    }

    @Test
    @DisplayName("[성공] 특강 신청 여부 조회")
    void checkApplicationTest() {
        // Given
        Long userId = 1L;
        Long specialLectureId = 1L;
        String title = "title";
        String tutor = "tutor";
        lectureHistoryRepository.create(userId, specialLectureId, title, tutor);

        // When
        Optional<LectureHistory> checkApplyStatus = lectureService.getApplyStatus(userId, specialLectureId);

        // Then
        assertThat(checkApplyStatus.get().lectureTitle()).isEqualTo("title");
        assertThat(checkApplyStatus.get().lectureTutor()).isEqualTo("tutor");
    }


}
