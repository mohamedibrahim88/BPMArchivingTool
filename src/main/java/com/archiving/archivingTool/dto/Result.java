package com.archiving.archivingTool.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result<T> {
    String status ;
    T data;
}
