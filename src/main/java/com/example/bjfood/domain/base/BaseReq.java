package com.example.bjfood.domain.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseReq implements Serializable {

    protected int pageNum=1;


    protected int pageSize=10;

}
