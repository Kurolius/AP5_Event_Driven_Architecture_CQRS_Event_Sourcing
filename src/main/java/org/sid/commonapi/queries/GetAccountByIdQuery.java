package org.sid.commonapi.queries;

import lombok.Data;

@Data
public class GetAccountByIdQuery {
    private String id;

    public GetAccountByIdQuery(String id) {
        this.id = id;
    }
}
