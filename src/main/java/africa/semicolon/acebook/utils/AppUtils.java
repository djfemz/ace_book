package africa.semicolon.acebook.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class AppUtils {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE_NUMBER = 1;

    public static Pageable createPageRequest(int page, int size){
        if (page<1) page = DEFAULT_PAGE_NUMBER;
        if (size<1) size = DEFAULT_PAGE_SIZE;
        return PageRequest.of(page-DEFAULT_PAGE_NUMBER, size);
    }
}
