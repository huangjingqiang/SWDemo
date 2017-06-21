package io.agora.openvcall.model;

/**
 * Create by xjs
 * _______date : 17/5/18
 * _______description:
 */
public class ChannelKeyBean {
    /**
     * errorId : OK
     * message :
     * code : 0
     * data : {"key":"005AQAoADlDRjQyMDExNzUxNjYxQjUxMUI5NjA1MUMwNjE0ODk2MkM1MDdDODcQAG+JI16lqk0gkB8zyzJcBNZIih1Z9buDA8jEJlkAAA==","uid":1495108168}
     * lists : null
     * page : null
     * statusCode : 200
     */

    private String errorId;
    private String message;
    private int code;
    private DataBean data;
    private Object lists;
    private Object page;
    private int statusCode;

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getLists() {
        return lists;
    }

    public void setLists(Object lists) {
        this.lists = lists;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public static class DataBean {
        /**
         * key : 005AQAoADlDRjQyMDExNzUxNjYxQjUxMUI5NjA1MUMwNjE0ODk2MkM1MDdDODcQAG+JI16lqk0gkB8zyzJcBNZIih1Z9buDA8jEJlkAAA==
         * uid : 1495108168
         */

        private String key;
        private int uid;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
