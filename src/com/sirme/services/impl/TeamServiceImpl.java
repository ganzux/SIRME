package com.sirme.services.impl;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sirme.basicsecurity.business.data.User;
import com.sirme.basicsecurity.data.UserData;
import com.sirme.bussiness.Team;
import com.sirme.dao.ITeamsDao;
import com.sirme.data.TeamData;
import com.sirme.services.ITeamService;
import com.sirme.transform.TransformFactory;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;
import com.sirme.util.StringUtil;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;

@Service( SpringConstants.TEAM_SERVICE)
public class TeamServiceImpl implements ITeamService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( TeamServiceImpl.class );
	private static final String CLASS_NAME = "TeamServiceImpl";
	
	@Resource( name=SpringConstants.TEAM_DAO )
	protected ITeamsDao teamDao;

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
	//                      Métodos Públicos                     //
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
	public Team get( String teamName,String passWord ){
		MyLogger.info(log, CLASS_NAME, "get", "IN", teamName);
		
		Team team = null;
		TeamData td = teamDao.get( teamName,passWord );
		if ( td != null )
			team = (Team) TransformFactory.getTransformator(Team.class).toBusinessObject( td );
		
		MyLogger.info(log, CLASS_NAME, "get", "OUT", teamName);

		return team;
	}

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
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
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
			throw new ValidationException("La contraseña del Equipo es obligatoria");
		if ( team.getPhoneNumber() == null || team.getPhoneNumber().trim().isEmpty() )
			throw new ValidationException("El teléfono del Equipo es obligatoria");
	}
	
	private boolean validatePass(String pass1,String pass2) throws ValidationException{
		if ( pass1==null || pass1.trim().isEmpty() )
			throw new ValidationException( new Exception("Contraseña vacía") );
		if ( ! pass1.equals(pass2) )
			throw new ValidationException( new Exception("Las contraseñas no coinciden") );
		return true;
	}
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Privados               //
	///////////////////////////////////////////////////////////////
}
