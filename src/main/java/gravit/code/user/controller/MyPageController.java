package gravit.code.user.controller;

import gravit.code.auth.domain.LoginUser;
import gravit.code.learning.dto.response.LearningHistoryResponse;
import gravit.code.learning.dto.response.MyPageLearningResponse;
import gravit.code.learning.facade.LearningFacade;
import gravit.code.user.controller.docs.MyPageControllerDocs;
import gravit.code.user.dto.response.MyPageBannerResponse;
import gravit.code.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/my-pages")
public class MyPageController implements MyPageControllerDocs {

    private final UserFacade userFacade;
    private final LearningFacade learningFacade;

    @GetMapping("/banners")
    public ResponseEntity<MyPageBannerResponse> getMyPageBanner(@AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(userFacade.getMyPageBanner(loginUser.getId()));
    }

    @GetMapping("/learning")
    public ResponseEntity<MyPageLearningResponse> getMyPageLearning(@AuthenticationPrincipal LoginUser loginUser){
        return ResponseEntity.ok(learningFacade.getMyPageLearning(loginUser.getId()));
    }

    @GetMapping("/learning/history")
    public ResponseEntity<LearningHistoryResponse> getMyPageLearningHistory(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestParam("year") int year
    ){
        return ResponseEntity.ok(learningFacade.getMyPageLearningHistory(loginUser.getId(), year));
    }
}
