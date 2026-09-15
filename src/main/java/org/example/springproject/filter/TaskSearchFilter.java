package org.example.springproject.filter;

import org.example.springproject.enum_model.Priority;
import org.example.springproject.enum_model.Status;

public record TaskSearchFilter(
        Long creatorId,
        Long assignedUserId,
        Status status,
        Priority priority
) {
}