package com.archiving.archivingTool.dto.bpm;

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
