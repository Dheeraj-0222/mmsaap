package com.mmsaap.repository;

import com.mmsaap.entity.Property;
import com.mmsaap.entity.Reviews;
import com.mmsaap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
   List<Reviews> findByUser(User user);
   Reviews findByPropertyAndUser(Property Property, User User);
}