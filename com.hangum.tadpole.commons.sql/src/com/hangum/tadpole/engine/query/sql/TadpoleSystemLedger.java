/*******************************************************************************
 * Copyright (c) 2017 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
package com.hangum.tadpole.engine.query.sql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hangum.tadpole.commons.exception.TadpoleSQLManagerException;
import com.hangum.tadpole.engine.initialize.TadpoleSystemInitializer;
import com.hangum.tadpole.engine.manager.TadpoleSQLManager;
import com.hangum.tadpole.engine.query.dao.system.UserDBDAO;
import com.hangum.tadpole.engine.query.dao.system.ledger.DelegerHistoryDAO;
import com.hangum.tadpole.engine.query.dao.system.ledger.LedgerDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * Tadpole System ledger query
 * 
 * @author hangum
 *
 */
public class TadpoleSystemLedger {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TadpoleSystemLedger.class);
	
	/**
	 * 오라클 변경이력데이터 
	 * @param userDBDAO 
	 * 
	 * @param ledgerDAO
	 * @return
	 * @throws TadpoleSQLManagerException
	 * @throws SQLException
	 */
	public static List<DelegerHistoryDAO> getOracleDetailListLedger(UserDBDAO userDBDAO, LedgerDAO ledgerDAO) throws TadpoleSQLManagerException, SQLException {
		
		SqlMapClient sqlClient = TadpoleSQLManager.getInstance(userDBDAO);
		return sqlClient.queryForList("getOracleDetailListLedger", ledgerDAO);
	}
	
	/**
	 * 
	 * @param strUser
	 * @param crNumber
	 * @return
	 * @throws TadpoleSQLManagerException
	 * @throws SQLException
	 */
	public static List<LedgerDAO> getMySQLListLedger(String strUser, String crNumber) throws TadpoleSQLManagerException, SQLException {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("strUser", "%" + strUser + "%");
		mapParam.put("crNumber", "%" + crNumber + "%");
		
		SqlMapClient sqlClient = TadpoleSQLManager.getInstance(TadpoleSystemInitializer.getUserDB());
		return sqlClient.queryForList("getMySQLListLedger", mapParam);
	}
	
	/**
	 * ledger 정보를 저장한다.
	 *  
	 * @return
	 * @throws TadpoleSQLManagerException, SQLException
	 */
	public static LedgerDAO insertLedger(LedgerDAO ledger) throws TadpoleSQLManagerException, SQLException {
		SqlMapClient sqlClient = TadpoleSQLManager.getInstance(TadpoleSystemInitializer.getUserDB());
		java.lang.Integer intSeq = (java.lang.Integer)sqlClient.insert("insert_ledger", ledger);
		ledger.setSeq(intSeq);
		
		return ledger;
	}

	/**
	 * 원장 데이터 저장 상태를 기록한다.
	 * 
	 * @param ledgerDAO
	 */
	public static void insertLedgerResult(LedgerDAO ledgerDAO) throws TadpoleSQLManagerException, SQLException {
		SqlMapClient sqlClient = TadpoleSQLManager.getInstance(TadpoleSystemInitializer.getUserDB());
		sqlClient.update("update_ledger_result", ledgerDAO);
	}
	
}
