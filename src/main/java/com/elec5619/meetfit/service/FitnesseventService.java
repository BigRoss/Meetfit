package com.elec5619.meetfit.service;

import com.elec5619.meetfit.domain.Achievedbadges;
import com.elec5619.meetfit.domain.Fitnessevent;
import com.elec5619.meetfit.domain.User;
import com.elec5619.meetfit.repository.AchievedbadgesRepository;
import com.elec5619.meetfit.repository.FitnesseventRepository;
import com.elec5619.meetfit.repository.UserRepository;
import com.elec5619.meetfit.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class FitnesseventService {

    private final Logger log = LoggerFactory.getLogger(FitnesseventService.class);

    @Inject
    CalcBadgeService calcBadgeService;

    @Inject
    private FitnesseventRepository fitnesseventRepository;

    @Inject
    private AchievedbadgesRepository achievedbadgesRepository;

    @Inject
    private UserRepository userRepository;

    public Fitnessevent addAttending(Fitnessevent fitnessevent)
    {

        User currUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        fitnessevent.addAttending(currUser);
        //Add 10 points when a user joins an event
        Achievedbadges badges = achievedbadgesRepository.getOne(currUser.getId());
        if(badges != null){
            badges.setPoints(badges.getPoints() + 10);
            calcBadgeService.add(badges);
        }

        Fitnessevent result = fitnesseventRepository.save(fitnessevent);

        return result;
    }


}
