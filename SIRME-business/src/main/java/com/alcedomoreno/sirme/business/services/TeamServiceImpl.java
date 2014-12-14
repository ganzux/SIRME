package com.alcedomoreno.sirme.business.services;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.data.User;
import com.alcedomoreno.sirme.business.transform.TransformFactory;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.business.util.StringUtil;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;
import com.alcedomoreno.sirme.core.dao.TeamsDao;
import com.alcedomoreno.sirme.core.data.TeamData;
import com.alcedomoreno.sirme.core.data.UserData;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Transactional(readOnly=true)
@Service( ServiceConstants.TEAM_SERVICE)
public class TeamServiceImpl implements TeamService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( TeamServiceImpl.class );
	private static final String CLASS_NAME = "TeamServiceImpl";
	
	@Resource( name=DAOConstants.TEAM_DAO )
	protected TeamsDao teamDao;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public TeamServiceImpl(){
		MyLogger.info(log, CLASS_NAME, "TeamServiceImpl", "New Instance");
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public Collection<Team> getAll() {
		MyLogger.info(log, CLASS_NAME, "getAll", "IN");
		
		Collection<Team> dummies = null;
		dummies = (Collection<Team>) TransformFactory.getTransformator(Team.class).toBusinessObject( teamDao.getAll() );
		
		MyLogger.info(log, CLASS_NAME, "getAll", "OUT");

		return dummies;
	}

	@Override
	public Team get(String teamName, String passWord){
		MyLogger.info(log, CLASS_NAME, "get", "IN", teamName);
		
		Team team = null;
		TeamData td = teamDao.get( teamName,passWord );
		if ( td != null )
			team = (Team) TransformFactory.getTransformator(Team.class).toBusinessObject( td );
		
		MyLogger.info(log, CLASS_NAME, "get", "OUT", teamName);

		return team;
	}

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	@Override
	public void save(Team team) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "save", "IN");

		validateTeam( team );
		
		try{
			team.setPassWord( StringUtil.getMD5( team.getPassWord() ) );
			TeamData cd = getTeamNewContacts( team );
			teamDao.save( cd );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "save", "OUT");
	}

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	@Override
	public void delete(Team team) throws TransactionException{
		MyLogger.info(log, CLASS_NAME, "delete", "IN");

		try{
			teamDao.delete(  getTeamNewContacts( team ) );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "delete", "OUT");
	}

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	@Override
	public void update(Team team) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "update", "IN");

		validateTeam( team );
		
		try{
			TeamData cd = getTeamNewContacts( team );
			teamDao.update( cd );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "update", "OUT");
	}

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	@Override
	public void updatePassword(int idTeam, String pass1,String pass2) throws ValidationException,TransactionException{
		MyLogger.info(log, CLASS_NAME, "updatePassword", "team="+ idTeam, "START");
		
		validatePass(pass1,pass2);
		
		try{
			String hashedPass = StringUtil.getMD5( pass1 );
			teamDao.updatePass(idTeam, hashedPass);
		} catch( Exception e){
			throw new TransactionException( e );
		}

		MyLogger.info(log, CLASS_NAME, "updatePassword", "team="+ idTeam, "END");
	}

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	@Override
	public void updateMD5Password(int idTeam, String hashedPass,String hashedPass2) throws ValidationException,TransactionException{
		MyLogger.info(log, CLASS_NAME, "updatePassword", "team="+ idTeam, "START");
		
		validatePass(hashedPass,hashedPass2);
		
		try{
			teamDao.updatePass(idTeam, hashedPass);
		} catch( Exception e){
			throw new TransactionException( e );
		}

		MyLogger.info(log, CLASS_NAME, "updatePassword", "team="+ idTeam, "END");
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos P�blicos               //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                      M�todos Privados                     //
	///////////////////////////////////////////////////////////////
	private TeamData getTeamNewContacts( Team team){
		TeamData cd = (TeamData)TransformFactory.getTransformator(Team.class).toDataObject( team );
		Collection<UserData> users = (Collection<UserData>) TransformFactory.getTransformator(User.class).toDataObject( team.getUsers() );
		cd.setUsers( new HashSet(users) );
		return cd;
	}
	
	private void validateTeam(Team team) throws ValidationException{
		if ( team == null || team.getNameTeam() == null || team.getNameTeam().trim().isEmpty() )
			throw new ValidationException("El Nombre del Equipo es obligatorio");
		if ( team.getPassWord() == null || team.getPassWord().trim().isEmpty() )
			throw new ValidationException("La contrase�a del Equipo es obligatoria");
		if ( team.getPhoneNumber() == null || team.getPhoneNumber().trim().isEmpty() )
			throw new ValidationException("El tel�fono del Equipo es obligatoria");
	}
	
	private boolean validatePass(String pass1,String pass2) throws ValidationException{
		if ( pass1==null || pass1.trim().isEmpty() )
			throw new ValidationException( new Exception("Contrase�a vac�a") );
		if ( ! pass1.equals(pass2) )
			throw new ValidationException( new Exception("Las contrase�as no coinciden") );
		return true;
	}
	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos Privados               //
	///////////////////////////////////////////////////////////////
}
