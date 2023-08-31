package com.atipera.atipera.model;

import com.atipera.atipera.model.Branch;
import com.atipera.atipera.model.Owner;

import java.util.List;

public class Repository {
    private String name;
    private Owner owner;
    private boolean fork;
    private List<Branch> branches;

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}