package at.fehringer_reihs.restapi.Rest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class PaginatedResponse<T> {
    private long totalResults;
    private int pageSize;
    private int currentPage;
    private List<T> content;
}
