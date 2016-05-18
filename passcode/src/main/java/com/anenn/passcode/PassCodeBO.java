package com.anenn.passcode;

/**
 * Created by anenn <anennzxq@gmail.com> on 5/18/16.
 */
public class PassCodeBO {

    public static final int TYPE_NUMBER = 0;
    public static final int TYPE_EMPTY = 1;
    public static final int TYPE_DELETE = 2;

    private Object obj;
    private int type;

    public PassCodeBO(Object obj, int type) {
        this.obj = obj;
        this.type = type;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
