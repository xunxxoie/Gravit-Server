package gravit.code.lesson.domain;

import gravit.code.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "lesson_submission")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonSubmission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "learning_time", nullable = false)
    private int learningTime;

    @Column(name = "try_count", nullable = false)
    private int tryCount;

    @Column(name = "accuracy", nullable = false)
    private int accuracy;

    @Column(name = "lesson_id", nullable = false)
    private long lessonId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Builder(access = AccessLevel.PRIVATE)
    private LessonSubmission(
            int learningTime,
            int accuracy,
            long lessonId,
            long userId
    ) {
        this.learningTime = learningTime;
        this.tryCount = 1;
        this.accuracy = accuracy;
        this.lessonId = lessonId;
        this.userId = userId;
    }

    public static LessonSubmission create(
            int learningTime,
            int accuracy,
            long lessonId,
            long userId
    ) {
        return LessonSubmission.builder()
                .learningTime(learningTime)
                .accuracy(accuracy)
                .lessonId(lessonId)
                .userId(userId)
                .build();
    }

    public void updateLearningTime(int learningTime){
        this.learningTime = learningTime;
    }

    public void updateAccuracy(int accuracy){
        this.accuracy = accuracy;
    }

    public void updateTryCount() {
        this.tryCount++;
    }
}