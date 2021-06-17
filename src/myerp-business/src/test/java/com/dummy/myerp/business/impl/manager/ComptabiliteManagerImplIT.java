package com.dummy.myerp.business.impl.manager;


import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import com.dummy.myerp.testbusiness.business.SpringRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//class de test basé sur la BDD demo disponible dans docker
public class ComptabiliteManagerImplIT extends BusinessTestCase {

    private static final Logger logger = LoggerFactory.getLogger(ComptabiliteManagerImplIT.class);



    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @BeforeAll
    public static void initSpring(){
        SpringRegistry.init();

    }

    @Test
    public void getListCompteComptableTest(){
        List<CompteComptable> listeCompteComptableTest =  manager.getListCompteComptable();
        assertNotNull(listeCompteComptableTest.size());
    }


    @Test
    public void getListJournalComptableTest(){
        List<JournalComptable> listeJournalComptableTest =  manager.getListJournalComptable();
        assertNotNull(listeJournalComptableTest.size());
    }

    @Test
    public void getListEcritureComptableTest(){
        List<EcritureComptable> listeEcritureComptableTest =  manager.getListEcritureComptable();
        assertNotNull(listeEcritureComptableTest.size());
    }

    @Test
    public void getEcritureComptableTest() throws FunctionalException {
        EcritureComptable nEC = new EcritureComptable();
        nEC.setId(-1);
        nEC.setReference("AC-2016/00001");
        EcritureComptable fEC =  manager.getEcritureComptable(nEC.getId());
        assertEquals("AC-2016/00001",fEC.getReference());
    }

    //expected throw exception when checkEcritureComptable
    @Test
    public void insertEcritureComptableTestRG() {
        EcritureComptable nEC = new EcritureComptable();
        nEC.setId(-1);
        nEC.setReference("AC-2016/00001");
        nEC.setJournal(new JournalComptable("AC","Achat"));
        Exception exception = assertThrows(FunctionalException.class, () -> manager.insertEcritureComptable(nEC));
        logger.info(exception.getMessage());

    }


    @Test
    public void insertEcritureComptableTest() throws FunctionalException {
        EcritureComptable nEC = new EcritureComptable();
        nEC.setId(-1);
        nEC.setReference("AA-2021/00001");
        nEC.setJournal(new JournalComptable("AA","Achat"));
//        manager.insertEcritureComptable(nEC);

    }

    //expected throw exception when checkEcritureComptable
    @Test
    public void updateEcritureComptableTestRG() {
        EcritureComptable nEC = new EcritureComptable();
        nEC.setId(-1);

        Exception exception = assertThrows(FunctionalException.class, () -> manager.updateEcritureComptable(nEC));
        logger.info(exception.getMessage());

    }

    @Test
    public void updateEcritureComptableTest() throws FunctionalException {
        EcritureComptable nEC = new EcritureComptable();
        nEC.setId(-1);
        nEC = manager.getEcritureComptable(nEC.getId());
        String precedentLibelle = nEC.getLibelle();
        nEC.setLibelle("test");
        manager.updateEcritureComptable(nEC);

        assertEquals("test", manager.getEcritureComptable(-1).getLibelle());

        // reinitialisation du champ
        nEC.setLibelle(precedentLibelle);
        getBusinessProxy().getComptabiliteManager().updateEcritureComptable(nEC);

    }


}
