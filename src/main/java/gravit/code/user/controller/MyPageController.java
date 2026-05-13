package gravit.code.user.controller;

import gravit.code.auth.domain.LoginUser;
import gravit.code.learning.dto.response.MyPageLearningResponse;
import gravit.code.learning.facade.LearningFacade;
import gravit.code.user.dto.response.MyPageBannerResponse;
import gravit.code.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/my-page")
public class MyPageController {

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
}
