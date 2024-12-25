package com.mmsaap.controller;


import com.mmsaap.entity.Reviews;
import com.mmsaap.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    @PostMapping
    public  String addReview(
            @RequestBody Reviews  review,
            @RequestParam long propertyId,
            @AuthenticationPrincipal User user
    ){
        System.out.println(user.getName());
        System.out.println(user.getEmail());
       return  "added..";
    }
}
