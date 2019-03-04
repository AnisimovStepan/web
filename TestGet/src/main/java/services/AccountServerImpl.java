package services;

import java.util.concurrent.atomic.AtomicInteger;

public class AccountServerImpl implements AccountServer {
    private AtomicInteger usersCount;
    private int usersLimit;
    
    public AccountServerImpl(int usersLimit) {
        this.usersCount = new AtomicInteger(0);
        this.usersLimit = usersLimit;
    }
    
    @Override
    public void addNewUser() {
        usersCount.incrementAndGet();
    }
    
    @Override
    public void removeUser() {
        usersCount.decrementAndGet();
    }
    
    @Override
    public int getUsersLimit() {
        return usersLimit;
    }
    
    @Override
    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit;
    }
    
    @Override
    public int getUsersCount() {
        return usersCount.get();
    }
}
