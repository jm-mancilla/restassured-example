package org.ecos.logic;

import lombok.Data;

import java.util.List;

@Data
public class User {

    protected String userId;
    protected String collectionOfIsbn;

    public User(String userId, String collectionOfIsbn) {
        this.userId = userId;
        this.collectionOfIsbn = collectionOfIsbn;
    }

    public String getUserId(){
        return this.userId;
    }
    public String getCollectionOfIsbn(){
        return this.collectionOfIsbn;
    }
}
