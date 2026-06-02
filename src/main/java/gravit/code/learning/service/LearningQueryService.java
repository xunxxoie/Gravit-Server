package gravit.code.learning.service;

import gravit.code.global.exception.domain.CustomErrorCode;
import gravit.code.global.exception.domain.RestApiException;
import gravit.code.learning.domain.Learning;
import gravit.code.learning.dto.internal.ConsecutiveAtRiskUser;
import gravit.code.learning.repository.LearningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningQueryService {

    private final LearningRepository learningRepository;

    @Transactional(readOnly = true)
    public Learning getLearning(long userId) {
        return learningRepository.findByUserId(userId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.LEARNING_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<ConsecutiveAtRiskUser> getConsecutiveAtRiskUsers() {
        return learningRepository.findConsecutiveAtRiskUsers();
    }

    @Transactional(readOnly = true)
    public List<Long> getDailyIncompleteUserIds() {
        return learningRepository.findDailyIncompleteUserIds();
    }
}
