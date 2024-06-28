package org.example.reservation.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.reservation.api.dto.request.ApplyRequest;
import org.example.reservation.domain.domain.LectureHistory;
import org.example.reservation.domain.domain.LectureSchedule;
import org.example.reservation.domain.repository.LectureHistoryRepository;
import org.example.reservation.domain.repository.LectureRepository;
import org.example.reservation.domain.repository.LectureScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final LectureScheduleRepository lectureScheduleRepository;
    private final LectureHistoryRepository lectureHistoryRepository;

    public List<LectureSchedule> getLectures() {
        //모든 특강 조회
        return lectureScheduleRepository.readAll();
    }

    @Transactional
    public LectureHistory reserve(Long userId, ApplyRequest request) {
        LectureSchedule lectureSchedule = lectureScheduleRepository.readById(request.specialLectureId());

        //특강 신청
        lectureScheduleRepository.applyLecture(lectureSchedule.enroll());

        //특강 신청 기록 저장
        return lectureHistoryRepository.create(userId, lectureSchedule.id(), lectureSchedule.lectureTitle(), lectureSchedule.lectureTutor());
    }

    public Optional<LectureHistory> getApplyStatus(Long userId, Long specialLectureId) {
        // userId로 해당 특강 예약 여부 확인
        if (lectureHistoryRepository.existsByUserIdAndSpecialLectureId(userId, specialLectureId)) {
            return Optional.of(lectureHistoryRepository.readByUserIdAndSpecialLectureId(userId, specialLectureId));
        }
        return LectureHistory.empty();
    }



}
