package net.careerboard.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "interview_type")
public class InterviewType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "interviewType", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Interview> interviews;
}
