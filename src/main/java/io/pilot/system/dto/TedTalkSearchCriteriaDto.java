package io.pilot.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TedTalkSearchCriteriaDto {

    private String author;
    private String title;
    private Long views;
    private Long likes;
}
