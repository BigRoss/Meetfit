package com.elec5619.meetfit.service;

import com.elec5619.meetfit.domain.Achievedbadges;
import com.elec5619.meetfit.domain.Badges;
import com.elec5619.meetfit.repository.AchievedbadgesRepository;
import com.elec5619.meetfit.repository.BadgesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class CalcBadgeService {

    private final Logger log = LoggerFactory.getLogger(CalcBadgeService.class);

    @Inject
    private AchievedbadgesRepository achievedbadgesRepository;

    @Inject
    private BadgesRepository badgesRepository;

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


        List<Badges> badges = badgesRepository.findAll();
        int maxpoint = 0;
        for (int i = 0; i < badges.size(); i++) {
            if(badges.get(i).getPoints() <= points && badges.get(i).getPoints() > maxpoint){
                result.setBadges(badges.get(i));
                maxpoint = badges.get(i).getPoints();
            }
        }

        return result;
    }
}
