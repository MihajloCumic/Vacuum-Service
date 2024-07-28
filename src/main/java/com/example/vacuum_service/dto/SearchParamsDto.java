package com.example.vacuum_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParamsDto {
    private String name;
    private List<String> statuses;
    private Long dateFrom;
    private Long dateTo;
}
