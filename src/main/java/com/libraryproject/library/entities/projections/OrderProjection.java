package com.libraryproject.library.entities.projections;

import com.libraryproject.library.entities.User;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface OrderProjection {
    Long getId();
    Long getClientId();
    Integer getStatus();
    Instant getMoment();
}
