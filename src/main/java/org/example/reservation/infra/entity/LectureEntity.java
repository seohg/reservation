package org.example.reservation.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.reservation.domain.domain.Lecture;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lecture")
public class LectureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String title;


    public LectureEntity(String title) {
        this.title = title;
    }

    public Lecture toLecture() {
        return new Lecture(getId(), title);
    }
}
