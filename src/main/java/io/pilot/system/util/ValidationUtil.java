package io.pilot.system.util;

import io.pilot.system.dto.CreateTedTalkRequestDto;
import io.pilot.system.dto.TedTalkSearchCriteriaDto;
import io.pilot.system.dto.UpdateTedTalkRequestDto;
import io.pilot.system.enums.Error;
import io.pilot.system.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

public class ValidationUtil {

    public static void validateRequest(CreateTedTalkRequestDto request) {
        requestValidation(request.getAuthor(), request.getTitle(), request.getDate(), request.getViews(), request.getLikes(), request.getLink());
    }

    public static void validateRequest(UpdateTedTalkRequestDto request) {
        requestValidation(request.getAuthor(), request.getTitle(), request.getDate(), request.getViews(), request.getLikes(), request.getLink());
    }

    private static void requestValidation(String author, String title, String date, Long views, Long likes, String link) {

        if(StringUtils.isBlank(author))
            throw new BadRequestException(Error.REQUEST_BODY_MISSING_AUTHOR.getCode(), Error.REQUEST_BODY_MISSING_AUTHOR.getMsg());

        if(StringUtils.isBlank(title))
            throw new BadRequestException(Error.REQUEST_BODY_MISSING_TITLE.getCode(), Error.REQUEST_BODY_MISSING_TITLE.getMsg());

        if(StringUtils.isBlank(date))
            throw new BadRequestException(Error.REQUEST_BODY_MISSING_DATE.getCode(), Error.REQUEST_BODY_MISSING_DATE.getMsg());

        if(views == null)
            throw new BadRequestException(Error.REQUEST_BODY_MISSING_VIEWS.getCode(), Error.REQUEST_BODY_MISSING_VIEWS.getMsg());

        if(likes == null)
            throw new BadRequestException(Error.REQUEST_BODY_MISSING_LIKES.getCode(), Error.REQUEST_BODY_MISSING_LIKES.getMsg());

        if(StringUtils.isBlank(link))
            throw new BadRequestException(Error.REQUEST_BODY_MISSING_LINK.getCode(), Error.REQUEST_BODY_MISSING_LINK.getMsg());
    }

    public static boolean isValidaSearchCriteria(TedTalkSearchCriteriaDto searchCriteria) {

        if(StringUtils.isBlank(searchCriteria.getAuthor())
                && StringUtils.isBlank(searchCriteria.getTitle())
                && (searchCriteria.getLikes() == null || searchCriteria.getLikes() != 0)
                && (searchCriteria.getViews() == null || searchCriteria.getViews() != 0))
            return false;

        return true;
    }
}
