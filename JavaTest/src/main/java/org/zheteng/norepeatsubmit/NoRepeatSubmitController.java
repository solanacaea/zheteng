package org.zheteng.norepeatsubmit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoRepeatSubmitController {

    @GetMapping("/norepeat")
    @NoRepeatSubmit
    public void submit() {

    }
}
