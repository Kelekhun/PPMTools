package com.kelekhun.ppmtools.repositories;

import com.kelekhun.ppmtools.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

}
