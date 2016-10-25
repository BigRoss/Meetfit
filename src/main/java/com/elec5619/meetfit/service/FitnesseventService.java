package com.elec5619.meetfit.service;

import com.elec5619.meetfit.domain.Fitnessevent;
import com.elec5619.meetfit.domain.User;
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
    private FitnesseventRepository fitnesseventRepository;

    @Inject
    private UserRepository userRepository;

    public Fitnessevent CreatorJoinEvent(Fitnessevent fitnessevent){

        Fitnessevent result = fitnesseventRepository.save(fitnessevent);

        User currUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        fitnessevent.addAttending(currUser);
        fitnessevent.setOrganiser(currUser);

        return result;
    }


}
