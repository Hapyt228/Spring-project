package org.example.springproject;

import org.example.springproject.Enum.Priority;
import org.example.springproject.Enum.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Task(
        Long id,
        Long creatorId,
        Long assignedUserId,
        Status status,
        LocalDateTime createDateTime,
        LocalDate deadlineDate,
        Priority priority
) {
}
