package com.elec5619.meetfit.service;

import com.elec5619.meetfit.domain.Achievedbadges;
import com.elec5619.meetfit.repository.AchievedbadgesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class CalcBadgeService {

    private final Logger log = LoggerFactory.getLogger(CalcBadgeService.class);

    @Inject
    private AchievedbadgesRepository achievedbadgesRepository;

    public Achievedbadges add(Achievedbadges achievedbadges) {
        Achievedbadges result = achievedbadgesRepository.save(achievedbadges);
        int points = result.getPoints().intValue();
        if(points > 30){
            result.setType("gold");
        }else if(points > 20){
            result.setType("silver");
        }else if(points > 10){
            result.setType("bronze");
        }else{
            result.setType("noob");
        }
        return result;
    }
}
