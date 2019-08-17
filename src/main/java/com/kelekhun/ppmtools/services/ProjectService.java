package com.kelekhun.ppmtools.services;

import com.kelekhun.ppmtools.domain.Project;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    Project saveOrUpdate(Project project);
}
