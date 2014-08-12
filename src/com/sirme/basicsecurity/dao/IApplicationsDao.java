package com.sirme.basicsecurity.dao;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.basicsecurity.data.ApplicationData;

@Transactional(readOnly=true)
public interface IApplicationsDao {
     public ApplicationData getRootApplication();
     public Collection<ApplicationData> getChildApplications(int idApplication);
}