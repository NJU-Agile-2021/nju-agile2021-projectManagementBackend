package com.nju.projectManagement.Enum;

import java.util.Optional;

/**
 * @author Toby Fu
 * @date 2022/2/6
 **/

public enum TaskStateKind {
    // Task State
    UNASSIGNED("未分配", 0),
    IN_PROGRESS_DELAYED("进行中并已延期", 1),
    IN_PROGRESS("进行中并未延期", 2),
    TO_BE_CHECKED("待验收", 3),
    FINISHED_DELAYED("延期完成", 4),
    FINISHED("期限内完成", 5);

    private String text;
    private Integer code;

    TaskStateKind(String text, Integer code) {
        this.text = text;
        this.code = code;
    }

    @Override
    public String toString() {
        return text;
    }

    public Integer intValue() {
        return code;
    }

    public static Optional<TaskStateKind> fromValue(Integer code) {
        for (TaskStateKind t: TaskStateKind.values()) {
            if (t.intValue().equals(code)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    public static Optional<TaskStateKind> fromText(String text) {
        for (TaskStateKind t: TaskStateKind.values()) {
            if (t.toString().equals(text)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }
}
