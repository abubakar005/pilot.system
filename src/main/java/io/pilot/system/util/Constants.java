package io.pilot.system.util;

public class Constants {

    public static final String SYSTEM_USER = "System";
    public static String FILE_TYPE = "text/csv";

    // File headers
    public static String FILE_HEADER_TITLE = "title";
    public static String FILE_HEADER_AUTHOR = "author";
    public static String FILE_HEADER_DATE = "date";
    public static String FILE_HEADER_VIEWS = "views";
    public static String FILE_HEADER_LIKES = "likes";
    public static String FILE_HEADER_LINK = "link";
    public static String DELIMITER_COMMA = ",";
    public static int FILE_CHUNK_SIZE = 1000;

    public static String STEP_BUILDER_FACTORY_NAME = "Import-TedTalks-Step";
    public static String JOB_BUILDER_FACTORY_NAME = "Import-TedTalks-Job";
    public static String FLAT_ITEM_READER_NAME = "CSV-Reader";
}
