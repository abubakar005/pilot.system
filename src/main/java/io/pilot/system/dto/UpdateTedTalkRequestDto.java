package io.pilot.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateTedTalkRequestDto {

    private long id;
    private String title;
    private String author;
    private String date;
    private Long views;
    private Long likes;
    private String link;
}
