package gravit.code.version.controller;

import gravit.code.version.controller.docs.VersionControllerDocs;
import gravit.code.version.dto.response.VersionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/version")
public class VersionController implements VersionControllerDocs {

    private final String appVersion;

    public VersionController(@Value("${app.version}") String appVersion) {
        this.appVersion = appVersion;
    }

    @GetMapping
    public ResponseEntity<VersionResponse> getLatestVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(VersionResponse.of(appVersion));
    }
}
