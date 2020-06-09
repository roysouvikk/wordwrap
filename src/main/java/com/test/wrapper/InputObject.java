package com.test.wrapper;

public class InputObject {
    String serializedObject;
    Object marker;

    public InputObject(String obj, Object marker){
        this.serializedObject = obj;
        this.marker = marker;
    }
}
