package com.FileDepot.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthInterface extends Remote {
    int login(String email, String password) throws RemoteException;
    int register(String name, String email, String password, long number) throws RemoteException;
}
