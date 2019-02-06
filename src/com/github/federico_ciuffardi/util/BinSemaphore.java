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

import java.util.concurrent.Semaphore;

/* 
 * A binary semaphore. Like the java.util.concurrent.Semaphore semaphore
 * but the permits can't be greater than 1, so it can be only initialized
 * with 1 or 0 permits and when the permits are at 1 call of {@link #release}
 * won't increase the numbers of permits.
 * @author Federico Ciuffardi
 */

public class BinSemaphore implements java.io.Serializable {
	private static final long serialVersionUID = -2541779477310465377L;
	/* All mechanics via java.util.concurrent.Semaphore class */
	private Semaphore sem;
	
	/*
     * Creates a {@code Semaphore} with 1
     * permit and non fair fairness setting.
     */
	public BinSemaphore() {
		sem = new Semaphore(1);
	}
	
	/*
     * Creates a {@code Semaphore} with the given number of
     * permits and non fair fairness setting.
     *
     * @param permits the initial number of permits available.
     *        This value may be negative, in which case releases
     *        must occur before any acquires will be granted.
     *        Also it can be greater than 1, in which case the 
     *        permits are set to 1.
     */
	public BinSemaphore(int permits) {
		if(permits>1) {
			sem = new Semaphore(1);
		}else {
			sem = new Semaphore(permits);
		}	
	}
	
	/*
     * Creates a {@code Semaphore} with the given number of
     * permits and the given fairness setting.
     *
     * @param permits the initial number of permits available.
     *        This value may be negative, in which case releases
     *        must occur before any acquires will be granted.
     *        Also it can be greater than 1, in which case the 
     *        permits are set to 1.
     * @param fair {@code true} if this semaphore will guarantee
     *        first-in first-out granting of permits under contention,
     *        else {@code false}
     */
	public BinSemaphore(int permits,boolean fair) {
		if(permits>1) {
			sem = new Semaphore(1,fair);
		}else {
			sem = new Semaphore(permits,fair);
		}	
	}
	
    /*
     * Acquires a permit from this semaphore, blocking until one is
     * available.
     */
	public void acquire() {
		sem.acquireUninterruptibly();
	}
	
    /*
     * Releases a permit increasing the number of available permits by
     * one (the total permits can't be greater than 1 so if there 
     * is already one permit available nothing is done).
     * 
     * There is no requirement that a thread that releases a permit must
     * have acquired that permit by calling {@link #acquire}.
     * Correct usage of a semaphore is established by programming convention
     * in the application.
     */
	public void release() {
		if(sem.availablePermits() < 1) {
			sem.release();
		}
	}
	
    /**
     * Returns the current number of permits available in this semaphore.
     *
     * @return the number of permits available in this semaphore
     */
    public int availablePermits() {
        return sem.availablePermits();
    }
	
    /**
     * Acquires and returns all permits that are immediately available.
     *
     * @return the number of permits acquired
     */
    public int drainPermits() {
        return sem.drainPermits();
    }
	
    /**
     * Returns {@code true} if this semaphore has fairness set true.
     *
     * @return {@code true} if this semaphore has fairness set true
     */
    public boolean isFair() {
        return sem.isFair();
    }

    /**
     * Queries whether any threads are waiting to acquire. Note that
     * because cancellations may occur at any time, a {@code true}
     * return does not guarantee that any other thread will ever
     * acquire.  This method is designed primarily for use in
     * monitoring of the system state.
     *
     * @return {@code true} if there may be other threads waiting to
     *         acquire the lock
     */
    public boolean hasQueuedThreads() {
        return sem.hasQueuedThreads();
    }
	
    /**
     * Returns an estimate of the number of threads waiting to acquire.
     * The value is only an estimate because the number of threads may
     * change dynamically while this method traverses internal data
     * structures.  This method is designed for use in monitoring of the
     * system state, not for synchronization control.
     *
     * @return the estimated number of threads waiting for this lock
     */
    public int getQueueLength() {
        return sem.getQueueLength();
    }
	
    /**
     * Returns a string identifying this semaphore, as well as its state.
     * The state, in brackets, includes the String {@code "Permits ="}
     * followed by the number of permits.
     *
     * @return a string identifying this semaphore, as well as its state
     */
    public String toString() {
        return sem.toString();
    }
    
}
