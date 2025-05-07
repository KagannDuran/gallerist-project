package com.kagan.starter.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDetails<E> {
    private String path;
    private Date createTime;

    private E message;
}
