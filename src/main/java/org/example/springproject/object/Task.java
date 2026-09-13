package org.example.springproject.object;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.example.springproject.enum_model.Priority;
import org.example.springproject.enum_model.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Task(
        @Null
        Long id,

        @NotNull
        Long creatorId,

        @NotNull
        Long assignedUserId,

        Status status,

        @NotNull
        @FutureOrPresent
        LocalDateTime createDateTime,

        @NotNull
        @FutureOrPresent
        LocalDate deadlineDate,

        @NotNull
        Priority priority,

        LocalDateTime doneDateTime
) {
}
