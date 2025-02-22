package net.careerboard.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "interview_image")
public class InterviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false)
    @JsonBackReference
    private Interview interview;

    @Column(nullable = false, name = "image_name")
    private String imageName;

    @Override
    public String toString() {
        return "InterviewImage{" +
                "Image Name='" + imageName + '\'' +
                '}';
    }
}
