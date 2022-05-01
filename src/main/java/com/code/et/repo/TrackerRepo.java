package com.code.et.repo;

import java.util.Optional;

import com.code.et.exception.EtAuthException;
import com.code.et.model.Tracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackerRepo extends JpaRepository<Tracker, Integer> {
    public Optional<Tracker> findByEmail(String email) throws EtAuthException;

    public Integer countByEmail(String email);
}
