package com.matchfacts.anthony.app;

/**
 * Created by Anthony on 3/31/2016.
 */
public class AppConfig {

    private static final String HOST = "http://192.168.2.16:8888/";
    // private static final String HOST = "http://matchfacts.asuscomm.com:8888/";

    // Server user login url
    public static final String URL_LOGIN = HOST + "barter-me-that/login.php";
    // Server user register url
    public static final String URL_REGISTER = HOST + "barter-me-that/register.php";
    // url to get all products list
    public static final String URL_ALL_PRODUCTS = HOST + "android_connect/get_all_products.php";
    // single product url
    public static final String URL_PRODUCT_DETAILS = HOST + "android_connect/get_product_details.php";
    // url to update product
    public static final String URL_UPDATE_PRODUCT = HOST + "android_connect/update_product.php";
    // url to delete product
    public static final String URL_DELETE_PRODUCT = HOST + "android_connect/delete_product.php";

    // fucken user folder. I thought i already did this
    public static final String URL_USERS = HOST + "/barter-me-that/users/";

}
