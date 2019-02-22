package accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService <K, V>{
    private Map<K, V> accounts;
    
    public AccountService() {
        this.accounts = new HashMap<>();
    }
    
    public V getAccount(K key) {
        return accounts.get(key);
    }
    
    public void setAccount(K key, V account) {
        accounts.put(key, account);
    }
}
