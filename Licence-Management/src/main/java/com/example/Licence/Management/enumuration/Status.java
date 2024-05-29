package com.example.Licence.Management.enumuration;

//@JsonDeserialize(using = StatusDeserializer.class)
public enum Status {

	ACTIVE ,  INACTIVE , PENDING , APPROVED , ISSUED , RENEWED ,REQUEST;
	
//    @JsonCreator
//    public static Status fromValue(String value) {
//        return Status.valueOf(value.toUpperCase());
//    }
//
//    @JsonValue
//    public String toValue() {
//        return this.name().toLowerCase();
//    }
}
