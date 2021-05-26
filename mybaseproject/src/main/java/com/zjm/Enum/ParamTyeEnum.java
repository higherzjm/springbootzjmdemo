package com.zjm.Enum;

public  enum ParamTyeEnum{
    String("java.lang.String"),
    Integer("java.lang.Integer");

    private Object type;

    public Object getType() {
        return type;
    }

    ParamTyeEnum(Object type) {
        this.type = type;
    }

    public static ParamTyeEnum paramTyeEnumValueOf(Object type) {
        for (ParamTyeEnum paramTyeEnum : ParamTyeEnum.values()) {
            if (paramTyeEnum.getType().equals(type)) {
                return paramTyeEnum;
            }

        }
        return null;
    }
}