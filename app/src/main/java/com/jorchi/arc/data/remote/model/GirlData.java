package com.jorchi.arc.data.remote.model;

import com.jorchi.arc.data.local.db.entity.Girl;

import java.util.List;

public class GirlData {
    public final boolean error;

    public final List<Girl> results;

    public GirlData(boolean error, List<Girl> results) {
        this.error = error;
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public List<Girl> getResults() {
        return results;
    }
}
