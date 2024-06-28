package org.example.reservation.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.reservation.domain.domain.LectureHistory;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LectureHistory")
public class LectureHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long specialLectureId;

    private String lectureTitle;

    private String lectureTutor;

    public LectureHistoryEntity(Long userId, Long specialLectureId, String lectureTitle, String lectureTutor) {
        this.userId = userId;
        this.specialLectureId = specialLectureId;
        this.lectureTitle = lectureTitle;
        this.lectureTutor = lectureTutor;
    }
    public LectureHistory toLectureHistory() {
        return new LectureHistory(getId(), userId, specialLectureId, lectureTitle, lectureTutor);
    }

}
