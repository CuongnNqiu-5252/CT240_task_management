package com.pro.task_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pagination {
    int currentPage;
    int page;
    int size;
    long totalElements;
    int totalPages;
}
