package com.example.MainServices.enumuration;

//@JsonDeserialize(using = ExpriedStatusDeserializer.class)
public enum ExpiredStatus {
	EXPIRED , EXPIREDSOON , NOT_EXPIRED ,ACTIVETED;
	
	
//    @JsonCreator
//    public static ExpiredStatus fromValue(String value) {
//        return ExpiredStatus.valueOf(value.toUpperCase());
//    }
//
//    @JsonValue
//    public String toValue() {
//        return this.name().toLowerCase();
//    }
}
