package gravit.code.global.event;

public record NoticeCreatedEvent(
        long noticeId,
        String title
) {
}
