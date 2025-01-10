package com.mmsaap.controller;

import com.mmsaap.entity.Property;
import com.mmsaap.entity.Reviews;
import com.mmsaap.entity.User;
import com.mmsaap.repository.PropertyRepository;
import com.mmsaap.repository.ReviewsRepository;
import com.mmsaap.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private PropertyRepository propertyRepository;
    private ReviewsRepository reviewsRepository;
    private UserRepository userRepository;

    public ReviewController(PropertyRepository propertyRepository, ReviewsRepository reviewsRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.reviewsRepository = reviewsRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public String addReview(
            @RequestBody Reviews review,
            @RequestParam long propertyId
    ) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();  // Assuming principal is a String (username)

        // Retrieve the User object from the database based on the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));



        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
        Reviews reviewsStatus = reviewsRepository.findByPropertyAndUser(property, user);

        if(reviewsStatus != null){

        review.setProperty(property);
        review.setUser(user);

        reviewsRepository.save(review);
        return "added..";
        }return "review already given";
    }

    @GetMapping("user/reviews")
    public List<Reviews> viewMyReviews() {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();  // Assuming principal is a String (username)

        // Retrieve the User object from the database based on the username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return reviewsRepository.findByUser(user);
    }
}
