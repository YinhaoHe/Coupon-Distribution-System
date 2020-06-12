package com.coupon.customers.constant;

/**
 * <h1>Constants Definition</h1>
 */
public class Constants {

    /** Merchant coupon Kafka Topic */
    public static final String TEMPLATE_TOPIC = "merchants-template";

    /** token file dir */
    public static final String TOKEN_DIR = "/tmp/token/";

    /** Used token suffix */
    public static final String USED_TOKEN_SUFFIX = "_";

    /** total number of users redis key */
    public static final String USE_COUNT_REDIS_KEY = "total-user-count";

    /**
     * <h2>User HBase Table</h2>
     * */
    public class UserTable {

        /** User HBase table name */
        public static final String TABLE_NAME = "cp:user";

        /** Basic information row family */
        public static final String FAMILY_B = "b";

        /** user name */
        public static final String NAME = "name";

        /** user age */
        public static final String AGE = "age";

        /** user gender */
        public static final String GENDER = "gender";

        /** Extra information row family*/
        public static final String FAMILY_O = "o";

        /** phone number*/
        public static final String PHONE = "phone";

        /** address */
        public static final String ADDRESS = "address";
    }

    /**
     * <h2>Coupontemplate HBase Table</h2>
     * */
    public class CouponTemplateTable {

        /** Coupontemplate HBase table name */
        public static final String TABLE_NAME = "cp:coupontemplate";

        /** Basic information row family */
        public static final String FAMILY_B = "b";

        /** Merchant id */
        public static final String ID = "id";

        /** Coupon Title*/
        public static final String TITLE = "title";

        /** Coupon Summary */
        public static final String SUMMARY = "summary";

        /** Coupon Description */
        public static final String DESC = "desc";

        /** If Coupon has token */
        public static final String HAS_TOKEN = "has_token";

        /** Coupon background */
        public static final String BACKGROUND = "background";

        /** Restriction row family */
        public static final String FAMILY_C = "c";

        /** Max number of coupon */
        public static final String LIMIT = "limit";

        /** start time of coupon */
        public static final String START = "start";

        /** end time of coupon */
        public static final String END = "end";
    }

    /**
     * <h2>Coupon HBase Table</h2>
     * */
    public class CouponTable {

        /** Coupon HBase 表名 */
        public static final String TABLE_NAME = "cp:coupon";

        /** information row family */
        public static final String FAMILY_I = "i";

        /** user id */
        public static final String USER_ID = "user_id";

        /** coupon id */
        public static final String TEMPLATE_ID = "template_id";

        /** coupon token */
        public static final String TOKEN = "token";

        /** assigned date of coupon */
        public static final String ASSIGNED_DATE = "assigned_date";

        /** used date of coupon */
        public static final String CON_DATE = "con_date";
    }

    /**
     * <h2>Feedback Hbase Table</h2>
     * */
    public class Feedback {

        /** Feedback HBase table name */
        public static final String TABLE_NAME = "cp:feedback";

        /** information row family*/
        public static final String FAMILY_I = "i";

        /** user id */
        public static final String USER_ID = "user_id";

        /** comment type */
        public static final String TYPE = "type";

        /** couponTemplate RowKey, if it is app comment then it will be -1 */
        public static final String TEMPLATE_ID = "template_id";

        /** comment content */
        public static final String COMMENT = "comment";
    }
}
