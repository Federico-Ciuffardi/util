/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright (C) 2019  Federico Ciuffardi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Please contact me (federico.ciuffardi@outlook.com) if you need 
 * additional information or have any questions.
 */

package com.github.federico_ciuffardi.util;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
/* 
 * Simple preferences based on java.util.prefs.Preferences but with an
 * extra one that lets you define defaults and reset to them later.
 * @author Federico Ciuffardi
 */

public class Prefs {
	/* All mechanics via java.util.prefs.Preferences class */
	private Preferences preferences;
	private Map<String,String> defaults;
	private static Map<String,Map<String,String>> defaultsMaps = new HashMap<String,Map<String,String>>();
	/*
     * Creates a {@code Prefs} for storing preferences
     * for to the the current user and given id
     *
     * @param id the preferences id for this instance to access. 
     */
	public Prefs(String id) {
		preferences = Preferences.userRoot().node(id);
		if(!defaultsMaps.containsKey(id)) {
			defaults = new HashMap<String,String>();
			defaultsMaps.put(id,defaults);
		}else {
			defaults = defaultsMaps.get(id);
		}
		
	}
	
	/*
     * Sets the default value for the given attribute
     *
     * @param att attribute which default will be set. 
     * @param def default value to set. 
     */
    public void setDefault(String att, String def) {
    	defaults.put(att,def);
    }
    
	/*
     * Sets the value for the given attribute
     *
     * @param att attribute which value will be set. 
     * @param val value to set. 
     */
    public void set(String att, String val) {
    	preferences.put(att, val); 
    }
    
	/*
     * Gets the value stored for given attribute
     *
     * @param att attribute which value will be returned. 
     * 
     * @return value of the given attribute 
     * 
     * @throws IllegalArgumentException if {@code att} 
     * doesn't have a value or a default value
     */
    public String get(String att) throws IllegalArgumentException {
    	if(!defaults.containsKey(att) && preferences.get(att,"\0")=="\0") {
    		throw new IllegalArgumentException("No value or default value set for: "+ att);
    	}
    	return preferences.get(att,defaults.get(att));
    }
    
	/*
     * Sets the value of all the attributes that have a default value
     * to the default value
     */
    public void allToDefaults() {
    	for(String att:defaults.keySet()) {
    		preferences.remove(att);
    	}
    }
    
	/*
     * Sets the value the given attribute to the default value
     * 
     * @throws IllegalArgumentException if {@code att} doesn't 
     * have a default value
     */
    public void toDefault(String att) throws IllegalArgumentException {
    	if(!defaults.containsKey(att)) {
    		throw new IllegalArgumentException("No default value set for: "+ att);
    	}
    	preferences.remove(att);
    }
}
