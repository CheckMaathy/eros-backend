package com.erosproject.reactiveback.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ActionEnum {
    CREATE("create"),
    UPDATE("update"),
    DELETE("disable");

    private String name;

    public static ActionEnum actionName(String name){
        return Arrays.stream(ActionEnum.values())
                .filter(action -> action.name().equals(name))
                .findFirst().orElse(null);
    }
}
