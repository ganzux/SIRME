package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.dao.common.Operations;
import com.alcedomoreno.sirme.core.data.ApplicationData;

@Transactional(readOnly=true)
public interface ApplicationsDao extends Operations<ApplicationData> {
     public ApplicationData getRootApplication();
     public Collection<ApplicationData> getChildApplications(int idApplication);
}
