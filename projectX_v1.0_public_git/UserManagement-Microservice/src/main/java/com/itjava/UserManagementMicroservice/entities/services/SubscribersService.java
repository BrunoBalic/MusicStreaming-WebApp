package com.itjava.UserManagementMicroservice.entities.services;

import com.itjava.UserManagementMicroservice.entities.Subscribers;
import com.itjava.UserManagementMicroservice.entities.repositories.SubscribersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribersService {

    @Autowired
    SubscribersRepository subscribersRepository;

    public Subscribers newSubscription(int userId, int iWantTofollowUserId) {
        return subscribersRepository.save(new Subscribers(userId, iWantTofollowUserId));
    }

    // void ili da vratim neki znak ??
    public void removeSubscription(int userId, int iDontWantTofollowUserId) {
        subscribersRepository.delete(subscribersRepository.findByUserAndUserFollows(userId, iDontWantTofollowUserId));
    }

}
