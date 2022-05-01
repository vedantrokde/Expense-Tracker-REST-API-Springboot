package com.code.et.service;

import javax.transaction.Transactional;

import com.code.et.exception.EtAuthException;
import com.code.et.exception.EtNotFoundException;
import com.code.et.model.Tracker;
import com.code.et.repo.TrackerRepo;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class TrackerService {
    private TrackerRepo repo;

    public Tracker getTracker(int id) {
        return repo.findById(id).orElseThrow(() -> new EtNotFoundException("Tracker " + id + " not found"));
    }

    public Tracker saveTracker(Tracker tracker) throws EtAuthException {
        if(!repo.findById(tracker.getId()).isPresent() && repo.countByEmail(tracker.getEmail())>0) {
            throw new EtAuthException("Email already in use");
        }

        String hashPassword = BCrypt.hashpw(tracker.getPassword(), BCrypt.gensalt(10));
        tracker.setPassword("{bcrypt} " + hashPassword);

        return repo.save(tracker);
    }

    public Tracker validateTracker(String email, String password) throws EtAuthException {
        if(email != null) email = email.toLowerCase();
        Tracker tracker = repo.findByEmail(email).orElseThrow(() -> new EtAuthException("Invalid Email/Password"));

        if(!BCrypt.checkpw(password, tracker.getPassword().substring(9))) throw new EtAuthException("Invalid Email/Password");

        return tracker;
    }

    public void deleteTracker(int id) {
        repo.deleteById(id);
    }
}