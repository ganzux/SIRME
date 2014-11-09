package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.data.ApplicationData;

@Transactional(readOnly=true)
public interface ApplicationsDao {
     public ApplicationData getRootApplication();
     public Collection<ApplicationData> getChildApplications(int idApplication);
}