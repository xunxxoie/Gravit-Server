package gravit.code.user.service;

import gravit.code.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAccessService {

    private final UserRepository userRepository;
    private final Clock clock;

    @Transactional
    public void updateLastAccessed(long userId) {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime startOfToday = LocalDate.now(clock).atStartOfDay();

        userRepository.updateLastAccessedAt(userId, now, startOfToday);
    }
}
