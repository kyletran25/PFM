package com.finace.management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrevoEmailReqDto {
    private Map<String, String> sender;
    private List<Map<String, String>> to;
    private String subject;
    private String htmlContent;
    private int templateId;
    private Map<String, String> params;
}
