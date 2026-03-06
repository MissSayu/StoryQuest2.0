package com.vonk.storyquest.repository;

import com.vonk.storyquest.model.FollowRelation;
import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRelationRepository extends JpaRepository<FollowRelation, Long> {

    boolean existsByFollowerAndFollowed(User follower, User followed);

    Optional<FollowRelation> findByFollowerAndFollowed(User follower, User followed);

    boolean existsByFollowerAndStory(User follower, Story story);

    Optional<FollowRelation> findByFollowerAndStory(User follower, Story story);

    List<FollowRelation> findByFollowed(User followed);

    List<FollowRelation> findByFollower(User follower);

    List<FollowRelation> findByStory(Story story);
}
