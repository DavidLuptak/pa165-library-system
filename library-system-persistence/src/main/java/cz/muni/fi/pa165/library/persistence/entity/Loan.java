/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.library.persistence.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Bedrich Said
 */
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date loanDate;
	
    @Column(nullable = false)
    private boolean returned = false;
	
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date returnDate;
	
    //todo: cerate enum BookState
    //@Column
    //private BookState returnBookState;
}
