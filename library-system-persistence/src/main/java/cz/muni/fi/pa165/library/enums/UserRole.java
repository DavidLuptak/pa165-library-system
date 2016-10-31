/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.library.enums;

/**
 *
 * @author xmlynar
 */
public enum UserRole {
    ADMIN("Admin"), MEMBER("Member");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
