package org.example.reservation.infra.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.reservation.domain.domain.LectureSchedule;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lecture_schedule")
public class LectureScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lectureId;

    private Long capacity;

    private LocalDateTime openTime;

    private String title;

    private String tutor;


    public LectureSchedule toLectureSchedule() {
        return new LectureSchedule(getId(), lectureId, capacity, openTime, title, tutor);
    }
    public LectureScheduleEntity(Long lectureId, Long capacity, LocalDateTime openTime, String title, String tutor) {
        this.lectureId = lectureId;
        this.capacity = capacity;
        this.openTime = openTime;
        this.title = title;
        this.tutor = tutor;
    }


    public void updateCapacity(LectureSchedule lectureSchedule) {
        this.capacity = lectureSchedule.capacity();
    }
}
